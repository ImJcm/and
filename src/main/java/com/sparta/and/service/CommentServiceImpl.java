package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.CommentRequestDto;
import com.sparta.and.dto.response.CommentResponseDto;
import com.sparta.and.entity.*;
import com.sparta.and.repository.CommentRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final PostService postService;
    private final CommentRepository commentRepository;
    @Override
    public List<CommentResponseDto> getComments(Long postId, UserDetailsImpl userDetails) {
        Post post = postService.findPost(postId);

        List<Comment> comments = commentRepository.getCommentListFindByPostId(postId)
                .stream()
                .toList();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentResponseDtoHashMap = new HashMap<>();

        comments.forEach(c -> {
            CommentResponseDto commentResponseDto = new CommentResponseDto(c);

            // 비밀댓글 체크
            // 삭제여부 체크
            convertComment(commentResponseDto, c.getIsSecret(), c.getIsDeleted(), c.getWriter(), post, userDetails);

            commentResponseDtoHashMap.put(commentResponseDto.getCommentId(), commentResponseDto);
            if(c.getParent() != null) commentResponseDtoHashMap.get(c.getParent().getId()).getChild().add(commentResponseDto);
            else commentResponseDtoList.add(commentResponseDto);
        });
        return commentResponseDtoList;
    }

    @Override
    public ApiResponseDto insertComment(Long postId, User user, CommentRequestDto commentRequestDto) {
        Post post = postService.findPost(postId);

        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .writer(user)
                .deleteStatus(DeleteStatus.N)
                .secretStatus(SecretStatus.N)
                .parent(null)
                .post(post)
                .build();

        if(commentRequestDto.getParentId() != null) {
            Comment parentComment = getCommentById(commentRequestDto.getParentId());
            comment.setParent(parentComment);
        }

        if(commentRequestDto.getSecret() != null) {
            comment.setIsSecret(SecretStatus.Y);
        }

        commentRepository.save(comment);

        return new ApiResponseDto("댓글 등록 성공", HttpStatus.OK.value());
    }

    @Transactional
    @Override
    public ApiResponseDto updateComment(Long commentId, User user, CommentRequestDto commentRequestDto) {
        Comment comment = getCommentById(commentId);

        if(comment.getWriter().getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }

        comment.setContent(commentRequestDto.getContent());

        return new ApiResponseDto("댓글 수정 성공", HttpStatus.OK.value());
    }

    @Transactional
    @Override
    public ApiResponseDto deleteComment(Long commentId, User user) {
        Comment comment = getCommentById(commentId);

        if(comment.getWriter().getUserId() != user.getUserId()) {
            throw new IllegalArgumentException("댓글 작성자가 아닙니다.");
        }

        if(comment.getChildren().size() != 0) {
            comment.setIsDeleted(DeleteStatus.Y);
        } else {
            commentRepository.delete(getDeletableAncestorComment(comment));
        }

        return new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value());

    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }

    public Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted().equals(DeleteStatus.Y)) {
            return getDeletableAncestorComment(parent);
        }
        return comment;
    }

    public void convertComment(CommentResponseDto commentResponseDto, SecretStatus secretStatus, DeleteStatus deleteStatus, User writer, Post post, UserDetailsImpl connectUser) {
        // 비밀댓글 여부 체크
        if(checkSecretComment(writer, post, connectUser, secretStatus)) {
            commentResponseDto.setContent("비밀댓글입니다.");
        }

        // 댓글삭제 여부 체크
        if(checkDeleteComment(deleteStatus)) {
            commentResponseDto.setContent("삭제된 댓글입니다.");
        }

    }

    public Boolean checkSecretComment(User writer, Post post, UserDetailsImpl connectUser, SecretStatus secretStatus) {
        return secretStatus.equals(SecretStatus.Y) ?
                connectUser == null ?
                        true :
                        connectUser.getUser().getUserId().equals(writer.getUserId()) ||
                            connectUser.getUser().getUserId().equals(post.getUser().getUserId()) ?
                                // ADMIN USER TABLE에서 접속유저와 같은지 검사해야함.
                                // connectUser.getUser().getUserId().equals(ADMIN) ?
                                false :
                                true :
                false;
    }

    public Boolean checkDeleteComment(DeleteStatus deleteStatus) {
        return deleteStatus.equals(DeleteStatus.Y) ?
                true :
                false;
    }
}

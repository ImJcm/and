package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.CommentRequestDto;
import com.sparta.and.dto.response.CommentResponseDto;
import com.sparta.and.entity.Comment;
import com.sparta.and.entity.DeleteStatus;
import com.sparta.and.entity.Post;
import com.sparta.and.entity.User;
import com.sparta.and.repository.CommentRepository;
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
    public List<CommentResponseDto> getComments(Long postId) {
        Post post = postService.findPost(postId);

        List<Comment> comments = commentRepository.getCommentListFindByPostId(postId)
                .stream()
                .toList();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        Map<Long, CommentResponseDto> commentResponseDtoHashMap = new HashMap<>();

        comments.forEach(c -> {
            CommentResponseDto commentResponseDto = CommentResponseDto.convertCommentToDto(c);
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
                .parent(null)
                .post(post)
                .build();

        if(commentRequestDto.getParentId() != null) {
            Comment parentComment = getCommentById(commentRequestDto.getParentId());
            comment.setParent(parentComment);
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

    private Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParent();
        if(parent != null && parent.getChildren().size() == 1 && parent.getIsDeleted().equals(DeleteStatus.Y)) {
            return getDeletableAncestorComment(parent);
        }
        return comment;
    }
}

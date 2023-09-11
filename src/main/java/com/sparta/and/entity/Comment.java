package com.sparta.and.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long id;

	@Column(nullable = false)
	@Lob
	private String content;

    @Column
    private Long step;

	@Enumerated(value = EnumType.STRING)
	private DeleteStatus isDeleted;

    @Enumerated(value = EnumType.STRING)
    private SecretStatus isSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<ReportComment> reportCommentList = new ArrayList<>();

    @Builder
    public Comment(String content, Long step, DeleteStatus deleteStatus, SecretStatus secretStatus, User writer, Post post, Comment parent) {
        this.content = content;
        this.step = step;
        this.isDeleted = deleteStatus;
        this.isSecret = secretStatus;
        this.writer = writer;
        this.post = post;
        this.parent = parent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    public void setIsDeleted(DeleteStatus deleteStatus) {
        this.isDeleted = deleteStatus;
    }

    public void setIsSecret(SecretStatus secretStatus) {
        this.isSecret = secretStatus;
    }

    public void setStep(Long step) {
        this.step = step;
    }
}

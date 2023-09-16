package com.sparta.and.entity;

import com.sparta.and.dto.request.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@Table(name = "Board")
@DynamicInsert
@NoArgsConstructor
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MiddleCategory categoryId;

    @Column(name = "noticeViews")
    private Long noticeViews = 0L;

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Board(MiddleCategory categoryId, BoardRequestDto requestDto) {
        this.categoryId = categoryId;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}

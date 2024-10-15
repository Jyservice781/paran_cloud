package com.paranmanzang.groupservice.model.domain;

import com.paranmanzang.groupservice.model.entity.LikeBooks;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeBookModel {
    private long id;
    @NotBlank(message = "닉네임은 필수 값입니다..")
    private String nickname;
    @NotBlank(message = "도서 아이디는 필수값입니다.")
    private long bookId;
    private String title;
    private String author;
    private String categoryName;
    private int likeBookCount;

    public static LikeBookModel fromEntity(LikeBooks entity) {
        return LikeBookModel.builder()
                .id(entity.getId())
                .nickname(entity.getNickname())
                .bookId(entity.getBook() != null ? entity.getBook().getId() : 0L)
                .title(entity.getBook() != null ? entity.getBook().getTitle() : null)
                .author(entity.getBook() != null ? entity.getBook().getAuthor() : null)
                .categoryName(entity.getBook() != null ? entity.getBook().getCategoryName() : null)
                .likeBookCount(entity.getBook() != null ? entity.getBook().getLike_books().size() : 0)
                .build();
    }
}

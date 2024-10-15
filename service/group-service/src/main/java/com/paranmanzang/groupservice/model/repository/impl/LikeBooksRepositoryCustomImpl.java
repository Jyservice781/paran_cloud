package com.paranmanzang.groupservice.model.repository.impl;

import com.paranmanzang.groupservice.model.domain.LikeBookModel;
import com.paranmanzang.groupservice.model.repository.LikeBooksRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.paranmanzang.groupservice.model.entity.QBook.book;
import static com.paranmanzang.groupservice.model.entity.QLikeBooks.likeBooks;

@RequiredArgsConstructor
public class LikeBooksRepositoryCustomImpl implements LikeBooksRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<LikeBookModel> findLikeBooksByNickname(String nickname) {
        var ids = queryFactory
                .select(likeBooks.id)
                .from(likeBooks)
                .where(likeBooks.nickname.eq(nickname))
                .fetch();

        return ids.isEmpty() ? List.of() :
                queryFactory
                        .select(Projections.constructor(
                                LikeBookModel.class,
                                likeBooks.id,
                                likeBooks.nickname,
                                likeBooks.book.id.as("bookId"),
                                likeBooks.book.title.as("title"),
                                likeBooks.book.author.as("author"),
                                likeBooks.book.categoryName.as("categoryName"),
                                likeBooks.book.like_books.size().as("likeBookCount")
                        ))
                        .from(likeBooks)
                        .leftJoin(likeBooks.book, book)
                        .where(likeBooks.id.in(ids))
                        .fetch();
    }
}

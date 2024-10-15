package com.paranmanzang.userservice.model.repository.Impl;

import com.paranmanzang.userservice.model.domain.LikePostModel;
import com.paranmanzang.userservice.model.entity.QLikePosts;
import com.paranmanzang.userservice.model.repository.custom.LikePostRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikePostRepositoryImpl implements LikePostRepositoryCustom {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<LikePostModel> findLikePostByNickname(String nickname) {
        QLikePosts likePosts = QLikePosts.likePosts;
        //id
        var likePostsIds = jpaQueryFactory
                .select(likePosts.id)
                .from(likePosts)
                .where(likePosts.nickname.eq(nickname))
                .fetch();
        //리스트
        return likePostsIds.isEmpty() ? List.of() :
                jpaQueryFactory
                        .select(Projections.constructor(
                                LikePostModel.class,
                                likePosts.id,
                                likePosts.postId,
                                likePosts.nickname
                        ))
                        .from(likePosts)
                        .where(likePosts.id.in(likePostsIds))
                        .fetch();


    }
}

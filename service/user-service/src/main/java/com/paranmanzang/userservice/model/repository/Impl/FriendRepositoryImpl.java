package com.paranmanzang.userservice.model.repository.Impl;


import com.paranmanzang.userservice.model.domain.FriendModel;
import com.paranmanzang.userservice.model.domain.LikePostModel;
import com.paranmanzang.userservice.model.entity.Friends;
import com.paranmanzang.userservice.model.entity.QFriends;
import com.paranmanzang.userservice.model.entity.QLikePosts;
import com.paranmanzang.userservice.model.repository.custom.FriendRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class FriendRepositoryImpl implements FriendRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<FriendModel> findFriendByRequestUser(String nickname) {
        QFriends friends = QFriends.friends;
        var friendIds = jpaQueryFactory
                .select(friends.id)
                .from(friends)
                .where(friends.requestUser.eq(nickname)
                    .or(friends.responseUser.eq(nickname)))
                .fetch();

        return friendIds.isEmpty()? List.of() :
                jpaQueryFactory
                        .select(Projections.constructor(
                                FriendModel.class,
                                friends.id,
                                friends.requestUser,
                                friends.responseUser,
                                friends.request_at,
                                friends.response_at))
                        .from(friends)
                        .where(friends.id.in(friendIds))
                        .fetch();
    }

    @Override
    public Boolean existsByRequestUserAndResponseUser(String requestUser, String responseUser){
        log.info("Request User: {}, Response User: {}", requestUser, responseUser);
        QFriends friends = QFriends.friends;
        var friendIds = jpaQueryFactory
                .select(friends.id)
                .from(friends)
                .where(friends.requestUser.eq(requestUser)
                        .and(friends.responseUser.eq(responseUser))
                ).fetch();
        System.out.println(friendIds.size());
        System.out.println(friendIds);
        System.out.println(friendIds.isEmpty());
        return !friendIds.isEmpty();
    }

}

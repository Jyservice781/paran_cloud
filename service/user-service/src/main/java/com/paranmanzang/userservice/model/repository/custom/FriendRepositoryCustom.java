package com.paranmanzang.userservice.model.repository.custom;

import com.paranmanzang.userservice.model.domain.FriendModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepositoryCustom {
    List<FriendModel> findFriendByRequestUser(String requestUser);

    Boolean existsByRequestUserAndResponseUser(String requestUser, String responseUser);
}

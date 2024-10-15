package com.paranmanzang.userservice.model.repository.custom;

import com.paranmanzang.userservice.model.domain.LikeRoomModel;

import java.util.List;


public interface LikeRoomRepositoryCustom {
    List<LikeRoomModel> findLikeRoomByNickname(String nickname);
}

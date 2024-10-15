package com.paranmanzang.userservice.model.repository.custom;

import com.paranmanzang.userservice.model.domain.LikePostModel;

import java.util.List;

public interface LikePostRepositoryCustom {
    List<LikePostModel> findLikePostByNickname(String nickname);
}

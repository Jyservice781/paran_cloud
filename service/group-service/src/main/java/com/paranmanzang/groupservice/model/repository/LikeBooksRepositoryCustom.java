package com.paranmanzang.groupservice.model.repository;

import com.paranmanzang.groupservice.model.domain.LikeBookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LikeBooksRepositoryCustom {
    List<LikeBookModel> findLikeBooksByNickname(String nickname);

}

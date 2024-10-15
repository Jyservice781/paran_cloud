package com.paranmanzang.userservice.service;


import com.paranmanzang.userservice.model.domain.LikePostModel;
import com.paranmanzang.userservice.model.domain.LikeRoomModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikePostService {
    //좋아요
    Object insert(LikePostModel likePostModel);

    //좋아요 취소
    boolean remove(LikePostModel likePostModel);

    //마이페이지에서 조회
    List<LikePostModel> findAllByNickname(String nickname);

/*    //마이페이지에서 삭제
    boolean removeLikeById(Long id);

    //토글 확인
    LikePostModel existsByNicknameAndPostId(String nickname , Long postId);*/
}

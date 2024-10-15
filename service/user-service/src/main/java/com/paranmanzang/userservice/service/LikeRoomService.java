package com.paranmanzang.userservice.service;


import com.paranmanzang.userservice.model.domain.LikeRoomModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LikeRoomService {
    //좋아요
    Object insert(LikeRoomModel likeRoomModel);

    //좋아요 취소
    boolean remove(LikeRoomModel likeRoomModel);

    //마이페이지 공간 찜 조회
    List<LikeRoomModel> findAllByUserNickname(String nickname);

/*    //마이페이지 공간 찜 삭제
    boolean removeLikeById(Long id);

    //공간 찜 확인
    LikeRoomModel existsByUserIdAndRoomId(String nickname , Long roomId);*/
}

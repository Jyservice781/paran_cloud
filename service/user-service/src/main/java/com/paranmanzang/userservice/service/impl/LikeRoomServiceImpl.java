package com.paranmanzang.userservice.service.impl;



import com.paranmanzang.userservice.model.domain.LikeRoomModel;
import com.paranmanzang.userservice.model.entity.LikeRooms;
import com.paranmanzang.userservice.model.repository.LikeRoomRepository;
import com.paranmanzang.userservice.service.LikeRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class LikeRoomServiceImpl implements LikeRoomService {
    private final LikeRoomRepository likeRoomRepository;

    public LikeRoomServiceImpl(LikeRoomRepository likeRoomRepository ) {
        this.likeRoomRepository = likeRoomRepository;
    }

    @Override
    public List<LikeRoomModel> findAllByUserNickname(String userNickname) {
        return likeRoomRepository.findLikeRoomByNickname(userNickname);
    }


    @Override
    public Object insert(LikeRoomModel likeRoomModel) {
        String nickname = likeRoomModel.getNickname();
        Long roomId = likeRoomModel.getRoomId();

        try {
            if (likeRoomRepository.existsByNicknameAndRoomId(nickname, roomId)) {
                return false; // 이미 좋아요를 눌렀으면 false 반환
            }

            LikeRooms likeRooms = new LikeRooms();
            likeRooms.setRoomId(roomId);
            likeRooms.setNickname(nickname);
            return LikeRoomModel.fromEntity(likeRoomRepository.save(likeRooms));

        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;

        } catch (IllegalArgumentException e) {
            // 비즈니스 로직 예외 처리
            System.err.println("비즈니스 로직 오류 발생: " + e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean remove(LikeRoomModel likeRoomModel) {
        try {
            LikeRooms likeRooms = likeRoomRepository.findByNicknameAndRoomId(likeRoomModel.getNickname(), likeRoomModel.getRoomId());
            if (likeRooms != null) {
                likeRoomRepository.deleteByNicknameAndRoomId(likeRoomModel.getNickname(), likeRoomModel.getRoomId());
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

    /*@Override
    public boolean removeLikeById(Long id) {
        try {
            if (!likeRoomRepository.existsById(id)) {
                throw new IllegalArgumentException("해당 좋아요 정보가 존재하지 않습니다.");
            }
            likeRoomRepository.deleteById(id);
            return !likeRoomRepository.existsById(id);
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            // 비즈니스 로직 예외 처리
            System.err.println("비즈니스 로직 오류 발생: " + e.getMessage());
            return false;
        }
    }

    @Override
    public LikeRoomModel existsByUserIdAndRoomId(String nickname, Long roomId) {
        try {
            LikeRooms likeRooms = likeRoomRepository.findByNicknameAndRoomId(nickname, roomId);
            if (likeRooms != null) {
                return new LikeRoomModel(likeRooms.getId(), likeRooms.getRoomId(), likeRooms.getNickname());
            }
            return null;
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return null;
        }
    }*/
}

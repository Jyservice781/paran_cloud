package com.paranmanzang.userservice.service.impl;

import com.paranmanzang.userservice.model.domain.FriendModel;
import com.paranmanzang.userservice.model.entity.Friends;
import com.paranmanzang.userservice.model.repository.FriendRepository;
import com.paranmanzang.userservice.service.FriendService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FriendServiceImpl implements FriendService {
    private final FriendRepository friendRepository;

    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    // 친구 추가
    @Override
    public Object insert(FriendModel friendModel) {
        try {
            System.out.println("friend1"+ friendModel.getRequestUser() +"friend2" + friendModel.getResponseUser());
            log.info("friend1 {},friend2 {} ", friendModel.getRequestUser(), friendModel.getResponseUser());
            Boolean isFriendRequestExist1 = friendRepository.existsByRequestUserAndResponseUser(friendModel.getRequestUser(), friendModel.getResponseUser());
            Boolean isFriendRequsetExist2 = friendRepository.existsByRequestUserAndResponseUser(friendModel.getResponseUser(), friendModel.getRequestUser());
            if (isFriendRequestExist1) {
                throw new IllegalArgumentException("이미 친구 요청이 존재합니다.");
            }
            else if (isFriendRequsetExist2) {
                throw new IllegalArgumentException("이미 친구 요청이 존재합니다.");
            }
            if(friendModel.getRequestUser().equals(friendModel.getResponseUser())) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }
            // 유저 조회 및 친구 추가
            Friends friends = friendRepository.save(Friends.builder()
                    .responseUser(friendModel.getResponseUser())
                    .requestUser(friendModel.getRequestUser())
                    .response_at(LocalDateTime.now()) // response_at에 현재 시간 저장
                    .build());

            return FriendModel.fromEntity(friends);
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

    // 친구 삭제
    @Override
    @Transactional
    public boolean remove(Long id) {
        try {
            if (!friendRepository.existsById(id)) {
                throw new IllegalArgumentException("해당 친구 요청이 존재하지 않습니다.");
            }
            friendRepository.deleteById(id);
            return true;
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

    // 내 친구 리스트 조회
    @Override
    public List<FriendModel> findAllByNickname(String nickname) {
        try {
            return friendRepository.findFriendByRequestUser(nickname);
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return List.of(); // 빈 리스트 반환
        }
    }
}

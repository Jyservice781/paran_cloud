package com.paranmanzang.userservice.service.impl;


import com.paranmanzang.userservice.model.domain.LikePostModel;
import com.paranmanzang.userservice.model.domain.LikeRoomModel;
import com.paranmanzang.userservice.model.entity.LikePosts;
import com.paranmanzang.userservice.model.repository.LikePostRepository;
import com.paranmanzang.userservice.service.LikePostService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikePostServiceImpl implements LikePostService {
    private final LikePostRepository likePostRepository;

    public LikePostServiceImpl(LikePostRepository likePostRepository) {
        this.likePostRepository = likePostRepository;
    }
    @Override
    public List<LikePostModel> findAllByNickname(String userNickname) {
        return likePostRepository.findLikePostByNickname(userNickname);
    }
    @Override
    public Object insert(LikePostModel likePostModel) {
        String nickname = likePostModel.getNickname();
        Long postId = likePostModel.getPostId();

        try {
            if (likePostRepository.existsByNicknameAndPostId(nickname, postId)) {
                return false; // 이미 좋아요를 눌렀으면 false 반환
            }

            LikePosts likePosts = new LikePosts();
            likePosts.setPostId(postId);
            likePosts.setNickname(nickname);
            return LikePostModel.fromEntity(likePostRepository.save(likePosts));
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
    public boolean remove(LikePostModel likePostModel) {
        try {
            LikePosts likePosts = likePostRepository.findByNicknameAndPostId(likePostModel.getNickname(), likePostModel.getPostId());
            if (likePosts != null) {
                likePostRepository.deleteByNicknameAndPostId(likePostModel.getNickname(), likePostModel.getPostId());
                return true;
            }
            return false;
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return false;
        }
    }

/*    @Override
    public boolean removeLikeById(Long id) {
        try {
            if (!likePostRepository.existsById(id)) {
                throw new IllegalArgumentException("해당 좋아요 정보가 존재하지 않습니다.");
            }
            likePostRepository.deleteById(id);
            return !likePostRepository.existsById(id);
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
    public LikePostModel existsByNicknameAndPostId(String nickname, Long postId) {
        try {
            LikePosts likePosts = likePostRepository.findByUserNicknameAndPostId(nickname, postId);
            if (likePosts != null) {
                return new LikePostModel(likePosts.getId(), likePosts.getPostId(), likePosts.getUser().getId());
            }
            return null;
        } catch (DataAccessException e) {
            // 데이터베이스 접근 예외 처리
            System.err.println("데이터베이스 접근 중 오류 발생: " + e.getMessage());
            return null;
        }
    }*/
}

package com.paranmanzang.userservice.model.repository;

import com.paranmanzang.userservice.model.entity.LikePosts;
import com.paranmanzang.userservice.model.repository.custom.LikePostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikePostRepository extends JpaRepository<LikePosts,Long>, LikePostRepositoryCustom {
    // 사용자 닉네임과 룸 ID로 LikePosts 조회
    LikePosts findByNicknameAndPostId(String nickname, Long postId);

    // 사용자 낙네임과 룸 ID로 LikePost 삭제
    int deleteByNicknameAndPostId(String nickname, Long postId);

    // 사용자 닉네임과 룸 ID로 LikePost 조회
    Boolean existsByNicknameAndPostId(String nickname, Long postId);

}

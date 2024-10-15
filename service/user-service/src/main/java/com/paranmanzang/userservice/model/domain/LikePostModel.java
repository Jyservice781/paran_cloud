package com.paranmanzang.userservice.model.domain;

import com.paranmanzang.userservice.model.entity.LikePosts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="게시물 좋아요")

public class LikePostModel {
    @Schema(title="좋아요 id")
    private Long id;
    @Schema(title="게시물 id")
    private Long postId;
    @Schema(title="사용자 닉네임")
    private String nickname;

    public static LikePostModel fromEntity(LikePosts likeposts) {
        return LikePostModel.builder()
                .id(likeposts.getId())
                .postId(likeposts.getPostId())
                .nickname(likeposts.getNickname())
                .build();
    }
}

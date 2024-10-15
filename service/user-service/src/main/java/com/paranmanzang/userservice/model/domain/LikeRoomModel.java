package com.paranmanzang.userservice.model.domain;

import com.paranmanzang.userservice.model.entity.LikeRooms;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title="공간 찜")

public class LikeRoomModel {
    @Schema(title="찜 id")
    private Long id;
    @Schema(title="공간 id")
    private Long roomId;
    @Schema(title="유저 닉네임")
    private String nickname;

    public static LikeRoomModel fromEntity(LikeRooms likeRooms) {
        return LikeRoomModel.builder()
                .id(likeRooms.getId())
                .roomId(likeRooms.getRoomId())
                .nickname(likeRooms.getNickname())
                .build();
    }
}

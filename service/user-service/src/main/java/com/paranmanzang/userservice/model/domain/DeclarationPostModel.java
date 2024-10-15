package com.paranmanzang.userservice.model.domain;

import com.paranmanzang.userservice.model.entity.DeclarationPosts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title="신고 게시판")
public class DeclarationPostModel {
    @Schema(title="게시판 id")
    private Long id;
    @Schema(title="게시글 제목")
    private String title;
    @Schema(title="게시글 내용")
    private String content;
    @Schema(title="신고 대상자 닉네임")
    private String target;
    @Schema(title="신고자 닉네임")
    private String declarer;

    public static DeclarationPostModel fromEntity(DeclarationPosts declarationPosts) {
        return DeclarationPostModel.builder()
                .id(declarationPosts.getId())
                .title(declarationPosts.getTitle())
                .content(declarationPosts.getContent())
                .target(declarationPosts.getTarget())
                .declarer(declarationPosts.getDeclarer())
                .build();
    }

}

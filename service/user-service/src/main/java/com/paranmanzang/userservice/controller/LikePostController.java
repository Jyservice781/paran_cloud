package com.paranmanzang.userservice.controller;


import com.paranmanzang.userservice.model.domain.LikePostModel;
import com.paranmanzang.userservice.service.impl.LikePostServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Tag(name = "07. LikePost")
@RequestMapping("/api/users/likeposts")
public class LikePostController {

    private final LikePostServiceImpl likePostService;

    public LikePostController(LikePostServiceImpl likePostService) {
        this.likePostService = likePostService;
    }

    //좋아요
    @PostMapping
    @Operation(summary = "게시물 좋아요", description="게시물 좋아요 추가", tags={"07. LikePost"})
    public ResponseEntity<?> insert(@RequestBody LikePostModel likepostModel) {
        return ResponseEntity.ok(likePostService.insert(likepostModel));
    }
    //좋아요 취소
    @DeleteMapping
    @Operation(summary = "게시물 좋아요 취소", description="게시물 좋아요 취소")
    public ResponseEntity<?> remove(@RequestBody LikePostModel likepostModel) {
        return ResponseEntity.ok(likePostService.remove(likepostModel));
    }


    //좋아요 마이페이지 확인
    @GetMapping("/{nickname}")
    @Operation(summary = "마이페이지에서 좋아요 확인", description="마이페이지에서 좋아요 누른 게시물을 전부 확인할 수 있습니다.")
    public ResponseEntity<?> findAllByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(likePostService.findAllByNickname(nickname));
    }

/*    //좋아요 토글 확인
    @GetMapping("/{nickname}/{postId}")
    @Operation(summary = "게시물 좋아요 확인", description="토글로 좋아요를 눌렀는지 확인")
    public ResponseEntity<?> getLikeRoom(@PathVariable String nickname, @PathVariable Long postId) {
        return ResponseEntity.ok(likePostService.existsByNicknameAndPostId(nickname, postId));
    }


    //좋아요 마이페이지 취소
    @DeleteMapping("/{likeRoomId}")
    @Operation(summary = "좋아요 취소", description="마이페이지에서 누른 게시물 좋아요를 취소할 수 있습니다.")
    public ResponseEntity<?> removeLikeRoom(@PathVariable Long likeRoomId) {
        likePostService.removeLikeById(likeRoomId);
        return ResponseEntity.ok("remove");
    }*/
}

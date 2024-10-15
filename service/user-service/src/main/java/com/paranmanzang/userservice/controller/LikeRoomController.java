package com.paranmanzang.userservice.controller;

import com.paranmanzang.userservice.model.domain.LikeRoomModel;
import com.paranmanzang.userservice.service.impl.LikeRoomServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "08. LikeRoom")
@RequestMapping("/api/users/likerooms")
@RequiredArgsConstructor
public class LikeRoomController {
    private final LikeRoomServiceImpl likeRoomService;

    //좋아요
    @PostMapping
    @Operation(summary = "방 찜하기", description="방 찜하기", tags={"08. LikeRoom"})
    public ResponseEntity<?> insert(@RequestBody LikeRoomModel likeRoomModel) {
        return ResponseEntity.ok(likeRoomService.insert(likeRoomModel));
    }
    //좋아요 취소
    @DeleteMapping
    @Operation(summary = "방 찜 취소", description="찜한 방을 취소합니다.")
    public ResponseEntity<?> remove(@RequestBody LikeRoomModel likeRoomModel) {
        return ResponseEntity.ok(likeRoomService.remove(likeRoomModel));
    }

    //좋아요 마이페이지 확인
    @GetMapping("/{nickname}")
    @Operation(summary = "방 찜 확인", description="찜하기 누른 방을 마이페이지에서 확인합니다.")
    public ResponseEntity<?> findAllByUserNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(likeRoomService.findAllByUserNickname(nickname));
    }
/*    //좋아요 토글 확인
    @GetMapping("/{nickname}/{roomId}")
    @Operation(summary = "방 찜하기 확인", description="토글로 방의 찜 상태를 확인합니다.")
    public ResponseEntity<?> getLikeRoom(@PathVariable String nickname, @PathVariable Long roomId) {
        return ResponseEntity.ok(likeRoomService.existsByUserIdAndRoomId(nickname, roomId));
    }
    //좋아요 마이페이지 취소
    @DeleteMapping("/{likeRoomId}")
    @Operation(summary = "방 찜 취소", description="마이페이지에서 찜하기 누르 방을 취소합니다.")
    public ResponseEntity<?> removeLikeRoom(@PathVariable Long likeRoomId) {
        return ResponseEntity.ok(likeRoomService.removeLikeById(likeRoomId));
    }*/
}

package com.paranmanzang.userservice.controller;


import com.paranmanzang.userservice.model.domain.FriendModel;
import com.paranmanzang.userservice.model.entity.Friends;
import com.paranmanzang.userservice.service.impl.FriendServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "03. Friend")
@RequestMapping("/api/users/friends")
public class FriendController {
    private final FriendServiceImpl friendService;
    public FriendController(FriendServiceImpl friendService) {
        this.friendService = friendService;
    }

    //친구 등록
    @PostMapping
    @Operation(summary = "친구 등록", description = "친구 등록을 합니다. 해당 정보를 저장합니다.", tags = {"03. Friend"})
    public ResponseEntity<?> insert(@RequestBody FriendModel friendModel) {
        return ResponseEntity.ok(friendService.insert(friendModel));
    }
    //친구 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "친구 삭제", description = "입력받은 id를 통해 해당 친구를 삭제합니다.")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        return ResponseEntity.ok(friendService.remove(id));
    }
    //친구 목록 조회
    @GetMapping("/{nickname}")
    @Operation(summary = "친구 리스트 조회", description = "친구의 목록을 조회합니다.")
    public ResponseEntity<?> findAllByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(friendService.findAllByNickname(nickname));
    }
}


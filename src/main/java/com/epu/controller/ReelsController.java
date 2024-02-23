package com.epu.controller;

import com.epu.models.Reels;
import com.epu.models.User;
import com.epu.service.ReelsService;
import com.epu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController {

    @Autowired
    private ReelsService reelsService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/reels")
    public ResponseEntity<Reels> createReel(@RequestBody Reels reels, @RequestHeader("Authorization") String jwt) {
        User reqUser = userService.findUserByJwt(jwt);
        Reels createReels = reelsService.createReels(reels, reqUser);
        return new ResponseEntity<>(createReels, HttpStatus.OK);
    }

    @GetMapping("/api/reels")
    public List<Reels> findAllReel() {
        List<Reels> reels = reelsService.findAllReels();
        return reels;
    }

    @GetMapping("/api/reels/user/{userId}")
    public List<Reels> findUsersReels(@PathVariable Integer userId) throws Exception {
        List<Reels> reels = reelsService.findUsersReel(userId);
        return reels;
    }
}

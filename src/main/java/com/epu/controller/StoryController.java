package com.epu.controller;

import com.epu.models.Story;
import com.epu.models.User;
import com.epu.service.StoryService;
import com.epu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController {

    @Autowired
    private StoryService storyService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/story")
    public Story createStory(@RequestBody Story story, @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        Story createStory = storyService.createStory(story, user);
        return createStory;
    }

    @GetMapping("/api/story/user/{userId}")
    public List<Story> findUserStory(@PathVariable Integer userId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        List<Story> createStory = storyService.findStoryByUserId(userId);
        return createStory;
    }
}

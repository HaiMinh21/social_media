package com.epu.service;

import com.epu.models.Story;
import com.epu.models.User;
import com.epu.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImpl implements StoryService{

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Story createStory(Story story, User user) {
        Story createStory = new Story();
        createStory.setCaption(story.getCaption());
        createStory.setImage(story.getImage());
        createStory.setUser(user);
        createStory.setTimeStamp(LocalDateTime.now());
        return storyRepository.save(createStory);
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws Exception {
        userService.findUserById(userId);
        return storyRepository.findByUserId(userId);
    }
}

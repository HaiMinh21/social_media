package com.epu.service;

import com.epu.models.Story;
import com.epu.models.User;

import java.util.List;

public interface StoryService {

    public Story createStory(Story story, User user);

    public List<Story> findStoryByUserId(Integer userId) throws Exception;

}

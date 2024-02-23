package com.epu.service;

import com.epu.models.Reels;
import com.epu.models.User;
import com.epu.repository.ReelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelsServiceImpl implements ReelsService{

    @Autowired
    private ReelRepository reelRepository;

    @Autowired
    private UserService userService;

    @Override
    public Reels createReels(Reels reels, User user) {
        Reels createReel = new Reels();
        createReel.setTitle(reels.getTitle());
        createReel.setVideo(reels.getVideo());
        createReel.setUser(user);
        return reelRepository.save(createReel);
    }

    @Override
    public List<Reels> findAllReels() {
        return reelRepository.findAll();
    }

    @Override
    public List<Reels> findUsersReel(Integer userId) throws Exception {
        userService.findUserById(userId);
        return reelRepository.findByUserId(userId);
    }
}

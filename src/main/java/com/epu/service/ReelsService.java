package com.epu.service;

import com.epu.models.Reels;
import com.epu.models.User;

import java.util.List;

public interface ReelsService {

    Reels createReels(Reels reels, User user);

    List<Reels> findAllReels();

    List<Reels> findUsersReel(Integer userId) throws Exception;
}

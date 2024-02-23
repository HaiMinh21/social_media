package com.epu.repository;

import com.epu.models.Reels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelRepository extends JpaRepository<Reels, Integer> {

    public List<Reels> findByUserId(Integer userId);
}

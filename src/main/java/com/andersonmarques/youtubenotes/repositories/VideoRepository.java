package com.andersonmarques.youtubenotes.repositories;

import com.andersonmarques.youtubenotes.models.Video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

}

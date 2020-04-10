package com.andersonmarques.youtubenotes.repositories;

import java.util.List;

import com.andersonmarques.youtubenotes.models.Video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

	@Query(value = "SELECT * FROM VIDEO V WHERE V.USER_ID = ?1", nativeQuery = true)
	public List<Video> findAllByUserId(int userId);
}

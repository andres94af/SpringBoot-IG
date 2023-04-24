package com.instagram.service;

import java.util.List;
import java.util.Optional;

import com.instagram.model.Like;

public interface ILikeService{

	Optional<Like> findById(Integer id);
	List<Like> findAll();
	Like save(Like like);
	void delete (Integer id);
}

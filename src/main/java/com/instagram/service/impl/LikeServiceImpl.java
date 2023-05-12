package com.instagram.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Like;
import com.instagram.repository.ILikeRepository;
import com.instagram.service.ILikeService;

@Service
public class LikeServiceImpl implements ILikeService{
	
	@Autowired
	ILikeRepository likeRepo;

	@Override
	public Optional<Like> findById(Integer id) {
		return likeRepo.findById(id);
	}

	@Override
	public List<Like> findAll() {
		return likeRepo.findAll();
	}

	@Override
	public Like save(Like like) {
		return likeRepo.save(like);
	}

	@Override
	public void delete(Integer id) {
		likeRepo.deleteById(id);
	}

}

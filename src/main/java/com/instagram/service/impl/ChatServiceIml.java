package com.instagram.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.model.Chat;
import com.instagram.repository.IChatRepository;
import com.instagram.service.IChatService;

@Service
public class ChatServiceIml implements IChatService {

	@Autowired
	IChatRepository chatRepo;

	@Override
	public Chat save(Chat chat) {
		return chatRepo.save(chat);
	}

	@Override
	public void delete(Integer id) {
		chatRepo.deleteById(id);
	}

	@Override
	public Optional<Chat> findById(Integer id) {
		return chatRepo.findById(id);
	}

	@Override
	public List<Chat> findAll() {
		return chatRepo.findAll();
	}

}

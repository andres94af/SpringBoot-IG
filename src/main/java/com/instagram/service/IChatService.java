package com.instagram.service;

import java.util.List;
import java.util.Optional;

import com.instagram.model.Chat;

public interface IChatService {
	Chat save(Chat chat);

	void delete(Integer id);

	Optional<Chat> findById(Integer id);

	List<Chat> findAll();
}

package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Chat;

public interface IChatRepository extends JpaRepository<Chat, Integer>{

}

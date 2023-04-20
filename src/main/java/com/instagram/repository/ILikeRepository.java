package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Like;

public interface ILikeRepository extends JpaRepository<Like, Integer> {

}

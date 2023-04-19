package com.instagram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.model.Feed;

public interface IFeedRepository extends JpaRepository<Feed, Integer>{

}

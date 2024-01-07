package com.example.springdatajpa_hibernatedao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springdatajpa_hibernatedao.model.Author;

public interface AuthorRepo extends JpaRepository<Author, Long> {
	
}

package com.example.springdatajpa_hibernatedao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springdatajpa_hibernatedao.model.Book;

public interface BookRepo extends JpaRepository<Book, Long> {
	
}

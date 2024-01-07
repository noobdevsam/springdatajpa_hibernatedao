package com.example.springdatajpa_hibernatedao.dao;

import com.example.springdatajpa_hibernatedao.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {

	@Override
	public Author getById(Long id) {
		return null;
	}

	@Override
	public Author findAuthorByName(String name) {
		return null;
	}
	
	@Override
	public Author saveNewAuthor(Author author) {
		return null;
	}

	@Override
	public Author updateAuthor(Author author) {
		return null;
	}

	@Override
	public void deleteAuthorById(Long id) {
		
	}
}

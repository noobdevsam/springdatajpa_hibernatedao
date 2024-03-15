package com.example.springdatajpa_hibernatedao.dao;

import java.util.List;

import com.example.springdatajpa_hibernatedao.model.Author;

public interface AuthorDao {
	Author getById(Long id);
	Author findAuthorByName(String firstName, String lastName);
	Author saveNewAuthor(Author author);
	Author updateAuthor(Author author);
	void deleteAuthorById(Long id);
	List<Author> listAuthorByLastNameLike(String lastName);
	List<Author> findAll();
	Author findAuthorByNameCriteria(String firstName, String lastName);
}

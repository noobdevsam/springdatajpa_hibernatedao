package com.example.springdatajpa_hibernatedao.dao;

import com.example.springdatajpa_hibernatedao.model.Book;

public interface BookDao {
	Book getById(Long id);
	Book findBookByTitle(String title);
	Book saveNewBook(Book book);
	Book updateBook(Book book);
	void deleteBookById(Long id);
	Book findByISBN(String isbn);
	Book findBookByTitleCriteria(String title);
	Book findBookByTitleNative(String title);
}

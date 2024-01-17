package com.example.springdatajpa_hibernatedao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springdatajpa_hibernatedao.dao.*;
import com.example.springdatajpa_hibernatedao.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ComponentScan(basePackages = {"com.example.springdatajpa_hibernatedao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {

	@Autowired
	AuthorDaoImpl authorDaoImpl;

	@Autowired
	BookDaoImpl bookDaoImpl;

	@Test
	void test_get_author_by_id() {
		System.out.println("get-by-id");
		Author author = authorDaoImpl.getById(1L);
		assertThat(author).isNotNull();
	}

	@Test
	void test_find_author_by_name() {
		System.out.println("find-author-by-name");
		Author author = authorDaoImpl.findAuthorByName("Craig", "Walls");
		assertThat(author).isNotNull();
	}

	@Test
	void test_save_new_author() {
		System.out.println("save-new-author");
		Author author = new Author();
		author.setFirstName("Sam");
		author.setLastName("K");

		Author saved = authorDaoImpl.saveNewAuthor(author);

		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull();
	}

	@Test
	void test_update_author() {
		System.out.println("update-author");
		Author author = new Author();
		author.setFirstName("Sam");
		author.setLastName("Kn");
		Author saved = authorDaoImpl.saveNewAuthor(author);

		saved.setLastName("Knight");
		Author updated = authorDaoImpl.updateAuthor(saved);

		assertThat(updated.getLastName()).isEqualTo("Knight");
	}

	@Test
	void test_delete_author_by_id() {
		System.out.println("delete-author-by-id");
		Author author = new Author();
		author.setFirstName("Sam");
		author.setLastName("k");
		Author saved = authorDaoImpl.saveNewAuthor(author);

		authorDaoImpl.deleteAuthorById(saved.getId());

		Author deleted = authorDaoImpl.getById(saved.getId());
		assertThat(deleted).isNull();
		assertThat(authorDaoImpl.getById(saved.getId()));
	}

	@Test
	void test_get_book_by_id() {
		Book book = bookDaoImpl.getById(3L);
		assertThat(book).isNotNull();
	}

	@Test
	void test_find_book_by_title() {
		Book book = bookDaoImpl.findBookByTitle("Clean Code");
		assertThat(book.getTitle()).isEqualTo("Clean Code");
	}

	@Test
	void test_save_new_book() {
		Book book = new Book();
		book.setIsbn("48363628");
		book.setPublisher("Self");
		book.setTitle("Sgdhd");
		book.setAuthorId(1L);
		Book saved = bookDaoImpl.saveNewBook(book);
		assertThat(saved).isNotNull();
	}
	
	@Test
	void test_update_book() {
		Book book = new Book();
		book.setIsbn("48363628");
		book.setPublisher("Self");
		book.setTitle("Sgdhd");
		book.setAuthorId(1L);
		Book saved = bookDaoImpl.saveNewBook(book);

		saved.setTitle("my book");
		bookDaoImpl.updateBook(saved);

		Book fetched = bookDaoImpl.getById(saved.getId());
		assertThat(fetched.getTitle()).isEqualTo("my book");
	}

	@Test
	void test_delete_book_by_id() {
		Book book = new Book();
		book.setIsbn("48363628");
		book.setPublisher("Self");
		book.setTitle("Sgdhd");
		book.setAuthorId(1L);
		Book saved = bookDaoImpl.saveNewBook(book);

		bookDaoImpl.deleteBookById(saved.getId());
		Book dbook = bookDaoImpl.getById(saved.getId());
		assertThat(dbook).isNull();
	}
}

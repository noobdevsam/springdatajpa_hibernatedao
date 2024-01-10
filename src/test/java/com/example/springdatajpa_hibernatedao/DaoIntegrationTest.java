package com.example.springdatajpa_hibernatedao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.springdatajpa_hibernatedao.dao.*;
import com.example.springdatajpa_hibernatedao.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ComponentScan(basePackages = {"com.example.springdatajpa_hibernatedao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {

	@Autowired
	AuthorDaoImpl authorDaoImpl;

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
}

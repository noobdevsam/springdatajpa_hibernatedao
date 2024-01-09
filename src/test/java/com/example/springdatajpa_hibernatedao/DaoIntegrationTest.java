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
	void test_getauthorbyid() {
		Author author = authorDaoImpl.getById(1L);
		assertThat(author).isNotNull();
	}

	@Test
	void test_findauthorbyname() {
		Author author = authorDaoImpl.findAuthorByName("Craig", "Walls");
		assertThat(author).isNotNull();
	}
}

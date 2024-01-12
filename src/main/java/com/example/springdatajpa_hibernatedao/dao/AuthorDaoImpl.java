package com.example.springdatajpa_hibernatedao.dao;

import com.example.springdatajpa_hibernatedao.model.Author;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;

@Component
public class AuthorDaoImpl implements AuthorDao {

	private final EntityManagerFactory emf;

	public AuthorDaoImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Author getById(Long id) {
		EntityManager em = getEntityManager();
		Author author = em.find(Author.class, id);
		em.close();
		return author; /// here [.find(entity_object, primary_key)]
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {
		EntityManager em = getEntityManager();
		// Typed query with JQL
		TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a  " + "WHERE a.firstName = :first_name and a.lastName = :last_name", Author.class);
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);

		Author author = query.getSingleResult();
		em.close();
		return author;
	}
	
	@Override
	public Author saveNewAuthor(Author author) {
		EntityManager em = getEntityManager();

		// explicitly intialize and terminate transaction
		em.getTransaction().begin();
		em.persist(author);
		em.flush();
		em.getTransaction().commit();
		em.close();
		return author;
	}

	@Override
	public Author updateAuthor(Author author) {
		EntityManager em = getEntityManager();

		try {
			em.joinTransaction();
			em.merge(author);
			em.flush();
			em.clear();
			return em.find(Author.class, author.getId());
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteAuthorById(Long id) {
		EntityManager em = getEntityManager();

		em.getTransaction().begin();
		Author author = em.find(Author.class, id);
		em.remove(author);
		em.flush();
		em.getTransaction().commit();
		em.close();
	}

	// helper method to get entity-manager from entity-manager-factory
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}

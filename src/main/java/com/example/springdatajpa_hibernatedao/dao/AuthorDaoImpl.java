package com.example.springdatajpa_hibernatedao.dao;

import java.util.List;
import org.springframework.stereotype.Component;
import com.example.springdatajpa_hibernatedao.model.Author;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

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
		// TypedQuery<Author> query = em.createQuery("SELECT a FROM Author a " + "WHERE
		// a.firstName = :first_name and a.lastName = :last_name", Author.class);
		TypedQuery<Author> query = em.createNamedQuery("author_find_by_name", Author.class);
		query.setParameter("first_name", firstName);
		query.setParameter("last_name", lastName);

		Author author = query.getSingleResult();
		em.close();
		return author;
	}

	@Override
	public Author findAuthorByNameCriteria(String firstName, String lastName) {
		EntityManager em = getEntityManager();

		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);

			Root<Author> root = criteriaQuery.from(Author.class);

			ParameterExpression<String> fNameParam = criteriaBuilder.parameter(String.class);
			ParameterExpression<String> lNameParam = criteriaBuilder.parameter(String.class);

			Predicate fNPredicate = criteriaBuilder.equal(root.get("firstName"), fNameParam);
			Predicate lNPredicate = criteriaBuilder.equal(root.get("lastName"), lNameParam);

			criteriaQuery.select(root).where(criteriaBuilder.and(fNPredicate, lNPredicate));

			TypedQuery<Author> typedQuery = em.createQuery(criteriaQuery);
			typedQuery.setParameter(fNameParam, firstName);
			typedQuery.setParameter(lNameParam, lastName);

			return typedQuery.getSingleResult();
		} finally {
			em.close();
		}

		/*
		 * 1. **Method Signature**:
		 * - The method is named `findAuthorByNameCriteria`.
		 * - It takes two parameters: `firstName` (a String) and `lastName` (a String).
		 * - It returns an `Author` entity (assuming `Author` is an entity class).
		 * 
		 * 2. **Initialization**:
		 * - The method initializes an `EntityManager` (presumably obtained from a
		 * persistence context).
		 * 
		 * 3. **Criteria API Setup**:
		 * - The method creates a `CriteriaBuilder` from the `EntityManager`.
		 * - It creates a `CriteriaQuery<Author>` for querying `Author` entities.
		 * - It defines a `Root<Author>` (represents the root entity in the query) based
		 * on the `Author` class.
		 * 
		 * 4. **Parameter Expressions**:
		 * - Two parameter expressions (`fNameParam` and `lNameParam`) are created for
		 * the first name and last name.
		 * - These parameter expressions are placeholders for the actual values to be
		 * used in the query.
		 * 
		 * 5. **Predicates**:
		 * - Two predicates are created:
		 * - `fNPredicate`: Represents the condition `root.get("firstName") =
		 * fNameParam`.
		 * - `lNPredicate`: Represents the condition `root.get("lastName") =
		 * lNameParam`.
		 * - These predicates are combined using `criteriaBuilder.and()`.
		 * 
		 * 6. **Select Query**:
		 * - The query selects the `Author` entity (`criteriaQuery.select(root)`).
		 * - The `where` clause specifies that both `fNPredicate` and `lNPredicate` must
		 * be true.
		 * 
		 * 7. **Typed Query Execution**:
		 * - A `TypedQuery<Author>` is created from the `CriteriaQuery`.
		 * - The parameter values (`firstName` and `lastName`) are set using
		 * `typedQuery.setParameter()`.
		 * - The method executes the query and retrieves a single result using
		 * `typedQuery.getSingleResult()`.
		 * 
		 * 8. **Cleanup**:
		 * - The `EntityManager` is closed in a `finally` block to release resources.
		 * 
		 * Overall, this code snippet demonstrates how to construct a dynamic query
		 * using the Criteria API to find an `Author` entity by their first name and
		 * last name. It's a powerful way to create flexible and type-safe queries in
		 * Java when working with JPA (Java Persistence API).
		 */
	}
	
	@Override
	public Author findAuthorByNameNative(String firstName, String lastName) {
		EntityManager em = getEntityManager();
		try {
			Query query = em.createNativeQuery("SELECT * FROM author a WHERE a.first_name = ? and a.last_name = ?", Author.class);

			query.setParameter(1, firstName);
			query.setParameter(2, lastName);

			return (Author) query.getSingleResult();
		} finally {
			em.close();
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		EntityManager em = getEntityManager();

		try {
			Query query = em.createQuery("SELECT a from Author a WHERE a.lastName LIKE :last_name");
			query.setParameter("last_name", lastName + "%");
			List<Author> authors = query.getResultList();
			return authors;
		} finally {
			em.close();
		}
	}

	@Override
	public List<Author> findAll() {
		EntityManager em = getEntityManager();

		try {
			TypedQuery<Author> typedQuery = em.createNamedQuery("author_find_all", Author.class);
			/*
			 * here createNamedQuery(name, resultClass) and the value of 'name'
			 * must match with the value provided in the entity with @NamedQuery(name,
			 * query)
			 */
			return typedQuery.getResultList();
		} finally {
			em.close();
		}
	}

	// helper method to get entity-manager from entity-manager-factory
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}

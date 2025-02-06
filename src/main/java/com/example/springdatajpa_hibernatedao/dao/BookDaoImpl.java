package com.example.springdatajpa_hibernatedao.dao;

import org.springframework.stereotype.Component;
import com.example.springdatajpa_hibernatedao.model.Book;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

@Component
public class BookDaoImpl implements BookDao {

    private final EntityManagerFactory emf;

    public BookDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
	public Book getById(Long id) {
        EntityManager em = getEntityManager();
        Book book = em.find(Book.class, id);
        em.close();
        return book;
    }

    @Override
	public Book findBookByTitle(String title) {
        EntityManager em = getEntityManager();
        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class);
        query.setParameter("title", title);
        Book book = query.getSingleResult();
        em.close();
        return book;
    }

    @Override
    public Book findBookByTitleCriteria(String title) {
        EntityManager em = getEntityManager();

        try{
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);

            Root<Book> root = criteriaQuery.from(Book.class);

            ParameterExpression<String> titleParam = criteriaBuilder.parameter(String.class);

            Predicate tPredicate = criteriaBuilder.equal(root.get("title"), titleParam);
            
            criteriaQuery.select(root).where(tPredicate);

            TypedQuery<Book> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(titleParam, title);

            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Book findBookByTitleNative(String title) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNativeQuery("SELECT * FROM book b WHERE b.title = ?", Book.class);
            query.setParameter(1, title);

            return (Book) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
	public Book saveNewBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.flush();
        em.getTransaction().commit();
        em.close();
        return book;
    }

    @Override
	public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        Book savedBook = em.find(Book.class, book.getId());
        em.getTransaction().commit();
        em.close();
        return savedBook;
    }

    @Override
	public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn",Book.class);
            query.setParameter("isbn", isbn);
            var book = query.getSingleResult();
            return book;
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}

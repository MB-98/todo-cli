package com.mb.todo.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.Session;

import com.mb.todo.entity.Todo;
import com.mb.todo.util.HibernateUtil;

public class TodoDao {

	public Optional<Todo> get(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Optional<Todo> req = Optional.ofNullable(session.find(Todo.class, id));
		session.close();
		return req;
	}

	public List<Todo> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Todo> reqList = session.createQuery("FROM Todo t ", Todo.class).getResultList();
		session.close();
		return reqList;
	}
	public List<Todo> getAllNotDone() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		List<Todo> reqList = session.createQuery("FROM Todo t where done = false", Todo.class).getResultList();
		session.close();
		return reqList;
	}

	public void save(Todo r) {
		executeTransaction(entityManager -> entityManager.persist(r));

	}

	public void update(Todo r) {
		executeTransaction(entityManager -> entityManager.merge(r));

	}

	public void delete(Todo r) {
		executeTransaction(entityManager -> entityManager.remove(r));
	}

	private void executeTransaction(Consumer<EntityManager> action) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		EntityTransaction tx = session.getTransaction();
		try {
			tx.begin();
			action.accept(session);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}
}

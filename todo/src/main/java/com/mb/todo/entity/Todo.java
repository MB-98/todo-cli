package com.mb.todo.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "todo")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "todo", nullable = false)
	private String todo;

	@Column(name = "done", nullable = false)
	private Boolean done;

	@Column(name = "creationDate", nullable = false)
	private Timestamp creationDate;

	@Column(name = "dueDate")
	private Date dueDate;

	public Todo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Todo(String todo, Date dueDate) {
		super();
		this.todo = todo;
		this.done = false;
		this.creationDate = new Timestamp(System.currentTimeMillis());
		this.dueDate = dueDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todoText) {
		this.todo = todoText;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", todo=" + todo + ", done=" + done + ", creationDate=" + creationDate
				+ ", dueDate=" + dueDate + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationDate, done, dueDate, id, todo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		return Objects.equals(creationDate, other.creationDate) && Objects.equals(done, other.done)
				&& Objects.equals(dueDate, other.dueDate) && id == other.id && Objects.equals(todo, other.todo);
	}
	
	
	
	

	
}

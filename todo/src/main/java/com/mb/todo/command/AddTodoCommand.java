package com.mb.todo.command;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mb.todo.dao.TodoDao;
import com.mb.todo.entity.Todo;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "add", mixinStandardHelpOptions = true, version = "add 1.0", description = "Add a todo to your list.")
public class AddTodoCommand implements Runnable {

	@Parameters(description = "The text that describes your todo.")
	private String[] todoArray;

	@Option(names = { "-nd", "--nextDay" }, description = "Set due date next day.")
	private Boolean nDay = false; //TODO Widerholungen einbauen int statt boolean

	@Option(names = { "-nw", "--nextWeek" }, description = "Set due date next week.")
	private Boolean nWeek = false;

	@Option(names = { "-nm", "--nextMonth" }, description = "Set due date next month.")
	private Boolean nMonth = false;

	@Option(names = { "-ny", "--nextYear" }, description = "Set due date next year.")
	private Boolean nYear = false;

	@Option(names = { "-d", "--dueDate" }, description = "Set specific due date for your todo. (yyyy-MM-dd)")
	private String dateString;

	@Override
	public void run() {
		String todoText = setTodoText();
		if(todoText != null) {
			TodoDao todoDao = new TodoDao();
			List<Todo> todoList = new ArrayList<>();
			
			if (nDay || nWeek || nMonth || nYear || dateString != null) {
				
				java.sql.Date sqlDate = null;
				Calendar c = Calendar.getInstance();
				
				if (nDay) {
					Date date = new Date();
					c.setTime(date);
					c.add(Calendar.DATE, 1);
					date = (Date) c.getTime();
					sqlDate = new java.sql.Date(date.getTime());
					todoList.add(new Todo(todoText, sqlDate));
				}
				if (nWeek) {
					Date date = new Date();
					c.setTime(date);
					c.add(Calendar.DATE, 7);
					date = (Date) c.getTime();
					sqlDate = new java.sql.Date(date.getTime());
					todoList.add(new Todo(todoText, sqlDate));
				}
				if (nMonth) {
					Date date = new Date();
					c.setTime(date);
					c.add(Calendar.MONTH, 1);
					date = (Date) c.getTime();
					sqlDate = new java.sql.Date(date.getTime());
					todoList.add(new Todo(todoText, sqlDate));
				}
				if (nYear) {
					Date date = new Date();
					c.setTime(date);
					c.add(Calendar.YEAR, 1);
					date = (Date) c.getTime();
					sqlDate = new java.sql.Date(date.getTime());
					todoList.add(new Todo(todoText, sqlDate));
				}
				if (dateString != null) {
					sqlDate = java.sql.Date.valueOf(dateString);
					todoList.add(new Todo(todoText, sqlDate));
				}
				
			}
			else {
				todoList.add(new Todo(todoText, null));
			}
			
			todoList.forEach(e -> {
				todoDao.save(e);
				System.out.println("Added: "+ e);
			});
			
		}
		else{
			System.err.println("The todo cannot be empty!");
		}
	}

	private String setTodoText() {
		String todoText = null;
		StringBuffer sb = new StringBuffer();
		for (String word : todoArray) {
			sb.append(word + " ");
		}
		todoText = sb.toString();
		return todoText;
	}


}

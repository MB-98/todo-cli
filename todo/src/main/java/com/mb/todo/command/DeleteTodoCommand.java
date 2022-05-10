package com.mb.todo.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mb.todo.dao.TodoDao;
import com.mb.todo.entity.Todo;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


@Command(name = "delete", mixinStandardHelpOptions = true, version = "delete 1.0", description = "Delete your todo's.")
public class DeleteTodoCommand implements Runnable{

	@Parameters(description = "Id's of todo's to delete.")
	private String[] todoIdArray;

	@Option(names = { "-a", "--all" }, description = "delete all todo's.")
	private Boolean deleteAll = false;

	@Override
	public void run() {
		TodoDao todoDao = new TodoDao();
		if(deleteAll) {
			List<Todo> todoList = todoDao.getAll();
			todoList.forEach(e -> {
				todoDao.delete(e);
			});
			System.out.println("All todo's deleted.");
		}
		else if(todoIdArray != null) {
			List<Integer> idList = new ArrayList<>();
			
			for(String idString : todoIdArray) {
				try {
				Integer id = Integer.parseInt(idString);
				idList.add(id);
				} catch (NumberFormatException nfe) {
			       System.out.println("The Input "+ idString + "is not a valid id.");
			    }
			}
			
			idList.forEach(e -> {
				Optional<Todo> oTodoToPrint = todoDao.get(e);
				if(oTodoToPrint.isPresent()) {
					todoDao.delete(oTodoToPrint.get());
					System.out.println("Todo with id: "+ oTodoToPrint.get().getId() + " deleted.");
				}
				else {
					System.out.println("There is no todo with id: " + e+".");
				}
			});
		}
		else {
			 System.out.println("You have to enter todo id's or add option -a or --all to delete all todo's");
		}
		
	}
	
}

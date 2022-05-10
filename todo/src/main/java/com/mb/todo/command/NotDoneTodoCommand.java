package com.mb.todo.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mb.todo.dao.TodoDao;
import com.mb.todo.entity.Todo;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "notDone", mixinStandardHelpOptions = true, version = "notDone 1.0", description = "Set Todo's to not done.")
public class NotDoneTodoCommand implements Runnable{
	
	@Parameters(description = "Id's of todo's you not finished.")
	private String[] todoIdArray;

	@Option(names = { "-a", "--all" }, description = "Set status not done to all todo's.")
	private Boolean doneAll = false;

	@Override
	public void run() {
		TodoDao todoDao = new TodoDao();
		if(doneAll) {
			List<Todo> todoList = todoDao.getAll();
			System.out.println(todoList.size());
			todoList.forEach(e -> {
				e.setDone(false);
				todoDao.update(e);
			});
			System.out.println("All todo's are not done.");
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
					Todo todo = oTodoToPrint.get();
					todo.setDone(false);
					todoDao.update(todo);
					System.out.println("Todo with id: "+ todo.getId() + " is not done.");
				}
				else {
					System.out.println("There is no todo with id: " + e+".");
				}
			});
			
		}
		else {
			 System.out.println("You have to enter todo id's or add option -a or --all to set all todo's to not done");
		}
		
	}

}

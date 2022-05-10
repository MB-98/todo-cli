package com.mb.todo.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mb.todo.dao.TodoDao;
import com.mb.todo.entity.Todo;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "get", mixinStandardHelpOptions = true, version = "get 1.0", description = "Print your todo's.")
public class GetTodoCommand implements Runnable{

	@Parameters(description = "Id's of todo's to print.")
	private String[] todoIdArray;

	@Option(names = { "-a", "--all" }, description = "print all todo's.")
	private Boolean printAll = false;
	
	//TODO sort Option

	@Override
	public void run() {
		TodoDao todoDao = new TodoDao();
		if(printAll) {
			List<Todo> todoList = todoDao.getAll();
			todoList.forEach(e -> System.out.println(e.toString()));
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
					 System.out.println(oTodoToPrint.get().toString());
				}
				else {
					System.out.println("There is no todo with id: " + e+".");
				}
			});
		}
		else {
			 System.out.println("You have to enter todo id's or add option -a or --all to get all todo's");
		}
		
		
	} 
	
	public static void main(String... args) {
		int exitCode = new CommandLine(new GetTodoCommand()).execute(args);
		System.exit(exitCode);
	}
}

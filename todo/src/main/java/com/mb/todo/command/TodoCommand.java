package com.mb.todo.command;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
  subcommands = {
		  AddTodoCommand.class,
		  DeleteTodoCommand.class,
		  GetTodoCommand.class,
		  DoneTodoCommand.class,
		  NotDoneTodoCommand.class
	  }
	)
public class TodoCommand  implements Runnable {
	
	public static void main(String... args) {
		int exitCode = new CommandLine(new TodoCommand()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public void run() {
		System.out.println("Todo App startet");
		
	}

}

package sg.edu.nus.iss.todolist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.todolist.Model.Todo;
import sg.edu.nus.iss.todolist.Service.DatabaseService;
import sg.edu.nus.iss.todolist.Service.TodoService;

@SpringBootApplication
public class TodolistApplication implements CommandLineRunner {

	@Autowired
	TodoService todoservice;

	@Autowired
	DatabaseService dbs;

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String filename = "todos.json";
		List<Todo> todolist = todoservice.readfile(filename);
		for (Todo todo : todolist) 
		{
			// System.out.println(todo);
			dbs.save(todo);
		}
	}
}

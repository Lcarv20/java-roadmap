package com.example.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TasksApiApplication {

	@Autowired
	private final TaskRepository taskRepository;
	public TasksApiApplication(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(TasksApiApplication.class, args);
	}


}

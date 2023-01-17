package com.example.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "tasks")
public class TasksController {

        @Autowired
        TaskRepository taskRepository;

        private HttpHeaders responseHeaders = new HttpHeaders();

        public TasksController() {
                responseHeaders.set("Content-Type", "application/json");
        }

        // List all tasks
        @GetMapping
        @ResponseBody
        List<Task> listTasks() {
                return taskRepository.findAll();
        }

        // Add task
        @PostMapping
        @ResponseBody
        ResponseEntity addTask(
                        @RequestBody UserRequest task) {
                if (task.description() == null || task.description().isEmpty()) {
                        return new ResponseEntity(
                                        new BadRequest(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        "I am sorry but you need to provide a description"),
                                        responseHeaders,
                                        HttpStatus.BAD_REQUEST);
                }

                Task newTask = new Task();
                newTask.setDescription(task.description());
                newTask.setPriority(task.priority());
                taskRepository.save(newTask);

                return new ResponseEntity(
                                task,
                                responseHeaders,
                                HttpStatus.OK);
        }

        // Update task
        @PutMapping("{taskId}")
        @ResponseBody
        public ResponseEntity editTask(
                        @PathVariable("taskId") long id,
                        @RequestBody UserRequest task) {
                if (task.description() == null || task.description().isEmpty()) {
                        return new ResponseEntity<BadRequest>(
                                        new BadRequest(
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        "I am sorry but you need to provide a description"),
                                        responseHeaders,
                                        HttpStatus.BAD_REQUEST);
                }

                Optional<Task> taskRef = taskRepository.findById(id);

                if (taskRef.isEmpty()) {
                        return new ResponseEntity<BadRequest>(
                                        new BadRequest(
                                                        HttpStatus.NOT_FOUND.value(),
                                                        "Cannot find task with given Id"),
                                        responseHeaders,
                                        HttpStatus.BAD_REQUEST);
                }

                Task updatedTask = taskRef.get();
                updatedTask.setPriority(task.priority());
                updatedTask.setDescription(task.description());
                updatedTask.setCompleted(task.completed());

                taskRepository.save(updatedTask);

                return new ResponseEntity(
                                task,
                                responseHeaders,
                                HttpStatus.OK);
        }

        // Complete task
        @GetMapping("complete/{taskId}")
        @ResponseBody
        public ResponseEntity completeTask(
                        @PathVariable("taskId") long id) {
                Optional<Task> task = taskRepository.findById(id);
                if (task.isEmpty()) {
                        return new ResponseEntity(
                                        new BadRequest(
                                                        HttpStatus.NOT_FOUND.value(),
                                                        "Cannot find task with given Id"),
                                        responseHeaders,
                                        HttpStatus.BAD_REQUEST);
                }

                Task updatableTask = task.get();
                updatableTask.setCompleted(true);
                taskRepository.save(updatableTask);

                return new ResponseEntity(
                                updatableTask,
                                responseHeaders,
                                HttpStatus.OK);
        }

        // get task by id
        @GetMapping("{taskId}")
        @ResponseBody
        public ResponseEntity getTaskById(
                        @PathVariable("taskId") long id) {
                Optional<Task> task = taskRepository.findById(id);

                if (task.isEmpty())
                        return ResponseEntity
                                        .status(HttpStatus.BAD_REQUEST)
                                        .body(
                                                        new BadRequest(HttpStatus.BAD_REQUEST.value(),
                                                                        "task with with provided id not found"));

                return new ResponseEntity<Task>(
                                task.get(),
                                responseHeaders,
                                HttpStatus.OK);
        }

        // delete task by id
        @DeleteMapping("{taskId}")
        @ResponseBody
        public ResponseEntity deleteTaskById(
                        @PathVariable("taskId") long id) {
                System.out.println(id);
                taskRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).build();
        }

        record UserRequest(String description, int priority, boolean completed) {
        }

        record BadRequest(int status, String message) {
        }
}

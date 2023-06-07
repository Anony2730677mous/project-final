package com.javarush.jira.bugtracking;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/api/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/{taskId}/tags", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTagsToTask(@PathVariable Long taskId, @RequestBody Set<String> tags) {
        taskService.addTagsToTask(taskId, tags);
    }


//    @PutMapping("/users/{userId}/tasks/{taskId}/status")
//    public void updateUserTaskStatus(@PathVariable Long userId, @PathVariable Long taskId, @RequestParam String statusCode) {
//        taskService.updateUserTaskStatus(userId, taskId, statusCode);
//    }



}

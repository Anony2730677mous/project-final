package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/taskuser", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskUserController {
    private final TaskUserService taskUserService;
    private final TaskService taskService;

    public TaskUserController(TaskUserService taskUserService, TaskService taskService) {
        this.taskUserService = taskUserService;
        this.taskService = taskService;
    }
    /*
    Изменение статуса задачи, выбранной пользователем при подписке, заносится в БД для дальнейшего использования
    */
    @PutMapping(value = "/{taskId}/{userId}/status", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateTaskUserStatus(@PathVariable Long taskId, @PathVariable Long userId,
                                                     @RequestBody String statusCode) {
        taskUserService.createOrUpdateTaskUserStatus(taskId, userId, statusCode);
    }
    /*
    Пользователь удаляет задачу после отслеживания её статуса(удаление из подписки)
     */
    @DeleteMapping(value = "/{taskId}/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskUser(@PathVariable Long taskId,
                               @PathVariable Long userId){
        taskUserService.deleteTaskUserByTaskId(taskId, userId);
    }

    /*
    Пользователь выбирает задачу для отслеживания её статуса(подписка на задачу)
     */
    @PostMapping(value = "/{taskId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String createTaskUser(@PathVariable Long taskId,
                                 @PathVariable Long userId){
        Task task = taskService.repository.getExisted(taskId);
        String statusCode = task.getStatusCode();
        taskUserService.createOrUpdateTaskUserStatus(taskId, userId, statusCode);
        return statusCode;
    }
}


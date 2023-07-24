package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.TaskUser;
import com.javarush.jira.bugtracking.internal.repository.TaskUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskUserService {
    private final TaskUserRepository taskUserRepository;

    public TaskUserService(TaskUserRepository taskUserRepository) {
        this.taskUserRepository = taskUserRepository;
    }

    public void createOrUpdateTaskUserStatus(Long taskId, Long userId, String statusCode) {
        Optional<TaskUser> taskUserOptional = taskUserRepository.findByTaskIdAndUserId(taskId, userId);
        if (taskUserOptional.isPresent()) {
            TaskUser taskUser = taskUserOptional.get();
            if (!statusCode.equalsIgnoreCase(taskUser.getStatusCode())){
                taskUser.setStatusCode(statusCode);
                taskUserRepository.save(taskUser);
            }
        } else {
            TaskUser taskUser = new TaskUser();
            taskUser.setStatusCode(statusCode);
            taskUser.setTaskId(taskId);
            taskUser.setUserId(userId);
            taskUserRepository.save(taskUser);
        }
    }

    public void deleteTaskUserByTaskId(Long taskId, Long userId) {
        Optional<TaskUser> taskUserOptional = taskUserRepository.findByTaskIdAndUserId(taskId, userId);
        if (taskUserOptional.isPresent()) {
            TaskUser taskUser = taskUserOptional.get();
            taskUserRepository.delete(taskUser);
        } else
            throw new EntityNotFoundException("Task User not found");
    }
}

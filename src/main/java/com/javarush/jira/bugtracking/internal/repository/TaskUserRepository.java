package com.javarush.jira.bugtracking.internal.repository;

import com.javarush.jira.bugtracking.internal.model.TaskUser;
import com.javarush.jira.common.BaseRepository;

import java.util.Optional;

public interface TaskUserRepository extends BaseRepository<TaskUser> {
    Optional<TaskUser> findByTaskIdAndUserId(Long taskId, Long userId);
}

package org.humber.project.services;

import org.humber.project.domain.Task;
import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getTasksByUserId(Long userId);
    Task changeTaskStatus(Long taskId, boolean status);
    Task changeTaskDescription(Long taskId, String description);
    void removeTask(Long taskId);
}
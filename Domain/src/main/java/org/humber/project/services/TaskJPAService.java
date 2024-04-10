package org.humber.project.services;

import org.humber.project.domain.Task;
import java.util.List;

public interface TaskJPAService {
    Task saveTask(Task task);
    List<Task> findByUserId(Long userId);
    Task updateTaskStatus(Long taskId, boolean status);
    Task updateTaskDescription(Long taskId, String description);
    void deleteTask(Long taskId);
}
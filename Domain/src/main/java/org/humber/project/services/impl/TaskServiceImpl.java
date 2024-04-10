package org.humber.project.services.impl;

import org.humber.project.domain.Task;
import org.humber.project.services.TaskJPAService;
import org.humber.project.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskJPAService taskJPAService;

    @Autowired
    public TaskServiceImpl(TaskJPAService taskJPAService) {
        this.taskJPAService = taskJPAService;
    }

    @Override
    public Task createTask(Task task) {
        return taskJPAService.saveTask(task);
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        return taskJPAService.findByUserId(userId);
    }

    @Override
    public Task changeTaskStatus(Long taskId, boolean status) {
        return taskJPAService.updateTaskStatus(taskId, status);
    }

    @Override
    public Task changeTaskDescription(Long taskId, String description) {
        return taskJPAService.updateTaskDescription(taskId, description);
    }

    @Override
    public void removeTask(Long taskId) {
        taskJPAService.deleteTask(taskId);
    }
}
package org.humber.project.services.impl;

import org.humber.project.domain.Task;
import org.humber.project.entities.TaskEntity;
import org.humber.project.repositories.TaskRepository;
import org.humber.project.services.TaskJPAService;
import org.humber.project.transformers.TaskEntityTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskJPAServiceImpl implements TaskJPAService {

    private final TaskRepository taskRepository;
    private final TaskEntityTransformer taskEntityTransformer;

    @Autowired
    public TaskJPAServiceImpl(TaskRepository taskRepository, TaskEntityTransformer taskEntityTransformer) {
        this.taskRepository = taskRepository;
        this.taskEntityTransformer = taskEntityTransformer;

    }

    @Override
    public Task saveTask(Task task) {
        TaskEntity taskEntity = taskEntityTransformer.transformToTaskEntity(task);
        TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
        return taskEntityTransformer.transformToTask(savedTaskEntity);
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        List<TaskEntity> taskEntities = taskRepository.findByUserId(userId);
        return taskEntities.stream()
                .map(taskEntityTransformer::transformToTask)
                .collect(Collectors.toList());
    }

    @Override
    public Task updateTaskStatus(Long taskId, boolean status) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskEntity.setStatus(status);
        TaskEntity updatedTaskEntity = taskRepository.save(taskEntity);
        return taskEntityTransformer.transformToTask(updatedTaskEntity);
    }

    @Override
    public Task updateTaskDescription(Long taskId, String description) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        taskEntity.setTaskDescription(description);
        TaskEntity updatedTaskEntity = taskRepository.save(taskEntity);
        return taskEntityTransformer.transformToTask(updatedTaskEntity);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
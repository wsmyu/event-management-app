package org.humber.project.transformers;

import org.humber.project.domain.Task;
import org.humber.project.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskEntityTransformer {

    public TaskEntity transformToTaskEntity(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskId(task.getTaskId());
        taskEntity.setEventId(task.getEventId());
        taskEntity.setUserId(task.getUserId());
        taskEntity.setTaskDescription(task.getTaskDescription());
        taskEntity.setStatus(task.isStatus());
        taskEntity.setEventDate(task.getEventDate());
        return taskEntity;
    }

    public Task transformToTask(TaskEntity taskEntity) {
        Task task = new Task();
        task.setTaskId(taskEntity.getTaskId());
        task.setEventId(taskEntity.getEventId());
        task.setUserId(taskEntity.getUserId());
        task.setTaskDescription(taskEntity.getTaskDescription());
        task.setStatus(taskEntity.isStatus());
        task.setEventDate(taskEntity.getEventDate());
        return task;
    }
}

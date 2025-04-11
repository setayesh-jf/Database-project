package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.entity.Task.Status;

import java.util.Date;

public class TaskService {

    public static void setAsCompleted(int taskId) throws EntityNotFoundException {
        try {
            Task task = (Task) Database.get(taskId);
            task.setStatus(Status.Completed);
            task.setLastModificationDate(new Date());
            Database.update(task);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Cannot find task with ID=" + taskId);
        }
    }

    public static int getTaskId(Task task) throws InvalidEntityException {
        if (task == null) {
            throw new InvalidEntityException("Task cannot be null.");
        }
        return task.getId();
    }

    public static Task addTask(String title, String description, Date dueDate)
            throws InvalidEntityException {

        if (title == null || title.trim().isEmpty()) {
            throw new InvalidEntityException("Task title cannot be empty.");
        }

        if (dueDate == null) {
            throw new InvalidEntityException("Due date cannot be null.");
        }


        Task task = new Task(title, description, dueDate, Status.NotStarted);
        Database.add(task);
        return task;
    }

    public static Task getTask(int taskId) throws EntityNotFoundException {
        return (Task) Database.get(taskId);
    }

    public static void deleteTask(int taskId) throws EntityNotFoundException {
        Database.delete(taskId);
    }

    public static void updateTaskTitle(int taskId, String newTitle)
            throws EntityNotFoundException, InvalidEntityException {

        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new InvalidEntityException("New title cannot be empty.");
        }

        Task task = (Task) Database.get(taskId);
        task.setTitle(newTitle);
        task.setLastModificationDate(new Date());
        Database.update(task);
    }

    public static void updateTaskStatus(int taskId, Status newStatus)
            throws EntityNotFoundException {

        Task task = (Task) Database.get(taskId);
        task.setStatus(newStatus);
        task.setLastModificationDate(new Date());
        Database.update(task);
    }

    public static void updateTaskDueDate(int taskId, Date newDueDate)
            throws EntityNotFoundException, InvalidEntityException {

        if (newDueDate == null) {
            throw new InvalidEntityException("Due date cannot be null.");
        }

        if (newDueDate.before(new Date())) {
            throw new InvalidEntityException("Due date cannot be in the past.");
        }

        Task task = (Task) Database.get(taskId);
        task.setDueDate(newDueDate);
        task.setLastModificationDate(new Date());
        Database.update(task);
    }
}
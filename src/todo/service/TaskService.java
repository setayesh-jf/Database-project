package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import todo.entity.Step;
import todo.entity.Task.Status;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


public class TaskService {

    public static List<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();
        for (Object entity : Database.getAll(100)) {
            if (entity instanceof Task) {
                tasks.add((Task) entity);
            }
        }


        tasks.sort(Comparator.comparing(Task::getDueDate));
        return tasks;
    }


    public static List<Task> getIncompleteTasks() {
        List<Task> incompleteTasks = new ArrayList<>();


        for (Object entity : Database.getAll(100)) {
            if (entity instanceof Task task) {

                if (task.getStatus() == Task.Status.NotStarted || task.getStatus() == Task.Status.InProgress) {
                    incompleteTasks.add(task);
                }
            }
        }

        return incompleteTasks;
    }

    public static List<Step> getStepsForTask(int taskId) throws EntityNotFoundException {
        return StepService.getStepsForTask(taskId);
    }


    public static Task addTask(String title, String description, Date dueDate)
            throws InvalidEntityException {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidEntityException("Task title cannot be empty.");
        }


        if (description == null || description.trim().isEmpty()) {
            throw new InvalidEntityException("Task description cannot be null or empty.");
        }


        if (dueDate == null) {
            throw new InvalidEntityException("Due date cannot be null.");
        }



        Task task = new Task(title, description, dueDate);
        Database.add(task);
        return task;
    }


    public static Task getTask(int taskId) throws EntityNotFoundException {
        return (Task) Database.get(taskId);
    }


    public static void deleteTask(int taskId) throws EntityNotFoundException {
        List<Step> steps = StepService.getStepsForTask(taskId);
        for (Step step : steps) {
            Database.delete(step.id);
        }
        Database.delete(taskId);
    }



    public static void updateTaskTitle(int taskId, String newTitle)
            throws EntityNotFoundException, InvalidEntityException {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new InvalidEntityException("New title cannot be empty.");
        }


        Task task = getTask(taskId);
        task.setTitle(newTitle);
        task.setLastModificationDate(new Date());
        Database.update(task);
    }


    public static void updateTaskStatus(int taskId, Status newStatus) throws EntityNotFoundException {
        Task task = getTask(taskId);
        task.setStatus(newStatus);
        task.setLastModificationDate(new Date());
        Database.update(task);

        if (newStatus == Status.Completed) {
            List<Step> steps = StepService.getStepsForTask(taskId);
            for (Step step : steps) {
                StepService.setStepStatus(step.id, Step.Status.Completed);
            }
        }
    }



    public static void updateTaskDueDate(int taskId, Date newDueDate)
            throws EntityNotFoundException, InvalidEntityException {

        if (newDueDate == null) {
            throw new InvalidEntityException("Due date cannot be null.");
        }

        if (newDueDate.before(new Date())) {
            throw new InvalidEntityException("Due date cannot be in the past.");
        }


        Task task = getTask(taskId);
        task.setDueDate(newDueDate);
        task.setLastModificationDate(new Date());
        Database.update(task);
    }


    public static void setAsCompleted(int taskId) throws EntityNotFoundException {
        Task task = getTask(taskId);
        task.setStatus(Status.Completed);
        task.setLastModificationDate(new Date());
        Database.update(task);



    }
}

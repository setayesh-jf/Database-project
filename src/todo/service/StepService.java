package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import db.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StepService {

    public static Step saveStep(int taskRef, String title) throws EntityNotFoundException, InvalidEntityException {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidEntityException("Step title cannot be empty.");
        }

        Task task = (Task) Database.get(taskRef);

        Step step = new Step(title, Step.Status.NotStarted, taskRef);
        step.setCreationDate(new Date());
        step.setLastModificationDate(new Date());
        Database.add(step);
        return step;
    }

    public static int getStepId(Step step) throws InvalidEntityException {
        if (step == null) {
            throw new InvalidEntityException("Step cannot be null.");
        }
        return step.id;
    }

    public static void setStepStatus(int stepId, Step.Status status) throws EntityNotFoundException {

        Step step = getStepById(stepId);
        step.setStatus(status);
        step.setLastModificationDate(new Date());
        Database.update(step);


        checkAndUpdateTaskStatus(step.getTaskRef());
    }

    public static Step getStepById(int stepId) throws EntityNotFoundException {
        return (Step) Database.get(stepId);
    }

    public static void updateStepTitle(int stepId, String newTitle) throws EntityNotFoundException, InvalidEntityException {
        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new InvalidEntityException("New title cannot be empty.");
        }

        Step step = getStepById(stepId);
        step.setTitle(newTitle);
        step.setLastModificationDate(new Date());
        Database.update(step);
    }

    public static void checkAndUpdateTaskStatus(int taskRef) throws EntityNotFoundException {

        List<Step> steps = getStepsForTask(taskRef);

        boolean allCompleted = true;
        boolean anyInProgress = false;

        for (Step step : steps) {
            if (step.getStatus() != Step.Status.Completed) {
                allCompleted = false;
            }
            if (step.getStatus() == Step.Status.Completed) {
                anyInProgress = true;
            }
        }


        Task task = TaskService.getTask(taskRef);


        if (allCompleted) {
            task.setStatus(Task.Status.Completed);
        } else if (anyInProgress) {
            task.setStatus(Task.Status.InProgress);
        }


        Database.update(task);
    }

    public static List<Step> getStepsForTask(int taskRef) throws EntityNotFoundException {
        List<Entity> entities = Database.getAll(80);
        List<Step> steps = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity instanceof Step step && step.getTaskRef() == taskRef) {
                steps.add(step);
            }
        }

        return steps;
    }


}

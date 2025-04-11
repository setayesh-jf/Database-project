package todo.service;
import db.Database;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;
import todo.entity.Task;
import java.util.Date;

public class StepService {
    public static Step saveStep(int taskRef, String title)
            throws EntityNotFoundException, InvalidEntityException {


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
        return step.getId();
    }

    public static void setStepStatus(int stepId, Step.Status status)
            throws EntityNotFoundException {

        Step step = (Step) Database.get(stepId);
        step.setStatus(status);
        step.setLastModificationDate(new Date());
        Database.update(step);
    }

    public static void deleteStep(int stepId) throws EntityNotFoundException {
        Database.delete(stepId);
    }

    public static void updateStepTitle(int stepId, String newTitle)
            throws EntityNotFoundException, InvalidEntityException {

        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new InvalidEntityException("New title cannot be empty.");
        }

        Step step = (Step) Database.get(stepId);
        step.setTitle(newTitle);
        step.setLastModificationDate(new Date());
        Database.update(step);
    }
}
package todo.validator;

import db.Entity;
import db.Validator;
import db.exception.InvalidEntityException;
import todo.entity.Task;
import java.util.Date;

public class TaskValidator implements Validator {

    @Override
    public void validate(Entity entity) throws InvalidEntityException {

        if (!(entity instanceof Task)) {
            throw new IllegalArgumentException("Entity must be of type Task");
        }

        Task task = (Task) entity;


        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new InvalidEntityException("Task title cannot be null or empty");
        }


        if (task.getDueDate() == null) {
            throw new InvalidEntityException("Due date cannot be null");
        }

    }
}
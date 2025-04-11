package todo.validator;

import db.Database;
import db.Entity;
import db.Validator;
import db.exception.EntityNotFoundException;
import db.exception.InvalidEntityException;
import todo.entity.Step;

public class StepValidator implements Validator{

    @Override
    public void validate(Entity entity) throws InvalidEntityException {
        if (!(entity instanceof Step)){
            throw new IllegalArgumentException("entity is not Class Step");
        }

        Step step = (Step) entity;


        if (step.getTitle() == null || step.getTitle().trim().isEmpty()){
            throw new InvalidEntityException("Step tittle can't null or empty");
        }

        try {
            Database.get(step.getTaskRef());
        }catch (EntityNotFoundException e){
            throw new InvalidEntityException("taskref id" + step.getTaskRef() + "does'nt exist");
        }


    }
}

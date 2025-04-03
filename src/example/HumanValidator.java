package example;
import db.Entity;
import db.exception.InvalidEntityException;
import db.Validator;

public class HumanValidator implements Validator {
    @Override
    public void validate(Entity entity)  {
        if (!(entity instanceof  Human)){
            throw new InvalidEntityException("Invalid entity type");
        }


        Human human = (Human) entity;
        if (human.name == null || human.name.trim().isEmpty()){
            throw new InvalidEntityException("Human can't name null or empty");
        }


        if (human.age < 0){
            throw new InvalidEntityException(" Age must be a positive integer");
        }
    }
}
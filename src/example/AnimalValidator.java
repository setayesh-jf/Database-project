package example;
import db.Entity;
import db.exception.InvalidEntityException;
import db.Validator;

public class AnimalValidator implements Validator {
    @Override
    public void validate(Entity entity) throws InvalidEntityException {
            if (!(entity instanceof  Animal)){
                throw new InvalidEntityException("Invalid entity type");
            }


            Animal animal = (Animal) entity;


            if (animal.name == null || animal.name.trim().isEmpty()){
                throw new InvalidEntityException("Human can't name null or empty");
            }

        }
    }


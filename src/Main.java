import db.Database;
import db.exception.InvalidEntityException;
import example.AnimalValidator;
import example.HumanValidator;
import example.Animal;
import example.Human;


public class Main {
    public static void main(String[] args) throws InvalidEntityException {
        Database.registerValidator(Human.HUMAN_ENTITY_CODE, new HumanValidator());
        Database.registerValidator(Animal.Animal_ENTITY_CODE , new AnimalValidator());

        Animal roza = new Animal("Roza");
        Human ali = new Human("Ali", -10);
        Database.add(roza);
        Database.add(ali);
    }
}
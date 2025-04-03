package example;
import db.Entity;

public class Animal extends Entity {
    public String name;
    public static final int Animal_ENTITY_CODE = 20;

    public Animal(String name){
        this.name = name;
    }

    @Override
    public Animal copy() {
        Animal copyAnimal = new Animal(name);
        copyAnimal.id = id;

        return copyAnimal;
    }

    @Override
    public int getEntityCode() {
        return  Animal_ENTITY_CODE;
    }
}

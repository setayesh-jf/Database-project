import db.Database;
import db.exception.EntityNotFoundException;
import example.Animal;
import example.Human;


public class Main {
    public static void main(String[] args) {
        Human ali = new Human("Ali");
        Animal shoka = new Animal("Shoka");
        Database.add(ali);
        Database.add(shoka);

        ali.name = "Ali Hosseini";
        shoka.name = "Shoka";

        Human aliFromTheDatabase = (Human) Database.get(ali.id);
        Animal shokaFromTheDatabase = (Animal) Database.get(shoka.id);

        System.out.println("ali's name in the database: " + aliFromTheDatabase.name);
        System.out.println("shoka's name in the database: " + shokaFromTheDatabase.name);
    }
}
import db.Database;
import db.exception.EntityNotFoundException;
import example.Animal;
import example.Human;


public class Main {
    public static void main(String[] args) {
        Human[] humans = {
                new Human("Gholi"),
                new Human("Jamshid"),
                new Human("Akbar"),
        };

        Animal[] animals = {
                new Animal("Shoka"),
                new Animal("Papet"),
                new Animal("Roza"),
        };

        System.out.println("#### Test add method ####");

        for (Human h : humans) {
            System.out.println("Adding " + h.name + " to the database.");
            Database.add(h);
        }

        for (Animal a : animals){
            System.out.println("Adding " + a.name + " to the database.");
            Database.add(a);
        }

        for (Human h : humans) {
            System.out.println("Id of \"" + h.name + "\" is " + h.id + ".");
        }


        for (Animal a : animals){
            System.out.println("Id of \"" + a.name + "\" is " + a.id + ".");
        }

        System.out.println();
        System.out.println("#### Test get method ####");

        int gholiId = humans[0].id;
        Human gholi = (Human) Database.get(gholiId);

        int shokaId = animals[0].id;
        Animal shoka = (Animal) Database.get(shokaId);

        System.out.println("successfully got " + gholi.name + " from the database.");
        System.out.println("successfully got " + shoka.name + " from the database.");

        System.out.println();
        System.out.println("#### Test update method ####");

        gholi.name = "Gholi Mohammadi";
        shoka.name = "Shoka";
        Database.update(gholi);
        Database.update(shoka);

        Human gholiAgain = (Human) Database.get(gholiId);
        Animal shokaAgain = (Animal) Database.get(shokaId);
        System.out.println("Updated name: \"" + gholiAgain.name + "\".");
        System.out.println("Updated name: \"" + shokaAgain.name + "\".");


        System.out.println();
        System.out.println("#### Test delete method ####");

        int jamshidId = humans[1].id;
        int papetId = animals[1].id;
        Database.delete(jamshidId);
        Database.delete(papetId);

        try {
            Human jamshid = (Human) Database.get(jamshidId);
            Animal papet = (Animal) Database.get(papetId);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
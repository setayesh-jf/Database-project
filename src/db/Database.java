package db;
import db.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int id = 1;
    private static final HashMap<Integer, Validator> validators = new HashMap<>();


    private Database(){}


    public static void  add(Entity e){
        if (e instanceof Trackable){
            Trackable trackable = (Trackable) e;

            Date presentTime = new Date();
            trackable.setCreationDate(presentTime);
            trackable.setLastModificationDate(presentTime);
        }

        Validator validator = validators.get(e.getEntityCode());

        if (validator != null) {
            validator.validate(e);
        }



        e.id = id++;
     Entity copy = e.copy();
    entities.add(copy);

    }


    public static Entity get(int id){
    for (Entity entity : entities){
        if (entity.id == id){
            return entity.copy();
        }
    }
    throw new EntityNotFoundException(id);
    }



    public static void delete(int id){
   boolean remove =  entities.removeIf(entity -> entity.id == id);
   if (!remove){
       throw new EntityNotFoundException(id);
   }
    }



    public static void update(Entity e){

        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            validator.validate(e);
        }



        if (e instanceof Trackable){
            Trackable trackable = (Trackable) e;

            Date presentTime = new Date();
            trackable.setLastModificationDate(presentTime);
        }

    for (int i = 0; i < entities.size(); i++){
    if (entities.get(i).id == e.id){
        entities.set(i, e.copy());
        return;
       }
    }

    throw new EntityNotFoundException(e.id);

    }

    public static void registerValidator(int entityCode, Validator validator) {
        if (validators.containsKey(entityCode)){
            throw new IllegalArgumentException("entity code" + entityCode + "is not there");
        }

        validators.put(entityCode , validator);

    }
}





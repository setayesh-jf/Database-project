package db;
import db.exception.EntityNotFoundException;
import java.util.ArrayList;

public class Database {
    private static final ArrayList<Entity> entities = new ArrayList<>();
    private static int ID = 1;

    private Database(){}

    public static void add(Entity e){
    e.id = ID++;
    entities.add(e);
    }

    public static Entity get(int id){
    for (Entity entity : entities){
        if (entity.id == id){
            return entity;
        }
    }
    throw new EntityNotFoundException (id);
    }


    public static void delete(int id){
   boolean remove =  entities.removeIf(entity -> entity.id == id);
   if (!remove){
       throw new EntityNotFoundException(id);
   }
    }


    public static void update(Entity e){
    for (int i = 0; i < entities.size(); i++){
    if (entities.get(i).id == e.id){
        entities.set(i, e);
        return;
       }
    }

    throw new EntityNotFoundException(e.id);
    }

}



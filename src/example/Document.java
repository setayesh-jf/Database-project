package example;

import db.Entity;
import db.Trackable;
import java.util.Date;

public class Document extends Entity implements Trackable{
   private Date creationDate;
   private Date lastModificationDate;

   public String content;

   public Document (String content){
       this.content = content;
   }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
    @Override
    public int getEntityCode() {
        return 100;
    }

    @Override
    public Document copy(){
        Document copyDate = new Document(content);
        copyDate.id = id;
        if (this.creationDate != null){
            copyDate.creationDate = new Date(this.creationDate.getTime());
        }

        if (this.lastModificationDate != null) {
            copyDate.lastModificationDate = new Date(this.lastModificationDate.getTime());
        }

        return copyDate;
    }
}

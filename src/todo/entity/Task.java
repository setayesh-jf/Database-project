package todo.entity;

import db.Entity;
import db.Trackable;


import java.util.Date;

public class Task extends Entity implements Trackable {
   public enum Status{
        NotStarted , InProgress , Completed;
    }
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private Status status;


    public Task(String title, String description, Date dueDate, Status status){
        this.title = title;
        this.description = description;
        this.dueDate = new Date(dueDate.getTime());
        this.status = Status.NotStarted;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Date getDueDate(){
        return new Date(dueDate.getTime());
    }

    public Status getStatus(){
        return status;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setDueDate(Date dueDate){
        this.dueDate = new Date(dueDate.getTime());
    }

    public void setStatus(Status status){
        this.status = status;
    }

    private Date creationDate;
    private Date lastModificationDate;


    @Override
    public Date getCreationDate(){
        return creationDate != null ? new Date(creationDate.getTime()) : null;
    }

    @Override
    public Date getLastModificationDate(){
        return lastModificationDate != null ? new Date(lastModificationDate.getTime()) : null;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate != null ? new Date(creationDate.getTime()) : null;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date != null ? new Date(date.getTime()) : null;
    }

    @Override
    public Task copy(){
         Task copy = new Task(title , description, new Date(this.dueDate.getTime()), this.status);

         copy.id = this.id;
         copy.status = this.status;

         if (this.creationDate != null){
             copy.creationDate = new Date(this.creationDate.getTime());
         }

         if (this.lastModificationDate != null){
             copy.lastModificationDate = new Date(this.lastModificationDate.getTime());
         }

         return copy;
    }

    public int getEntityCode(){
        return 100;
    }

}


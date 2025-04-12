package todo.entity;

import db.Entity;
import db.Trackable;


import java.util.Date;

public class Task extends Entity implements Trackable {
   public enum Status{
        NotStarted , InProgress , Completed;
    }


    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    private Date creationDate;
    private Date lastModificationDate;


    public Task(String title, String description, Date dueDate){
        this.title = title;
        this.description = description;
        this.dueDate = new Date(dueDate.getTime());
        this.status = Status.NotStarted;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
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
        setLastModificationDate(new Date());
    }

    public void setDescription(String description){
        this.description = description;
        setLastModificationDate(new Date());
    }

    public void setDueDate(Date dueDate){
        this.dueDate = new Date(dueDate.getTime());
        setLastModificationDate(new Date());
    }

    public void setStatus(Status status){
        this.status = status;
        setLastModificationDate(new Date());
    }


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
         Task copy = new Task(title , description, new Date(this.dueDate.getTime()));
         copy.id = this.id;
         copy.status = this.status;
         copy.setCreationDate(this.creationDate) ;
         copy.setLastModificationDate(this.lastModificationDate);
         return copy;
    }

    public int getEntityCode(){
        return 100;
    }

}


package todo.entity;
import java.util.Date;

import db.Entity;

public class Step extends Entity{
   public enum Status{
        NotStarted, Completed;
    }
    private int id;
    private String title;
    private Status status;
    private int taskRef;
    private Date creationDate;
    private Date lastModificationDate;

    public Step(String title, Status status, int taskRef){
        this.title = title;
        this.status = status != null ? status : Status.NotStarted ;
        this.taskRef = taskRef;
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

    public Status getStatus(){
        return status;
    }

    public int getTaskRef(){
        return taskRef;
    }

    public Date getCreationDate() {
        return creationDate != null ? new Date(creationDate.getTime()) : null;
    }

    public Date getLastModificationDate() {
        return lastModificationDate != null ? new Date(lastModificationDate.getTime()) : null;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTaskRef(int taskRef) {
        this.taskRef = taskRef;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date != null ? new Date(date.getTime()) : null;
    }

    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date != null ? new Date(date.getTime()) : null;
    }

    @Override
    public Step copy(){
        Step copy = new Step(this.title, this.status, this.taskRef);
        copy.id = this.id;
        copy.status = this.status;
        return copy;
    }

    @Override
    public int getEntityCode() {
        return 80;
    }
}

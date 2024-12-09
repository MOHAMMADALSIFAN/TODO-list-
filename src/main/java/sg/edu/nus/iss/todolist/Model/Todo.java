package sg.edu.nus.iss.todolist.Model;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Todo {
  
  @NotBlank
  @Size(max=50)
  private String id;
  @NotBlank
  @Size(min=10,max=50,message="Min length:10 & Max length:50")
  private String name;
  @Size(max=255, message="Max length:255 characters")
  private String description;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @FutureOrPresent
  private Date dueDate;
  private String priority;
  private String status;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdAt;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updatedAt;

  public Todo() {
    this.id = generateUniqueId();
  }

  public Todo(String name, String description, Date dueDate, String priority, String status, Date createdAt,
      Date updatedAt) {
    this.id = generateUniqueId();
    this.name = name;
    this.description = description;
    this.dueDate = dueDate;
    this.priority = priority;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Todo(String id, String name, String description, Date dueDate, String priority, String status, Date createdAt,
      Date updatedAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.dueDate = dueDate;
    this.priority = priority;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  private String generateUniqueId() {
    return UUID.randomUUID().toString().toUpperCase().substring(0, 36);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public String getPriority() {
    return priority;
  }

  public void setPriority(String priority) {
    this.priority = priority;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  

}

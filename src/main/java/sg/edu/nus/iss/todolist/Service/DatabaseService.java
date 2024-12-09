package sg.edu.nus.iss.todolist.Service;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.todolist.Constant.Util;
import sg.edu.nus.iss.todolist.Model.Todo;
import sg.edu.nus.iss.todolist.Repo.Hashrepo;

@Service
public class DatabaseService {

  @Autowired
  Hashrepo hashrepo;

  public void save(Todo todo) {
    JsonObject jsontodoobject = Json.createObjectBuilder()
        .add("id", todo.getId())
        .add("name", todo.getName())
        .add("desc", todo.getDescription())
        .add("dueDate", todo.getDueDate().getTime()) // Convert Date to long
        .add("priority", todo.getPriority())
        .add("status", todo.getStatus())
        .add("created", todo.getCreatedAt().getTime()) // Convert Date to long
        .add("updated", todo.getUpdatedAt().getTime()) // Convert Date to long
        .build();

    String jsontodoobjectstring = jsontodoobject.toString();
    hashrepo.create(Util.folder, String.valueOf(todo.getId()), jsontodoobjectstring);
  }

  public List<Todo> getTodofromredis(){
    List<Todo> RetrievedTodorawData = new ArrayList<>();
    Map<Object,Object> retrieveData = hashrepo.getEntries(Util.folder);
    for (Entry<Object,Object> mapEntry : retrieveData.entrySet()){
      JsonReader jsonReader = Json.createReader(new StringReader(mapEntry.getValue().toString()));
      JsonObject record = jsonReader.readObject();
      Todo todos = new Todo();
      todos.setId(record.getString("id"));
      todos.setName(record.getString("name"));
      todos.setDescription(record.getString("desc"));

      Date duedate = new Date(record.getJsonNumber("dueDate").longValue());
      todos.setDueDate(duedate);
      
      todos.setPriority(record.getString("priority"));
      todos.setStatus(record.getString("status"));

      Date createdDate = new Date(record.getJsonNumber("created").longValue());
      todos.setCreatedAt(createdDate);

      Date updateDate = new Date(record.getJsonNumber("updated").longValue());
      todos.setUpdatedAt(updateDate);
      RetrievedTodorawData.add(todos);
    }
    return RetrievedTodorawData;
  }

  public Boolean delete(String id){
    Long deletedCount = hashrepo.delete(Util.folder, id);
    return deletedCount > 0;
  }
  public void savenew(Todo todo){
    JsonObject jsontodoobject = Json.createObjectBuilder()
    .add("id", todo.getId())
    .add("name", todo.getName())
    .add("desc", todo.getDescription())
    .add("dueDate", todo.getDueDate().getTime()) // Convert Date to long
    .add("priority", todo.getPriority())
    .add("status", todo.getStatus())
    .add("created", todo.getCreatedAt().getTime()) // Convert Date to long
    .add("updated", todo.getUpdatedAt().getTime()) // Convert Date to long
    .build();
    String jsontodoobjectstring = jsontodoobject.toString();
    hashrepo.create(Util.folder, String.valueOf(todo.getId()), jsontodoobjectstring);

  }

  public Todo getTodobyId (String id){
    String jsonString = hashrepo.getValueFromMap(Util.folder, id);
    JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
    JsonObject record = jsonReader.readObject();
      Todo todos = new Todo();
      todos.setId(record.getString("id"));
      todos.setName(record.getString("name"));
      todos.setDescription(record.getString("desc"));

      Date duedate = new Date(record.getJsonNumber("dueDate").longValue());
      todos.setDueDate(duedate);
      
      todos.setPriority(record.getString("priority"));
      todos.setStatus(record.getString("status"));

      Date createdDate = new Date(record.getJsonNumber("created").longValue());
      todos.setCreatedAt(createdDate);

      Date updateDate = new Date(record.getJsonNumber("updated").longValue());
      todos.setUpdatedAt(updateDate);
      return todos;
  }

  public void update (Todo todo){
    if(hashrepo.keyExists(Util.folder, todo.getId())){
      JsonObject jsontodoobj = Json.createObjectBuilder()
    .add("id", todo.getId())
    .add("name", todo.getName())
    .add("desc", todo.getDescription())
    .add("dueDate", todo.getDueDate().getTime()) // Convert Date to long
    .add("priority", todo.getPriority())
    .add("status", todo.getStatus())
    .add("created", todo.getCreatedAt().getTime()) // Convert Date to long
    .add("updated", todo.getUpdatedAt().getTime()) // Convert Date to long
    .build();
    String jsontodoobjectstring = jsontodoobj.toString();
    hashrepo.create(Util.folder, String.valueOf(todo.getId()),jsontodoobjectstring);
    }
  }

  }


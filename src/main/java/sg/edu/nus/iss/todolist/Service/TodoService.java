package sg.edu.nus.iss.todolist.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.todolist.Model.Todo;

@Service
public class TodoService {

  public List<Todo> readfile(String filename) throws FileNotFoundException, ParseException {
    InputStream is = new FileInputStream(filename);
    JsonReader jReader = Json.createReader(is);
    JsonArray todo = jReader.readArray();
    List<Todo> todolist = new ArrayList<>();
    for (JsonValue todoValue : todo) {
      JsonObject jTodo = todoValue.asJsonObject();
      Todo todos = new Todo();
      todos.setId(jTodo.getString("id"));
      todos.setName(jTodo.getString("name"));
      todos.setDescription(jTodo.getString("description"));

      // Json String date to object Date Format
      // When extracting a JSON string date to store in a Date object in Java,
      // you must match the format of the date string in the JSON exactly when parsing
      // it with SimpleDateFormat.
      // Java's SimpleDateFormat requires the input format to match the provided
      // pattern for it to
      // parse the string into a Date object successfully.

      SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MM/dd/yyyy");
      Date date1 = dateFormat.parse(jTodo.getString("due_date"));
      todos.setDueDate(date1);

      todos.setPriority(jTodo.getString("priority_level"));
      todos.setStatus(jTodo.getString("status"));

      Date createdate = dateFormat.parse(jTodo.getString("created_at"));
      todos.setCreatedAt(createdate);

      Date updatedate = dateFormat.parse(jTodo.getString("updated_at"));
      todos.setUpdatedAt(updatedate);

      todolist.add(todos);
    }
    return todolist;

  }
}

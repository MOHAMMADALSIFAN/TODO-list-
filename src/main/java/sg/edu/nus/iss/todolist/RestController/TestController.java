package sg.edu.nus.iss.todolist.RestController;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.todolist.Model.Todo;
import sg.edu.nus.iss.todolist.Service.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api")
public class TestController {
  
  @Autowired
  TodoService todoservice;

  @GetMapping("")
  ResponseEntity<List<Todo>> gettodos() throws FileNotFoundException, ParseException {
        List<Todo> todo = new ArrayList<>();

        todo = todoservice.readfile("C:\\Users\\alsif\\Desktop\\VISA\\02-ssf\\practice\\todolist\\todolist\\todos.json");

        return ResponseEntity.ok().body(todo);
    }
  
  

}

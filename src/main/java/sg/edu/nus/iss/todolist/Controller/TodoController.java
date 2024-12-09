package sg.edu.nus.iss.todolist.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import sg.edu.nus.iss.todolist.Service.DatabaseService;
import sg.edu.nus.iss.todolist.Service.TodoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.todolist.Model.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import sg.edu.nus.iss.todolist.Model.Todo;

@Controller
@RequestMapping("/todo")
public class TodoController {
  @Autowired
  TodoService todoService;

  @Autowired
  DatabaseService dbs;

  @GetMapping("")
  public String showlogin(Model model) {
    User user = new User();
    model.addAttribute("user", user);
    return "login";
  }

  @PostMapping("/login")
  public String authlogin(@ModelAttribute("user") User us, HttpSession session, Model model) {
    int age = Integer.parseInt(us.getAge());
    if (age < 10) {
      return "redirect:/todo/underage"; // Redirect to underage page if age is less than 10
    }

    session.setAttribute("user", us);
    return "redirect:/todo/listing";
  }

  @GetMapping("/refused")
  public String refusedPage() {
    return "refused"; // Return the refused HTML page
  }

  @GetMapping("/underage")
  public String underagePage() {
    return "underage"; // Return the underage HTML page
  }

  @GetMapping("/listing")
  public String showtable(@RequestParam(name = "status", defaultValue = "all") String status,
      HttpSession session, Model model) {
    User userdetails = (User) session.getAttribute("user");
    if (userdetails == null) {
      return "login";
    } else {
      model.addAttribute("user", userdetails);

      List<Todo> filteredTodos;
      List<Todo> getalltodo = dbs.getTodofromredis();

      if ("pending".equals(status)) {
        filteredTodos = getalltodo.stream()
            .filter(todo -> "pending".equals(todo.getStatus()))
            .collect(Collectors.toList());
      } else if ("in_progress".equals(status)) {
        filteredTodos = getalltodo.stream()
            .filter(todo -> "in_progress".equals(todo.getStatus()))
            .collect(Collectors.toList());
      } else if ("completed".equals(status)) {
        filteredTodos = getalltodo.stream()
            .filter(todo -> "completed".equals(todo.getStatus()))
            .collect(Collectors.toList());
      } else {
        filteredTodos = getalltodo; // "all" selected, no filtering
      }

      model.addAttribute("todo", filteredTodos);
      return "listing";
    }
  }
  @GetMapping("/add")
  public String shownewtask(Model model) {
      Todo todoform = new Todo();
      model.addAttribute("todoform", todoform);

      List<String> listStatus = Arrays.asList("pending", "in_progress", "completed");
      model.addAttribute("listStatus", listStatus); 
      return "form";
  }

  @PostMapping("/save")
  public String saveNew(@Valid @ModelAttribute("todoform") Todo todo, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "form";
    } else {
      dbs.savenew(todo);
      return "redirect:/todo/listing";
    }
  }
  @GetMapping("/delete/{id}")
  public String deletePerson(@PathVariable String id) {
    dbs.delete(id);
    return "redirect:/todo/listing"; // Redirects back to persons list after deletion
  }

  @GetMapping("/update/{id}")
  public String updatePerson(@PathVariable String id, Model model) {
   Todo todo  = dbs.getTodobyId(id);
    if (todo == null) {
      return "redirect:/todo/listing";
    }
    model.addAttribute("todo",todo);
    return "update-form";
  }

  @PostMapping("/update")
  public String updatePerson(@Valid @ModelAttribute("todo") Todo todo, BindingResult result) {
    if (result.hasErrors()) {
      return "update-form";
    } 
    else {
      dbs.update(todo);
      return "redirect:/todo/listing";
    }
  }


}

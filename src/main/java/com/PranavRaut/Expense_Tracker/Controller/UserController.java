package com.PranavRaut.Expense_Tracker.Controller;

import com.PranavRaut.Expense_Tracker.entity.Expense;
import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.service.ExpenseService;
import com.PranavRaut.Expense_Tracker.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers (){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<?> addUser (@RequestBody User user){
        try {
            userService.addUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getByUsername (@PathVariable("username") String username){
        User byusername = userService.getByusername(username);
        if (byusername == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getByusername(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser (@PathVariable("id") ObjectId id , @RequestBody User user){
        try {
            User newUser = userService.editUser(id,user);
            return ResponseEntity.ok(newUser);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{username")
    public ResponseEntity<String> deleteUser (@PathVariable("username") String username){
        boolean b = userService.deleteUser(username);
        if(b){
            return ResponseEntity.ok("User Deleted");
        }
        return  ResponseEntity.notFound().build();
    }

    // endpoints connected to Expenses

    @GetMapping("/{username}")
    public ResponseEntity<List<Expense>>  userExpenses (@PathVariable("username") String username) {
        return new ResponseEntity<>(userService.userExpenses(username), HttpStatus.OK);
    }

    @PostMapping("/{username}/expenses")
    public ResponseEntity<?> addUserExpense (@RequestBody Expense expense , @PathVariable("username") String username){
        return new ResponseEntity<>(userService.addUserExpense(username,expense) , HttpStatus.CREATED);
    }



}

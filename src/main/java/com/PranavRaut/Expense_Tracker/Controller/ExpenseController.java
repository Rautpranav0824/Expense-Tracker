package com.PranavRaut.Expense_Tracker.Controller;

import com.PranavRaut.Expense_Tracker.entity.Expense;
import com.PranavRaut.Expense_Tracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getallexpenses(){
        return expenseService.getallexpense();
    }

    @PostMapping
    public void createExpense (@RequestBody Expense expense){
        expenseService.saveExpense(expense);
    }

    @GetMapping("id/{myId}")
    public Optional<Expense> getById (@PathVariable("myId") String id){
        return expenseService.findById(id);
    }

    


}

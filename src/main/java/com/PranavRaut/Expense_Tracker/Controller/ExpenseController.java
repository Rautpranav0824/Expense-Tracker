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

    @PutMapping("/id/{myId}")
    public void editexpense (@RequestBody Expense newexpense , @PathVariable("myId") String id){
        Expense oldexpense = expenseService.findById(id).orElse(null);
        if(oldexpense != null){
            oldexpense.setTitle(newexpense.getTitle() != null && !newexpense.getTitle().equals("") ? newexpense.getTitle() : oldexpense.getTitle());
            oldexpense.setCategory(newexpense.getCategory() != null && !newexpense.getCategory().equals("") ? newexpense.getCategory() : oldexpense.getCategory());
            oldexpense.setAmount( newexpense.getAmount() >= 0 ? newexpense.getAmount() : oldexpense.getAmount() );
            oldexpense.setDescription(newexpense.getDescription() != null && !newexpense.getDescription().equals("") ? newexpense.getDescription() : oldexpense.getDescription());

            expenseService.saveExpense(oldexpense);
        }
        else {
            System.out.println("NOT FOUND");
        }
    }

    @DeleteMapping("/id/{myId}")
    public void deleteById (@PathVariable("myId") String id){
        Optional<Expense> byId = expenseService.findById(id);
        if(byId.isPresent()){
            expenseService.deleteByID(id);
        }
        else{
            System.out.println("NOT FOUND");
        }
    }


}

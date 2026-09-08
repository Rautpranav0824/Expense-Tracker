package com.PranavRaut.Expense_Tracker.Controller;

import com.PranavRaut.Expense_Tracker.entity.Expense;
import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.service.ExpenseService;
import com.PranavRaut.Expense_Tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;


    // ---------------------------------------------------------
    // GET ALL EXPENSES OF LOGGED-IN USER
    // ---------------------------------------------------------

    @GetMapping
    public ResponseEntity<?> getAllExpenses() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.findByUserName(username);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<Expense> expenses = user.getExpenses();

        if (expenses == null || expenses.isEmpty()) {
            return new ResponseEntity<>("No expenses found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    // ---------------------------------------------------------
    // CREATE EXPENSE
    // ---------------------------------------------------------

    @PostMapping
    public ResponseEntity<?> createExpense(@RequestBody Expense myEntry) {

        try {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            expenseService.saveExpense(myEntry, username);

            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseEntity<>(
                    "Unable to create expense",
                    HttpStatus.BAD_REQUEST
            );
        }
    }


    // ---------------------------------------------------------
    // GET EXPENSE BY ID
    // ---------------------------------------------------------

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getById(
            @PathVariable("myId") String id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.findByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }

        Optional<Expense> expense = expenseService.findById(id);

        if (expense.isEmpty()) {
            return new ResponseEntity<>(
                    "Expense not found",
                    HttpStatus.NOT_FOUND
            );
        }

        // Make sure the expense actually belongs to this user
        boolean belongsToUser = user.getExpenses()
                .stream()
                .anyMatch(e -> e.getId().equals(expense.get().getId()));

        if (!belongsToUser) {
            return new ResponseEntity<>(
                    "You are not authorized to access this expense",
                    HttpStatus.FORBIDDEN
            );
        }

        return new ResponseEntity<>(
                expense.get(),
                HttpStatus.OK
        );
    }


    // ---------------------------------------------------------
    // UPDATE EXPENSE
    // ---------------------------------------------------------

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> editExpense(
            @RequestBody Expense newExpense,
            @PathVariable("myId") String id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.findByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }

        Optional<Expense> optionalExpense =
                expenseService.findById(id);

        if (optionalExpense.isEmpty()) {
            return new ResponseEntity<>(
                    "Expense not found",
                    HttpStatus.NOT_FOUND
            );
        }

        Expense oldExpense = optionalExpense.get();

        // Check ownership
        boolean belongsToUser = user.getExpenses()
                .stream()
                .anyMatch(e -> e.getId().equals(oldExpense.getId()));

        if (!belongsToUser) {
            return new ResponseEntity<>(
                    "You are not authorized to edit this expense",
                    HttpStatus.FORBIDDEN
            );
        }

        // Update only provided fields

        if (newExpense.getTitle() != null &&
                !newExpense.getTitle().isEmpty()) {

            oldExpense.setTitle(newExpense.getTitle());
        }

        if (newExpense.getCategory() != null &&
                !newExpense.getCategory().isEmpty()) {

            oldExpense.setCategory(newExpense.getCategory());
        }

        if (newExpense.getAmount() >= 0) {

            oldExpense.setAmount(newExpense.getAmount());
        }

        if (newExpense.getDescription() != null &&
                !newExpense.getDescription().isEmpty()) {

            oldExpense.setDescription(newExpense.getDescription());
        }

        if (newExpense.getDate() != null) {

            oldExpense.setDate(newExpense.getDate());
        }

        expenseService.saveExpense(oldExpense);

        return new ResponseEntity<>(
                oldExpense,
                HttpStatus.OK
        );
    }


    // ---------------------------------------------------------
    // DELETE EXPENSE
    // ---------------------------------------------------------

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(
            @PathVariable("myId") String id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userService.findByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }

        Optional<Expense> expense =
                expenseService.findById(id);

        if (expense.isEmpty()) {
            return new ResponseEntity<>(
                    "Expense not found",
                    HttpStatus.NOT_FOUND
            );
        }

        // Check ownership
        boolean belongsToUser = user.getExpenses()
                .stream()
                .anyMatch(e -> e.getId().equals(expense.get().getId()));

        if (!belongsToUser) {
            return new ResponseEntity<>(
                    "You are not authorized to delete this expense",
                    HttpStatus.FORBIDDEN
            );
        }

        expenseService.deleteByID(id);

        // Remove reference from User
        user.getExpenses().removeIf(
                e -> e.getId().equals(expense.get().getId())
        );

        userService.saveUser(user);

        return new ResponseEntity<>(
                "Expense deleted successfully",
                HttpStatus.OK
        );
    }


    // ---------------------------------------------------------
    // GET EXPENSES BY CATEGORY
    // ---------------------------------------------------------

    @GetMapping("/category/{category}")
    public List<Expense> getByCategory(
            @PathVariable("category") String category) {

        return expenseService.getByCategory(category);
    }


    // ---------------------------------------------------------
    // EXPENSES GREATER THAN AMOUNT
    // ---------------------------------------------------------

    @GetMapping("/greater/{amt}")
    public List<Expense> greaterThan(
            @PathVariable("amt") Double amt) {

        return expenseService.greaterThan(amt);
    }


    // ---------------------------------------------------------
    // EXPENSES LESS THAN AMOUNT
    // ---------------------------------------------------------

    @GetMapping("/less/{amt}")
    public List<Expense> lessThan(
            @PathVariable("amt") Double amt) {

        return expenseService.lessThan(amt);
    }


    // ---------------------------------------------------------
    // SEARCH EXPENSE BY TITLE
    // ---------------------------------------------------------

    @GetMapping("/title/{word}")
    public List<Expense> findByTitleContaining(
            @PathVariable("word") String word) {

        return expenseService.findByTitleContaining(word);
    }


    // ---------------------------------------------------------
    // TOTAL EXPENSE
    // ---------------------------------------------------------

    @GetMapping("/total")
    public int totalExpense() {

        return expenseService.totalExpense();
    }


    // ---------------------------------------------------------
    // HIGHEST EXPENSE
    // ---------------------------------------------------------

    @GetMapping("/highest")
    public Expense highestExpense() {

        return expenseService.highestExpense();
    }


    // ---------------------------------------------------------
    // MONTHLY EXPENSE LIST
    // ---------------------------------------------------------

    @GetMapping("/{year}/{month}")
    public List<Expense> monthlyList(
            @PathVariable("month") int month,
            @PathVariable("year") int year) {

        return expenseService.monthly(month, year);
    }


    // ---------------------------------------------------------
    // MONTHLY EXPENSE TOTAL
    // ---------------------------------------------------------

    @GetMapping("/total/{year}/{month}")
    public Double monthlyExpense(
            @PathVariable("month") int month,
            @PathVariable("year") int year) {

        return expenseService.monthlyExpense(month, year);
    }
}

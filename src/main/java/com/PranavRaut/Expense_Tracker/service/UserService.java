package com.PranavRaut.Expense_Tracker.service;

import com.PranavRaut.Expense_Tracker.entity.Expense;
import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.repository.ExpenseRepository;
import com.PranavRaut.Expense_Tracker.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User getByusername(String username){
        return userRepository.findByUserName(username);
    }

    public User editUser(ObjectId id , User Newuser){
        User OldUser = userRepository.findById(id).orElse(null);
        if (OldUser != null){
            OldUser.setUsername(Newuser.getUsername() != null && !Newuser.getUsername().isBlank() ? Newuser.getUsername() : OldUser.getUsername());
            OldUser.setPassword(Newuser.getPassword() != null && !Newuser.getPassword().isBlank() ? Newuser.getPassword() : OldUser.getPassword());
            OldUser.setEmail(Newuser.getEmail() != null && !Newuser.getEmail().isBlank() ? Newuser.getEmail() : OldUser.getEmail());

            userRepository.save(OldUser);
            return OldUser;
        }

        return null;
    }

    public boolean deleteUser (String username){
        User byUserName = userRepository.findByUserName(username);
        if(byUserName != null){
            userRepository.delete(byUserName);
            return true;
        }
        else {
            return false;
        }
    }

    //get all expenses of a user
    public List<Expense> userExpenses (String username){
        User user = userRepository.findByUserName(username);
        if ( user == null){
            throw new RuntimeException("User not Found");
        }

        return user.getExpenses();
    }

    //add expense to a user
    public Expense addUserExpense (String username , Expense expense){
        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new RuntimeException("User not Found");
        }
        Expense saved = expenseRepository.save(expense);
        user.getExpenses().add(saved);
        userRepository.save(user);

        return saved;

    }

    //get one expense of user
    public Expense getExpense (String username , ObjectId id){
        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new RuntimeException("User Not found");
        }

        for (Expense expense : user.getExpenses()){
            if(expense.getId().equals(id)){
                return expense;
            }
        }
        throw new RuntimeException("Expense not found");
    }

    //update expense
    public Expense updateExpense (String username , ObjectId id , Expense newExpense){
        User user = userRepository.findByUserName(username);
        if(user == null){
            throw new RuntimeException("User Not found");
        }
        for (Expense Oldexpense : user.getExpenses()){
            if(Oldexpense.getId().equals(id)){
                Oldexpense.setTitle(newExpense.getTitle() != null && !newExpense.getTitle().isBlank() ? newExpense.getTitle() : Oldexpense.getTitle());
                Oldexpense.setAmount(newExpense.getAmount() >=0 ? newExpense.getAmount() : Oldexpense.getAmount());
                Oldexpense.setCategory(newExpense.getCategory() != null && !newExpense.getCategory().isBlank() ? newExpense.getCategory() : Oldexpense.getCategory());
                Oldexpense.setDescription(newExpense.getDescription() != null && !newExpense.getDescription().isBlank() ? newExpense.getDescription() : Oldexpense.getDescription());

                expenseRepository.save(Oldexpense);
                return  Oldexpense;
            }
        }
        throw new RuntimeException("Expense not found");
    }

}

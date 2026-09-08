package com.PranavRaut.Expense_Tracker.service;

import com.PranavRaut.Expense_Tracker.entity.Expense;
import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.repository.ExpenseRepository;
import com.PranavRaut.Expense_Tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Expense> getallexpense (){
        return expenseRepository.findAll();
    }

    @Transactional
    public void saveExpense(Expense expense, String username) {

        User user = userRepository.findByUsername(username);

        expenseRepository.save(expense);

        user.getExpenses().add(expense);

        userRepository.save(user);
    }

    public void saveExpense (Expense expense){
        expenseRepository.save(expense);
    }

    public Optional<Expense> findById (String id){
        return expenseRepository.findById(id);
    }

    public void deleteByID (String id){
        expenseRepository.deleteById(id);
    }

    public List<Expense> getByCategory (String Category){
        return expenseRepository.findByCategory(Category);
    }

    public List<Expense> greaterThan (Double Amount){
        return expenseRepository.findByAmountGreaterThan(Amount);
    }

    public List<Expense> lessThan (Double Amount){
        return expenseRepository.findByAmountLessThan(Amount);
    }

    public List<Expense> findByTitleContaining (String word){
        return expenseRepository.findByTitleContaining(word);
    }

    public int totalExpense (){
        List<Expense> numbers = expenseRepository.findAll();
        int sum = 0;

        for (Expense num : numbers ) {
            sum += num.getAmount();
        }

        return sum;
    }

    public Expense highestExpense (){
        List<Expense> numbers = expenseRepository.findAll();
        if(!numbers.isEmpty()) {

            Expense highestExpense = numbers.getFirst();
            for (Expense num : numbers) {
                if (num.getAmount() > highestExpense.getAmount()) {
                    highestExpense = num;
                }
            }
            return highestExpense;
        }
        return null;
     }

     public List<Expense> monthly (int month , int year ){
        List<Expense> expenses = expenseRepository.findAll();
        List<Expense> monthly = new ArrayList<>();
        if(!expenses.isEmpty()){
            for ( Expense num : expenses){
                if(num.getDate().getMonthValue() == month && num.getDate().getYear() == year){
                    monthly.add(num);
                }
            }
            return monthly;
        }
        return List.of();
     }

     public Double monthlyExpense (int month , int year){
        List<Expense> expenses = expenseRepository.findAll();
        if(!expenses.isEmpty()){
            double sum = 0;

            for (Expense num : expenses){
                if(num.getDate().getMonthValue() == month && num.getDate().getYear() == year){
                    sum += num.getAmount();
                }
            }
            return sum;
        }
        return 0.0;
     }

}

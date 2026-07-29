package com.PranavRaut.Expense_Tracker.repository;

import com.PranavRaut.Expense_Tracker.entity.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense,String> {
    Expense findByCategory (String category);

    List<Expense> findByAmountGreaterThan(Double amount);

    List<Expense> findByAmountLessThan(Double amount);

    List<Expense> findByTitleContaining(String word);
}

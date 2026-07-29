package com.PranavRaut.Expense_Tracker.repository;

import com.PranavRaut.Expense_Tracker.entity.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<Expense,String> {
    Expense findByCategory (String category);
}

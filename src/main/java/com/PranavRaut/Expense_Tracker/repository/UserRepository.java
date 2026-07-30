package com.PranavRaut.Expense_Tracker.repository;

import com.PranavRaut.Expense_Tracker.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

}

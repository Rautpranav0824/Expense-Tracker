package com.PranavRaut.Expense_Tracker;

import com.PranavRaut.Expense_Tracker.entity.User;
import com.PranavRaut.Expense_Tracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}


	@Bean
	public PlatformTransactionManager database (MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}

}

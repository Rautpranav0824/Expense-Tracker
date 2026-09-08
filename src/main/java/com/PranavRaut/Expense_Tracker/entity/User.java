package com.PranavRaut.Expense_Tracker.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
@Data

public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String username ;
    private String password ;


    @DBRef
    private List<Expense> expenses = new ArrayList<>();

    private List<String> roles = new ArrayList<>();

}

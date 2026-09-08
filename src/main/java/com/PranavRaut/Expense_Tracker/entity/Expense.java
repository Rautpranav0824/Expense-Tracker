package com.PranavRaut.Expense_Tracker.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "expenses")
@Data
@JsonPropertyOrder({"id", "title","date","category", "amount","description",})
public class Expense {
    @Id
    private ObjectId id;
    private String title;
    @NonNull
    private double amount;
    private LocalDate date;
    private String description;
    private String category;
}

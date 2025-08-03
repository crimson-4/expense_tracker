package com.expensetracker.expense.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(precision = 10, scale = 2) // optional but good for DB control
    private BigDecimal amount;


    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String userId;

    @Column(length = 1000)
    private String notes;
}

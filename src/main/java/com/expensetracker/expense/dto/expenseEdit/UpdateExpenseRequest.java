package com.expensetracker.expense.dto.expenseEdit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateExpenseRequest {
    private String title;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String notes;
}

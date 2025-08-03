package com.expensetracker.expense.dto.events;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpensePayload {
    private Long id;
    private String userId;
    private String title;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    private String notes;
}

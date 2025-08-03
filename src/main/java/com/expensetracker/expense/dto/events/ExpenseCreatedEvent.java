package com.expensetracker.expense.dto.events;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCreatedEvent {
    private String eventType;
    private ExpensePayload payload;
}

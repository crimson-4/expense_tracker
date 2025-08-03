package com.expensetracker.analytics.dto;

import com.expensetracker.expense.dto.ExpenseResponse;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsResponse {
    private BigDecimal totalSpent;
    private List<CategorySpending> categoryWiseSpending;
    private List<ExpenseResponse> top5Expenses;
}

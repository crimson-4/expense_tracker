package com.expensetracker.analytics.dto;


import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategorySpending {
    private String category;
    private BigDecimal totalSpent;
}

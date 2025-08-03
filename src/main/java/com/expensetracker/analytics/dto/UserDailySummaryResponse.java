package com.expensetracker.analytics.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDailySummaryResponse {
    private String userId;
    private LocalDate date;
    private BigDecimal totalAmount;
}


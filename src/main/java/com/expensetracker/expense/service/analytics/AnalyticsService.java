package com.expensetracker.expense.service.analytics;


import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.analytics.dto.AnalyticsResponse;
import com.expensetracker.analytics.dto.CategorySpending;
import com.expensetracker.analytics.dto.TimePeriod;
import com.expensetracker.expense.model.Expense;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.expense.service.expense.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseService expenseService;

    public AnalyticsResponse getAnalytics(TimePeriod period,String userId) {
        LocalDate fromDate = getStartDate(period);
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(
                userId,
                getStartDate(period),
                LocalDate.now()
        );
        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, BigDecimal> categoryMap = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense::getAmount, BigDecimal::add)
                ));

        List<CategorySpending> categoryWise = categoryMap.entrySet().stream()
                .map(e -> new CategorySpending(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        List<ExpenseResponse> top5 = expenses.stream()
                .sorted(Comparator.comparing(Expense::getAmount).reversed())
                .limit(5)
                .map(expenseService::mapToResponse)
                .collect(Collectors.toList());

        return AnalyticsResponse.builder()
                .totalSpent(total)
                .categoryWiseSpending(categoryWise)
                .top5Expenses(top5)
                .build();
    }

    private LocalDate getStartDate(TimePeriod period) {
        LocalDate today = LocalDate.now();
        return switch (period) {
            case WEEKLY -> today.minusWeeks(1);
            case MONTHLY -> today.minusMonths(1);
            case YEARLY -> today.minusYears(1);
        };
    }
}

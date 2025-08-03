package com.expensetracker.analytics.service;
import com.expensetracker.analytics.model.UserDailySummary;
import com.expensetracker.analytics.repository.UserDailySummaryRepository;
import com.expensetracker.expense.dto.events.ExpensePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsAggregationService {

    private final UserDailySummaryRepository summaryRepository;

    public void process(ExpensePayload payload) {
        String userId = payload.getUserId();
        var date = payload.getDate();

        summaryRepository.findByUserIdAndDate(userId, date).ifPresentOrElse(summary -> {
            // Update existing summary
            summary.setTotalAmount(summary.getTotalAmount().add(payload.getAmount()));
            summaryRepository.save(summary);
            log.info("Updated summary for user {} on {} with amount {}", userId, date, payload.getAmount());

        }, () -> {
            // Create new summary
            UserDailySummary newSummary = UserDailySummary.builder()
                    .userId(userId)
                    .date(date)
                    .totalAmount(payload.getAmount())
                    .build();
            summaryRepository.save(newSummary);
            log.info("Created new summary for user {} on {} with amount {}", userId, date, payload.getAmount());
        });
    }
}

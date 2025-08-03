package com.expensetracker.analytics.service;

import com.expensetracker.analytics.dto.UserDailySummaryResponse;
import com.expensetracker.analytics.model.UserDailySummary;
import com.expensetracker.analytics.repository.UserDailySummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalyticsSummaryQueryService {

    private final UserDailySummaryRepository repository;

    public Optional<UserDailySummaryResponse> getSummary(String userId, LocalDate date) {
        Optional<UserDailySummary> summary = repository.findByUserIdAndDate(userId, date);
        return summary.map(s -> UserDailySummaryResponse.builder()
                .userId(s.getUserId())
                .date(s.getDate())
                .totalAmount(s.getTotalAmount())
                .build()
        );
    }
}

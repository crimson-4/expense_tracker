package com.expensetracker.analytics.controller;

import com.expensetracker.analytics.dto.UserDailySummaryResponse;
import com.expensetracker.analytics.service.AnalyticsSummaryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsSummaryController {

    private final AnalyticsSummaryQueryService queryService;

    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<?> getUserSummary(
            @PathVariable String userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return queryService.getSummary(userId, date)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

package com.expensetracker.expense.controller;


import com.expensetracker.analytics.dto.AnalyticsResponse;
import com.expensetracker.analytics.dto.TimePeriod;
import com.expensetracker.expense.service.analytics.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public AnalyticsResponse getSummary(
            @RequestParam(defaultValue = "MONTHLY") TimePeriod period,
            @AuthenticationPrincipal String userId   ) {
        return analyticsService.getAnalytics(period,userId);
    }
}

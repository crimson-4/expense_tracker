package com.expensetracker.expense.service.kafka;

import com.expensetracker.analytics.service.AnalyticsAggregationService;
import com.expensetracker.expense.dto.events.ExpenseCreatedEvent;
import com.expensetracker.expense.dto.events.ExpensePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseEventsConsumer {

    private final ObjectMapper objectMapper;
    private final AnalyticsAggregationService analyticsService;

    @KafkaListener(topics = "expense-events", groupId = "analytics-service-group")
    public void consumeExpenseEvent(String message) {
        try {
            ExpenseCreatedEvent event = objectMapper.readValue(message, ExpenseCreatedEvent.class);
            ExpensePayload payload = event.getPayload();
            log.info("Received expense created event: {}", payload);

            analyticsService.process(payload);

        } catch (Exception e) {
            log.error("Failed to consume expense event", e);
        }
    }
}

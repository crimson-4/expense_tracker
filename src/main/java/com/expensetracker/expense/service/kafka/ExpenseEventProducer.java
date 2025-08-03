package com.expensetracker.expense.service.kafka;

import com.expensetracker.expense.dto.events.ExpenseCreatedEvent;
import com.expensetracker.expense.dto.events.ExpensePayload;
import com.expensetracker.expense.model.Expense;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishExpenseCreated(Expense expense) throws JsonProcessingException {
        ExpenseCreatedEvent event = ExpenseCreatedEvent.builder()
                .eventType("EXPENSE_CREATED")
                .payload(
                        ExpensePayload.builder()
                                .id(expense.getId())
                                .userId(expense.getUserId())
                                .title(expense.getTitle())
                                .amount(expense.getAmount())
                                .category(expense.getCategory())
                                .date(expense.getDate())
                                .notes(expense.getNotes())
                                .build()
                )
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(event);
        kafkaTemplate.send("expense-events", json);
    }
}

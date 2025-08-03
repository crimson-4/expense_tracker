package com.expensetracker.expense.service.expense;

import com.expensetracker.expense.dto.ExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.dto.expenseEdit.UpdateExpenseRequest;
import com.expensetracker.expense.model.Expense;
import com.expensetracker.expense.repository.ExpenseRepository;
import com.expensetracker.expense.service.kafka.ExpenseEventProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseEventProducer expenseEventProducer;

    public ExpenseResponse createExpense(ExpenseRequest request, String userId) throws JsonProcessingException {
        Expense expense = Expense.builder()
                .title(request.getTitle())
                .amount(request.getAmount())
                .category(request.getCategory())
                .date(request.getDate())
                .notes(request.getNotes())
                .userId(userId)
                .build();
        Expense saved = expenseRepository.save(expense);

        expenseEventProducer.publishExpenseCreated(saved);

        return mapToResponse(saved);
    }

    public ExpenseResponse mapToResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .title(expense.getTitle())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .notes(expense.getNotes())
                .build();
    }
    public Expense updateExpense(Long expenseId, UpdateExpenseRequest request, String userId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        if (!expense.getUserId().equals(userId)) {
            throw new AccessDeniedException("You cannot edit this expense");
        }

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setNotes(request.getNotes());

        return expenseRepository.save(expense);
    }

    public List<ExpenseResponse> listExpenses(String userId, String startDateStr, String endDateStr, String category) {
        LocalDate startDate = startDateStr != null ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = endDateStr != null ? LocalDate.parse(endDateStr) : null;

        List<Expense> expenses = expenseRepository.findAllByUserIdAndFilters(userId, startDate, endDate, category);

        return expenses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteExpense(Long expenseId, String userId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found")); // or use custom exception

        if (!expense.getUserId().equals(userId)) {
            throw new AccessDeniedException("You cannot delete this expense");
        }

        expenseRepository.delete(expense);
    }

}

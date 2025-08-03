package com.expensetracker.expense.controller;
import com.expensetracker.expense.dto.ExpenseRequest;
import com.expensetracker.expense.dto.ExpenseResponse;
import com.expensetracker.expense.dto.expenseEdit.UpdateExpenseRequest;
import com.expensetracker.expense.model.Expense;
import com.expensetracker.expense.service.expense.ExpenseService;
import com.expensetracker.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;


import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExpenseController {

    private  final ExpenseService expenseService;
    @PostMapping("/expense")
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody @Valid ExpenseRequest request,@AuthenticationPrincipal String id) throws JsonProcessingException {

        ExpenseResponse response=expenseService.createExpense(request,id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/expenses/{id}")
    public ResponseEntity<Expense> updateExpense(
            @PathVariable Long id,
            @RequestBody UpdateExpenseRequest request,
            @AuthenticationPrincipal String userId) {

        Expense updated = expenseService.updateExpense(id, request, userId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseResponse>> listExpenses(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String category,
            @AuthenticationPrincipal String userId
            ) {
        List<ExpenseResponse> expenses = expenseService.listExpenses(userId, startDate, endDate, category);
        return ResponseEntity.ok(expenses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long id,
            @AuthenticationPrincipal(expression = "email") String userId) {

        expenseService.deleteExpense(id, userId);
        return ResponseEntity.noContent().build();
    }


}

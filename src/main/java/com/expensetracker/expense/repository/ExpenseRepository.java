package com.expensetracker.expense.repository;

import com.expensetracker.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository  extends JpaRepository<Expense,Long> {
    @Query("SELECT e FROM Expense e WHERE e.userId = :userId "
            + "AND (:startDate IS NULL OR e.date >= :startDate) "
            + "AND (:endDate IS NULL OR e.date <= :endDate) "
            + "AND (:category IS NULL OR e.category = :category)")
    List<Expense> findAllByUserIdAndFilters(
            @Param("userId") String userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("category") String category
    );
    List<Expense> findByUserIdAndDateBetween(String userId, LocalDate start, LocalDate end);


}

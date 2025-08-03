package com.expensetracker.analytics.repository;

import com.expensetracker.analytics.model.UserDailySummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserDailySummaryRepository extends JpaRepository<UserDailySummary, Long> {

    Optional<UserDailySummary> findByUserIdAndDate(String userId, LocalDate date);
}

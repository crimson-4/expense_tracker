package com.expensetracker.analytics.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "user_daily_summary", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "summary_date"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDailySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "summary_date", nullable = false)
    private LocalDate date;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
}

package com.api.smartfarm.image;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TomatoSummaryRepository extends JpaRepository<TomatoSummary, Long> {
    Optional<TomatoSummary> findByDate(LocalDate date);
    List<TomatoSummary> findByDateBetween(LocalDate startDate, LocalDate endDate);
}

package com.api.smartfarm.condition;

import java.time.LocalDate;
import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long>{
//	Optional<DailySummary> findByDate(LocalDate date);
	List<DailySummary> findTop90ByOrderByDateDesc();
	void deleteByDateBefore(LocalDate date);
	List<DailySummary> findByDate(LocalDate date);
}

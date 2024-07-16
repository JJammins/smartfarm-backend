package com.api.smartfarm.condition;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DailyDataRepository extends JpaRepository<DailyData, Long>{
	List<DailyData> findAllByTimestampBetween(LocalDateTime start, LocalDateTime end);
	List<DailyData> findByTimestampBetweenOrderByTimestampDesc(LocalDateTime start, LocalDateTime end);
	
	@Modifying
	@Transactional
	@Query(value = "TRUNCATE TABLE daily_data", nativeQuery = true)
	void truncateTable();
	
    @Modifying
    @Query(value = "ALTER TABLE daily_data AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}

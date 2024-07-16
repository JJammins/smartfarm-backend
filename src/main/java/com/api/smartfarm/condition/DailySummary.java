package com.api.smartfarm.condition;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DailySummary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate date;
	private Double maxTemperature;
	private Double minTemperature;
	private Double avgTemperature;
	private Double maxHumidity;
	private Double minHumidity;
	private Double avgHumidity;
}

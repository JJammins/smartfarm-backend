package com.api.smartfarm.condition;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SensorDataService {

    @Autowired
    private DailyDataRepository dailyDataRepository;
    
    @Autowired
    private DailySummaryRepository dailySummaryRepository;
    
    public void saveData(double temperature, double humidity) {
        DailyData data = new DailyData();
        data.setTimestamp(LocalDateTime.now());
        data.setTemperature(temperature);
        data.setHumidity(humidity);
        dailyDataRepository.save(data);
    }
    
    // 테스트용 (매시간) : 0 0 * * * ?
    // 서비스용 (매일자정) : 0 0 0 * * ?
    //"0 30 17 * * *"
    @Scheduled(cron = "0 0 0 * * ?")
    public void createDailySummary() {
//    	LocalDateTime yesterday = LocalDateTime.now().minusMinutes(5);
        LocalDateTime yesterday = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime today = LocalDate.now().atStartOfDay();
        
        List<DailyData> dailyData = dailyDataRepository.findAllByTimestampBetween(yesterday, today);
        
        if (!dailyData.isEmpty()) {
            DoubleSummaryStatistics temp = dailyData.stream()
                    .mapToDouble(DailyData::getTemperature)
                    .summaryStatistics();
            DoubleSummaryStatistics humi = dailyData.stream()
                    .mapToDouble(DailyData::getHumidity)
                    .summaryStatistics();
            
            DailySummary summary = new DailySummary();
            summary.setDate(yesterday.toLocalDate());
            summary.setMaxTemperature(temp.getMax());
            summary.setMinTemperature(temp.getMin());
            summary.setAvgTemperature(temp.getAverage());
            summary.setMaxHumidity(humi.getMax());
            summary.setMinHumidity(humi.getMin());
            summary.setAvgHumidity(humi.getAverage());
            
            dailySummaryRepository.save(summary);
        }
        
        // 일일 데이터 테이블 초기화 및 자동 증가값 리셋
        dailyDataRepository.truncateTable();
        dailyDataRepository.resetAutoIncrement();
        
        // 90일 이전의 요약 데이터 삭제
        LocalDate ninetyDaysAgo = LocalDate.now().minusDays(90);
        dailySummaryRepository.deleteByDateBefore(ninetyDaysAgo);
    }
    
    public List<DailySummary> getDailySummary() {
        return dailySummaryRepository.findTop90ByOrderByDateDesc();
    }
    
    public List<DailySummary> getYesterdaySummary(){
    	LocalDate yesterday = LocalDate.now().minusDays(1);
    	return dailySummaryRepository.findByDate(yesterday);
    }
}

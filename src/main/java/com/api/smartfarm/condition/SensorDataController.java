package com.api.smartfarm.condition;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SensorDataController {

    @Autowired
    private SensorDataService sensorDataService;
    
    @PostMapping("/saveData")
    public ResponseEntity<String> saveData(@RequestBody Map<String, Double> data) {
    	Double temperature = data.get("temperature");
    	Double humidity = data.get("humidity");
    	
    	if (temperature == null || humidity == null) {
    		return ResponseEntity.badRequest().body("null");
    	}
    	
        sensorDataService.saveData(temperature, humidity);
        return ResponseEntity.ok("Data saved successfully");
    }
    
    @GetMapping("/dailySummary")
    public ResponseEntity<List<DailySummary>> getDailySummary() {
        List<DailySummary> summaries = sensorDataService.getDailySummary();
        return ResponseEntity.ok(summaries);
    }
    
    @GetMapping("/yesterdaySummary")
    public ResponseEntity<List<DailySummary>> getYesterdaySummary(){
    	List<DailySummary> summaries = sensorDataService.getYesterdaySummary();
    	return ResponseEntity.ok(summaries);
//    public ResponseEntity<List<DailySummary>> getYesterdaySummary(@RequestParam String uid){
//    	System.out.println(uid);
//
//    	List<DailySummary> summaries = sensorDataService.getYesterdaySummary();
//    	return ResponseEntity.ok(summaries);
    }
}

package com.api.smartfarm.condition;

import java.util.ArrayList;
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

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ListUsersPage;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SensorDataController {
	
	public List<String> getUids() throws Exception {
    	
    	List<String> uids = new ArrayList<String>();
		ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
		while (page != null) {
		  for (ExportedUserRecord user : page.getValues()) {
		    System.out.println("User: " + user.getUid());
		  }
		  page = page.getNextPage();
		}

		// Iterate through all users. This will still retrieve users in batches,
		// buffering no more than 1000 users in memory at a time.
		page = FirebaseAuth.getInstance().listUsers(null);
		for (ExportedUserRecord user : page.iterateAll()) {
		  System.out.println("User: " + user.getUid());
		  uids.add(user.getUid());
		}
		return uids;
	}
	
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
    public ResponseEntity<?> getDailySummary(@RequestParam(required = false) String uid) throws Exception {
    	System.out.println(uid);
    	System.out.println(getUids().contains(uid));
    	
    	if(uid == null || getUids().contains(uid) == false) {
    		return ResponseEntity.ok(null);
    	}
        List<DailySummary> summaries = sensorDataService.getDailySummary();
        return ResponseEntity.ok(summaries);
    }
    
    @GetMapping("/yesterdaySummary")
    public ResponseEntity<?> getYesterdaySummary(@RequestParam(required = false) String uid) throws Exception{
    	System.out.println(uid);
    	System.out.println(getUids().contains(uid));
    	
    	if(uid == null || getUids().contains(uid) == false) {
	    	return ResponseEntity.ok(null);
    	}
    	List<DailySummary> summaries = sensorDataService.getYesterdaySummary();
    	return ResponseEntity.ok(summaries);
    }
}

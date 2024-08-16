package com.api.smartfarm.image;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ListUsersPage;

@RestController
@RequestMapping("/api/images")
public class ImageController {
	
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
    private FirebaseStorageService firebaseStorageService;
    
    @Autowired
    private ImageMetadataRepository imageMetadataRepository;

    @PostMapping
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return firebaseStorageService.uploadImage(file);
    }

    @GetMapping(value = "/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("fileName") String fileName, @RequestParam(required = false) String uid) throws Exception{
    	System.out.println(uid);
    	System.out.println(getUids().contains(uid));
    	
    	if(uid == null || !getUids().contains(uid)) {
    		
    		byte[] empty = null;
	    	return empty;
    	}
    	
        return firebaseStorageService.getImage(fileName);
    }
    
    @GetMapping("/metadata")
    public ResponseEntity<List<ImageMetadata>> getImageMetadataByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String uid) throws Exception {
        
        if (uid == null || !getUids().contains(uid)) {
            return ResponseEntity.badRequest().build();
        }

        List<ImageMetadata> metadata = imageMetadataRepository.findByUploadDate(date);
        return ResponseEntity.ok(metadata);
    }
    
//    @GetMapping("/metadata/week")
//    public ResponseEntity<List<ImageMetadata>> getImageMetadataForLastWeek(
//            @RequestParam(required = false) String uid) throws Exception {
//        
//        // UID 검증
//        if (uid == null || !getUids().contains(uid)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        // 오늘 날짜와 일주일 전 날짜 계산
//        LocalDate today = LocalDate.now();
//        LocalDate oneWeekAgo = today.minusDays(7);
//
//        // 일주일간의 데이터를 가져옴
//        List<ImageMetadata> metadata = imageMetadataRepository.findByUploadDateBetween(oneWeekAgo, today);
//
//        return ResponseEntity.ok(metadata);
//    }
    
}
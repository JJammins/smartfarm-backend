//package com.api.smartfarm.user;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//
//	@Autowired
//	UserRepository userRepository;
//	
//	@PostMapping("/login")
//	public ResponseEntity<String> createUser(@RequestBody User user) {
//		Optional<User> existingUser = userRepository.findByUid(user.getUid());
//		
//		if (existingUser.isPresent()) {
//			return ResponseEntity.ok("로그인 성공");
//		}else {
//			userRepository.save(user);
//			return ResponseEntity.ok("계정 생성 성공");
//		}
//	}
//
//}

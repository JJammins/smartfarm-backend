package com.api.smartfarm.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // uid로 사용자 조회 메서드 추가
    User findByUid(String uid);
}

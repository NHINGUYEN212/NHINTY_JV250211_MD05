package com.ra.md05_session10.repository;

import com.ra.md05_session10.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    @Query("select u from User u where (:search is null or u.username like concat('%',:search,'%') )")
    Page<User> findByUsernameContaining(@Param("search") String search, Pageable pageable);
}

package com.cupfeedeal.domain.User.repository;

import com.cupfeedeal.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);

    @Query("SELECT u.userId FROM User u WHERE u.deletedAt IS NOT NULL AND u.deletedAt < :thresholdDate")
    List<Long> findUserIdsToDelete(LocalDateTime thresholdDate);

    @Modifying
    @Query("DELETE FROM User u WHERE u.userId IN :userIds")
    void deleteUsersByIds(List<Long> userIds);
}

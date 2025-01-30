package com.cupfeedeal.domain.Auth.service;

import com.cupfeedeal.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCleanupScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 3 * * ?")
    @Transactional
    public void deleteOldUsers() {
        LocalDateTime thresholdDate = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(30);
        List<Long> userIdsToDelete = userRepository.findUserIdsToDelete(thresholdDate);

        if (!userIdsToDelete.isEmpty()) {
            log.info("30일 이상 지난 탈퇴 회원 삭제 시작 - 삭제 대상 userIds: {}", userIdsToDelete);
            userRepository.deleteUsersByIds(userIdsToDelete);
            log.info("30일 이상 지난 탈퇴 회원 삭제 완료 - 삭제된 userIds: {}", userIdsToDelete);
        } else {
            log.info("삭제할 회원 없음 (30일 이상 경과한 탈퇴 회원 없음)");
        }
    }



}

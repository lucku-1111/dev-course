package com.example.spring.authservice.scheduler;

// * 탈퇴 saga 복구 배치 - "미완경 saga를 끝까지 밀어주는" 장치
// UserService.withdraw()는 커밋1(WITHDRAWING) -> board 호출 -> 커밋2(WITHDRAWING)의 연쇄인데,
// 커밋1과 커밋2 "사이"에 auth가 죽으면 사용자는 WITHDRAWING에 영원히 갇힌다.

import com.example.spring.authservice.config.client.BoardClient;
import com.example.spring.authservice.domain.entity.User;
import com.example.spring.authservice.domain.entity.UserStatus;
import com.example.spring.authservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawlRecoveryScheduler {

    private static final Duration GRACE_PERIOD = Duration.ofMinutes(5);

    private final UserRepository userRepository;
    private final BoardClient boardClient;

    // 앱 기동 10초 뒤 첫 실행, 이후 "이전 실행이 끝난 시점"부터 5분 간격
    @Scheduled(initialDelay = 10_000, fixedDelay = 300_000)
    public void recoveryStuckWithDrawals() {

        List<User> stuckUsers = userRepository.findByStatusAndStatusUpdatedAtBefore(
                UserStatus.WITHDRAWING,
                LocalDateTime.now().minus(GRACE_PERIOD)
        );

        if (stuckUsers.isEmpty()) {
            return; // 잔류 없음
        }

        log.warn("[탈퇴 saga 복구] 미완결 잔류 {}건 발견 - 재개 시작", stuckUsers.size());

        for (User user : stuckUsers) {

            try {

                boardClient.deleteUserContents(user.getUserId());
                userRepository.save(user.completeWithdrawal());
                log.info("[탈퇴 saga 복구] 재개 완료. userId : {}", user.getUserId());

            } catch (Exception e) {
                // 한 명 실패가 나머지 복구를 막지 않도록 개별 try-catch
                // board가 아직 죽어 있으면 다음 주기에 다시 시도된다.ㄴ
                log.warn("[탈퇴 saga 복구] 재개 실패 - 다음 주기에 재시도. userId : {}, 원인 : {}", user.getUserId(), e.getMessage());
            }

        }


    }


}

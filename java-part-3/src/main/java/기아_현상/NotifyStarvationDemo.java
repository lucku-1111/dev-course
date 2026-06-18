package 기아_현상;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

// =====================================================================
//  notify() vs notifyAll() — 기아(교착) 현상 비교 데모
// ---------------------------------------------------------------------
//  핵심 원리
//   - notify()    : 대기 중인 스레드 "딱 하나"만 임의로 깨운다
//   - notifyAll() : 대기 중인 스레드 "전부"를 깨운다 (각자 조건 재확인)
//
//  같은 락에서 "서로 다른 조건"으로 기다리는 스레드가 섞여 있으면,
//  notify()가 '조건이 안 맞는 엉뚱한 스레드'를 깨울 수 있다.
//  깨워진 스레드는 다시 wait()로 돌아가지만, 다른 스레드는 깨우지 않으므로
//  '진짜로 진행 가능한 스레드'가 영영 못 깨어난다 → 모두 대기 = 교착/기아.
//
//  여기서는 생산자-소비자(용량 1 버퍼)로 그 상황을 재현한다.
//   - 생산자: 버퍼가 "가득 차면" 대기   (조건 A)
//   - 소비자: 버퍼가 "비어 있으면" 대기 (조건 B)
//  둘 다 같은 buffer 객체의 락에서 wait/notify 하므로 엉뚱한 깨움이 발생한다.
// =====================================================================

class BoundedBuffer {
    // ★ 이 값만 바꿔서 두 경우를 비교한다 ★
    //   false → notify()    : 교착에 잘 빠진다
    //   true  → notifyAll() : 정상 동작
    static final boolean USE_NOTIFY_ALL = true;

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 1; // 용량을 1로 두면 교착이 잘 재현된다

    // 진행 상황 추적용 (감시 스레드가 교착을 판별하는 데 사용)
    static final AtomicInteger totalConsumed = new AtomicInteger(0);

    public synchronized void put(int value) throws InterruptedException {
        // while: 깨어난 뒤에도 조건을 반드시 다시 확인해야 한다 (if로 쓰면 버그)
        while (queue.size() == capacity) {
            wait(); // 버퍼가 가득 차서 대기 (생산자 조건)
        }
        queue.add(value);
        signal();
    }

    public synchronized int take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // 버퍼가 비어서 대기 (소비자 조건)
        }
        int value = queue.poll();
        totalConsumed.incrementAndGet();
        signal();
        return value;
    }

    // notify / notifyAll 을 한 곳에서 토글
    private void signal() {
        if (USE_NOTIFY_ALL) {
            notifyAll();
        } else {
            notify();
        }
    }
}

public class NotifyStarvationDemo {

    static final int PRODUCERS = 2;
    static final int CONSUMERS = 2;
    static final int ITEMS_PER_PRODUCER = 1000; // 충분히 반복해야 교착이 빨리 드러난다

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer buffer = new BoundedBuffer();
        int expectedTotal = PRODUCERS * ITEMS_PER_PRODUCER;

        System.out.println("모드: " + (BoundedBuffer.USE_NOTIFY_ALL ? "notifyAll()" : "notify()"));
        System.out.println("목표 소비 개수: " + expectedTotal + "\n");

        // 생산자 스레드들
        for (int p = 0; p < PRODUCERS; p++) {
            final int id = p;
            new Thread(() -> {
                try {
                    for (int i = 0; i < ITEMS_PER_PRODUCER; i++) {
                        buffer.put(i);
                    }
                } catch (InterruptedException ignored) {
                }
            }, "producer-" + id).start();
        }

        // 소비자 스레드들
        for (int c = 0; c < CONSUMERS; c++) {
            new Thread(() -> {
                try {
                    while (BoundedBuffer.totalConsumed.get() < expectedTotal) {
                        buffer.take();
                    }
                } catch (InterruptedException ignored) {
                }
            }, "consumer-" + c).start();
        }

        // - 감시(watchdog) 스레드
        // 일정 시간 동안 소비 개수가 전혀 늘지 않으면 "교착"으로 판단한다.
        int lastCount = -1;
        int stalledChecks = 0;
        while (BoundedBuffer.totalConsumed.get() < expectedTotal) {
            Thread.sleep(500);
            int now = BoundedBuffer.totalConsumed.get();
            if (now == lastCount) {
                stalledChecks++;
            } else {
                stalledChecks = 0;
                lastCount = now;
            }

            // 0.5초 × 4회 = 2초간 진행이 멈추면 교착으로 간주
            if (stalledChecks >= 4) {
                System.out.println("\n교착(기아) 발생!");
                System.out.println("   소비한 개수: " + now + " / " + expectedTotal
                        + " 에서 멈춤 (모든 스레드가 서로를 기다리는 중)");
                System.out.println("   → notify()가 '조건이 안 맞는 스레드'를 깨워서,");
                System.out.println("      정작 진행 가능한 스레드가 깨어나지 못했습니다.");
                System.out.println("   USE_NOTIFY_ALL = true 로 바꾸면 해결됩니다.");
                System.exit(0);
            }
        }

        System.out.println("\n✅ 정상 완료! 모든 항목(" + expectedTotal + "개)을 소비했습니다.");
        System.out.println("   notifyAll()은 대기 중인 모두를 깨우므로,");
        System.out.println("   '진짜 진행 가능한 스레드'가 반드시 깨어나 교착이 없습니다.");
        System.exit(0);
    }
}
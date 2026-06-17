
// * 싱글스레드 vs 멀티스레드

// - 멀티스레드가 항상 더 빠른 건 아니다. 오히려 더 느릴 수도 있다.
// - 그래서 단순 계산만 하는 작업이라면 싱글스레드가 더 효율적일 수 있다.
// 스레드를 번갈아 바꾸는 데 드는 비용(= 작업 전환, 컨택스트 스위칭) 때문이다.
// - 멀티 스레드가 유리한 경우 : 서로 다른 자원을 쓰는 작업

class C_thread_3_1 extends Thread{
    @Override
    public void run() {
        for ( int i = 0; i < 300; i++ ) {
            System.out.printf("%s ", "|");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("소요시간2 : " + (endTime - C_thread_3.startTime) + "ms");
    }
}

public class C_thread_3 {

    static long startTime = 0;

    // - 싱글스레드
    public static void exam1() {
        long startTime = System.currentTimeMillis();
        for ( int i = 0; i < 300; i++ ) {
            System.out.printf("%s ", "-");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("소요시간1 : " + (endTime - startTime) + "ms");
        for ( int i = 0; i < 300; i++ ) {
            System.out.printf("%s ", "|");
        }
        endTime = System.currentTimeMillis();
        System.out.println("소요시간2 : " + (endTime - startTime) + "ms");
    }

    // - 멀티스레드
    public static void exam2() {
        C_thread_3_1 thread = new C_thread_3_1();
        thread.start();
        startTime = System.currentTimeMillis();
        // main스레드가 출력
        for ( int i = 0; i < 300; i++ ) {
            System.out.printf("%s ", "-");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("소요시간1 : " + (endTime - startTime) + "ms");
    }

    public static void main(String[] args) {
        exam2();
    }
}

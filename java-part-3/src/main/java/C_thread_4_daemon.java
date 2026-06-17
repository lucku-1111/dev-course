
// * 데몬 스레드
// - 메인 작업을 "뒤에서 도와주는" 보조 스레드
// - 일반 스레드가 전부 끝나면, 데몬 스레드도 같이 종료된다.

// * 실제
// - 가비지 컬렉터(GC), 워드의 자동저장,...

public class C_thread_4_daemon implements Runnable {

    static boolean autoSave = false;

    // 3초 마다 한 번씩 "자동저장이 켜져 있으면" 저장을 실행
    // 데몬스레드라서 main이 끝나면 함께 자동 저장된다.
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep( 3*1000 );
                if ( autoSave ) {
                    autoSave();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void autoSave() {
        System.out.println("작업 파일이 자동 저장되었습니다.");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new C_thread_4_daemon());
        thread.setDaemon(true);
        thread.start();

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(i);
            if ( i == 5 ) autoSave = true;
        }

        System.out.println("프로그램을 종료합니다.");
    }
}

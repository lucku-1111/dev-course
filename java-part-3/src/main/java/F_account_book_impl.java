import java.util.*;

public class F_account_book_impl implements F_account_book {

    private Map<String, List<F_item>> data = new HashMap<>();
    private Scanner sc;

    public F_account_book_impl(Scanner sc) {
        this.sc = sc;
    }

    // 1. 내역 추가
    @Override
    public void addAccount() {
        System.out.println("날짜 입력 (예: 2026-06-18)");
        String date = sc.nextLine().trim();

        // 같은 날짜가 이미 있으면 기존 목록에 이어서 추가
        // key가 맵에 있으면 -> 거기에 맵핑된 값을 반환
        // key가 없으면 -> 두 번째 인자로 넘긴 값을 반환
        List<F_item> list = data.getOrDefault(date, new ArrayList<>());

        while (true) {
            System.out.println("항목 이름: ");
            String name = sc.nextLine().trim();
            System.out.println("금액: ");
            int price = readInt();

            list.add( new F_item(name, price) );

            System.out.println("더 추가할까요? (y/n)");

            if (sc.nextLine().trim().equals("n")) break;
        }

        data.put(date, list);
        System.out.println("[" + date + "] 등록완료");

        printItem(data.get(date));
    }

    // 2. 내역 조회
    @Override
    public void showAccount() {
        if (data.isEmpty()) {
            System.out.println("기록이 없습니다.");
            return;
        }

        System.out.println("=== 기록된 날짜 ===");
        List<String> dates = new ArrayList<>(data.keySet());
        Collections.sort(dates, Collections.reverseOrder()); // 최신순
        for (String date : dates) {
            System.out.println(date);
        }

        System.out.println("조회할 날짜: ");
        String date = sc.nextLine().trim();
        if (!data.containsKey(date)) {
            System.out.println("존재하지 않는 날짜입니다.");
            return;
        }

        System.out.println("[" + date + "]");
        printItem(data.get(date));
    }

    @Override
    public void deleteAll() {
        System.out.println("전체 삭제할 날짜 입력: ");
        String date = sc.nextLine().trim();

        if (data.containsKey(date)) {
            data.remove(date);
            System.out.println(date + " 삭제되었습니다.");
        } else {
            System.out.println("존재하지 않는 날짜입니다.");
        }
    }

    @Override
    public void deleteItem() {
        System.out.println("삭제할 날짜 입력: ");
        String date = sc.nextLine().trim();

        if (!data.containsKey(date)) {
            System.out.println("존재하지 않는 날짜입니다.");
            return;
        }

        List<F_item> items = data.get(date);
        for (int i = 0; i < items.size(); i++) {
            F_item item = items.get(i);
            System.out.println( (i + 1) + ". " + item.getName() + " : " + item.getPrice() + "원" );
        }

        System.out.println("삭제할 번호: ");
        int num = readInt();
        if (num < 1 || num > items.size()) {
            System.out.println("잘못된 번호입니다.");
            return;
        }

        F_item removed = items.remove(num - 1);
        System.out.println(removed.getName() + " 삭제되었습니다.");

        if (items.isEmpty()) {
            data.remove(date);
            System.out.println("(" + date + ") 의 항목 모두 사려져 날짜도 삭제됨");
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자로 다시 입력");
            }
        }
    }

    private void printItem(List<F_item> list) {
        int sum = 0;
        for ( F_item item : list ) {
            System.out.println(item.getName() + " : " + item.getPrice() + "원");
            sum += item.getPrice();
        }
        System.out.println("합계 : " + sum + "원");
    }
}

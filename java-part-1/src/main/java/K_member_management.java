import java.util.Scanner;

class Member {
    String name;
    String email;
    String phone;

    public Member(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "[이름] " + name + ", [메일] " + email + ", [연락처] " + phone;
    }
}

public class K_member_management {

    private Member[] members;
    private int totalCnt;
    private int memberCnt = 0;
    private Scanner sc = new Scanner(System.in);

    public K_member_management(int totalCnt) {
        this.totalCnt = totalCnt;
        this.members = new Member[totalCnt];
    }

    public int printPricePlan() {
        System.out.println("[요금제를 선택하세요]");
        System.out.println("[1]Lite : 10명 [2]Basic : 20명 [3]Premium : 30명");
        return sc.nextInt();
    }

    public int printMenu() {
        System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : " + memberCnt + "/" + totalCnt + "]");
        System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
        System.out.println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제");
        System.out.println("[7]프로그램 종료");
        return sc.nextInt();
    }

    public void addMember() {
        if (memberCnt == totalCnt) {
            System.out.println("회원이 꽉찼습니다.");
            return;
        }

        System.out.println("이름을 입력하세요.");
        String name = sc.nextLine();
        System.out.println("이메일을 입력하세요.");
        String email = sc.nextLine();
        System.out.println("연락처를 입력하세요.");
        String phone = sc.nextLine();

        if (checkEmail(email)) {
            System.out.println("이미 존재하는 회원입니다.");
            return;
        }

        members[memberCnt++] = new Member(name, email, phone);
    }

    public boolean checkEmail(String email) {
        for (int i = 0; i < memberCnt; i++) {
            if (email.equals(members[i].email)) return true;
        }
        return false;
    }

    public void selectEmail() {
        System.out.println("[조회] 이메일을 입력하세요.");
        String email = sc.nextLine();

        for (int i = 0; i < memberCnt; i++) {
            if (email.equals(members[i].email)) {
                System.out.println(members[i]);
                return;
            }
        }
        System.out.println("찾으시는 정보가 없습니다.");
    }

    public void selectName() {
        System.out.println("[조회] 이름을 입력하세요.");
        String name = sc.nextLine();

        for (int i = 0; i < memberCnt; i++) {
            if (name.equals(members[i].name)) {
                System.out.println(members[i]);
                return;
            }
        }
        System.out.println("찾으시는 정보가 없습니다.");
    }

    public void selectAll() {
        if (memberCnt == 0) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }
        for (int i = 0; i < memberCnt; i++) {
            System.out.println(members[i]);
        }
    }

    public void updateMember() {
        System.out.println("[수정] 이메일을 입력하세요.");
        String email = sc.nextLine();

        for (int i = 0; i < memberCnt; i++) {
            if (email.equals(members[i].email)) {
                System.out.println("변경할 이름을 입력하세요.");
                members[i].name = sc.nextLine();
                System.out.println("변경할 이메일을 입력하세요.");
                members[i].email = sc.nextLine();
                System.out.println("변경할 연락처를 입력하세요.");
                members[i].phone = sc.nextLine();
                System.out.println("수정이 완료되었습니다.");
                return;
            }
        }
        System.out.println("찾으시는 회원이 없습니다.");
    }

    public void deleteMember() {
        System.out.println("[삭제] 이메일을 입력하세요.");
        String email = sc.nextLine();

        for (int i = 0; i < memberCnt; i++) {
            if (email.equals(members[i].email)) {
                for (int j = i; j < memberCnt - 1; j++) {
                    members[j] = members[j + 1];
                }
                members[--memberCnt] = null;
                System.out.println("삭제가 완료되었습니다.");
                return;
            }
        }
        System.out.println("찾으시는 회원이 없습니다.");
    }

    public void run() {
        int pricePlan = printPricePlan();

        if (pricePlan < 1 || pricePlan > 3) {
            System.out.println("잘못된 입력값입니다. [1 ~ 3의 숫자를 입력해주세요.]");
            return;
        }

        this.totalCnt = pricePlan * 10;
        this.members = new Member[totalCnt];

        while (true) {
            int menu = printMenu();
            switch (menu) {
                case 1: addMember();   break;
                case 2: selectEmail(); break;
                case 3: selectName();  break;
                case 4: selectAll();   break;
                case 5: updateMember(); break;
                case 6: deleteMember(); break;
                case 7:
                    System.out.println("이용해주셔서 감사합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력값입니다.");
            }
        }
    }

    public static void main(String[] args) {
        new K_member_management(0).run();
    }
}
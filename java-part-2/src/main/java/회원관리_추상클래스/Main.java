package 회원관리_추상클래스;

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static int printMenu(MemberManager manager) {
        System.out.println("[수행할 업무를 선택하세요 - 현재 회원수 : " + manager.getCount() + "/" + manager.getCapacity() + "]");
        System.out.println("[1]회원추가 [2]회원조회(메일) [3]회원조회(이름)");
        System.out.println("[4]회원전체조회 [5]회원정보 수정 [6]회원삭제");
        System.out.println("[7]프로그램 종료");
        return Integer.parseInt(sc.nextLine());
    }

    public static void addMember(MemberManager manager) {
        if (manager.isFull()) {
            System.out.println("회원이 꽉 찼습니다.");
        } else {
            System.out.println("등급 [1]일반 [2]VIP");
            int grade = Integer.parseInt(sc.nextLine());
            System.out.print("이름 > ");   String name  = sc.nextLine();
            System.out.print("이메일 > "); String email = sc.nextLine();
            System.out.print("연락처 > "); String phone = sc.nextLine();

            if (manager.existsEmail(email)) {
                System.out.println("이미 존재하는 회원입니다.");
            } else {
                Member m = (grade == 2) ? new VipMember(name, email, phone)
                        : new NormalMember(name, email, phone);
                manager.add(m);
            }
        }
    }

    public static void selectEmail(MemberManager manager) {
        System.out.println("[조회] 이메일을 입력하세요.");
        String email = sc.nextLine();
        Member member = manager.findByEmail(email);

        if (member == null) {
            System.out.println("찾으시는 정보가 없습니다.");
            return;
        }
        member.printInfo();
    }

    public static void selectName(MemberManager manager) {
        System.out.println("[조회] 이름을 입력하세요.");
        String name = sc.nextLine();
        Member member = manager.findByName(name);

        if (member == null) {
            System.out.println("찾으시는 정보가 없습니다.");
            return;
        }
        member.printInfo();
    }

    public static void selectAll(MemberManager manager) {
        if (manager.getCount() == 0) {
            System.out.println("등록된 회원이 없습니다.");
            return;
        }
        manager.printAll();
    }

    public static void updateMember(MemberManager manager) {
        System.out.println("[수정] 이메일을 입력하세요.");
        String email = sc.nextLine();
        System.out.println("변경할 이름을 입력하세요.");
        String name = sc.nextLine();
        System.out.println("변경할 이메일을 입력하세요.");
        String newEmail = sc.nextLine();
        System.out.println("변경할 연락처를 입력하세요.");
        String phone = sc.nextLine();

        if (manager.update(email, name, newEmail, phone)) {
            System.out.println("수정이 완료되었습니다.");
            return;
        }
        System.out.println("찾으시는 회원이 없습니다.");
    }

    public static void deleteMember(MemberManager manager) {
        System.out.println("[삭제] 이메일을 입력하세요.");
        String email = sc.nextLine();

        if (manager.delete(email)) {
            System.out.println("삭제가 완료되었습니다.");
            return;
        }
        System.out.println("찾으시는 회원이 없습니다.");
    }


    public static void main(String[] args) {
        System.out.println("[1]Lite:10 [2]Basic:20 [3]Premium:30");
        int plan = Integer.parseInt(sc.nextLine());
        MemberManager manager = new MemberManager(plan * 10);

        while (true) {
            while (true) {
                int menu = printMenu(manager);
                switch (menu) {
                    case 1: addMember(manager); break;
                    case 2: selectEmail(manager); break;
                    case 3: selectName(manager); break;
                    case 4: selectAll(manager); break;
                    case 5: updateMember(manager); break;
                    case 6: deleteMember(manager); break;
                    case 7:
                        System.out.println("이용해주셔서 감사합니다.");
                        return;
                    default:
                        System.out.println("잘못된 입력값입니다.");
                }
            }
        }
    }
}

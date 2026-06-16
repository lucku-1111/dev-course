package 회원관리_프로그램_최종;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("[1]Lite:10 [2]Basic:20 [3]Premium:30");
        PricePlan plan = null;
        while (plan == null) {
            plan = PricePlan.from(readInt(sc));
            if (plan == null) System.out.println("1~3 중에서 선택하세요.");
        }
        MemberManager manager = new MemberManager(plan.getCapacity());

        while (true) {
            System.out.println("\n[현재 " + manager.size() + "/" + manager.capacity() + "]");
            System.out.println("[1]추가 [2]메일조회 [3]이름조회 [4]전체 [5]수정 [6]삭제 [7]종료");
            int menu = readInt(sc);

            switch (menu) {
                case 1: {
                    if (manager.isFull()) { System.out.println("정원이 찼습니다."); break; }
                    System.out.println("등급 [1]일반 [2]VIP");
                    int grade = readInt(sc);
                    System.out.print("이름 > ");   String name  = sc.nextLine();
                    System.out.print("이메일 > "); String email = sc.nextLine();
                    System.out.print("연락처 > "); String phone = sc.nextLine();
                    if (manager.existsEmail(email)) { System.out.println("이미 있는 회원입니다."); break; }
                    Member m = (grade == 2)
                            ? new VipMember(name, email, phone)
                            : new NormalMember(name, email, phone);
                    manager.add(m);   // 다형성: Member로 추가
                    System.out.println("추가되었습니다.");
                    break;
                }
                case 2: {
                    System.out.println("[조회] 이메일을 입력하세요.");
                    String email = sc.nextLine();
                    Member member = manager.findByEmail(email);

                    if (member == null) {
                        System.out.println("찾으시는 정보가 없습니다.");
                        return;
                    }
                    member.printInfo();
                    break;
                }
                case 3: {
                    System.out.println("[조회] 이름을 입력하세요.");
                    String name = sc.nextLine();
                    Member member = manager.findByName(name);

                    if (member == null) {
                        System.out.println("찾으시는 정보가 없습니다.");
                        return;
                    }
                    member.printInfo();
                    break;
                }
                case 4: {
                    manager.printAll();
                    break;
                }
                case 5: {
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
                    break;
                }
                case 6: {
                    System.out.println("[삭제] 이메일을 입력하세요.");
                    String email = sc.nextLine();

                    if (manager.delete(email)) {
                        System.out.println("삭제가 완료되었습니다.");
                        return;
                    }
                    System.out.println("찾으시는 회원이 없습니다.");
                    break;
                }
                case 7: System.out.println("이용해주셔서 감사합니다."); return;
                default: System.out.println("1~7 중에서 선택하세요.");
            }
        }
    }

    static int readInt(Scanner sc) {
        try { return Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}

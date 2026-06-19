package 가계부_fileIO;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AccountBookImpl implements AccountBook {

    private final String DIR = "account_book";
    private Scanner sc;

    public AccountBookImpl(Scanner sc) {
        this.sc = sc;
        File folder = new File(DIR);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    @Override
    public void addAccount() {
        String today = LocalDate.now().toString();
        File file = new File(DIR, today + ".txt");

        int total = 0;
        StringBuilder sb = new StringBuilder();
        while (true) {
            System.out.println("항목 이름 > ");
            String name = sc.nextLine().trim();
            System.out.println("금액 > ");
            int price = readInt();
            total += price;

            sb.append(name + " : " + price + "원\n");
            System.out.println("더 추가할까요? (y/n)");

            if (sc.nextLine().trim().equals("n")) break;
        }
        sb.append("합계 : " + total + "원\n");

        try (FileWriter fw = new FileWriter(file, true)) {
            System.out.println(today + " 에 저장 완료!");
            fw.write(sb.toString());
        } catch (IOException e) {
            System.out.println("저장 중 오류: " + e.getMessage());
        }
    }

    @Override
    public void showAccount() {
        String[] dates = listDates();

        if (dates.length == 0) {
            System.out.println("기록이 없습니다.");
            return;
        }

        System.out.println("=== 기록된 날짜 ===");
        for (String date : dates) {
            System.out.println(date);
        }

        System.out.println("조회할 날짜 입력 > ");
        String date = sc.nextLine().trim();
        File file = new File(DIR, date + ".txt");
        if (!file.exists()) {
            System.out.println("해당 날짜에 존재하는 데이터가 없습니다.");
            return;
        }

        System.out.println("\n=== " + date + " ===");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAccount() {
        String[] dates = listDates();
        if (dates.length == 0) {
            System.out.println("기록이 없습니다.");
            return;
        }

        System.out.println("=== 기록된 날짜 ===");
        for (String date : dates) {
            System.out.println(date);
        }

        System.out.println("삭제할 날짜 입력 > ");
        String date = sc.nextLine().trim();
        File file = new File(DIR, date + ".txt");
        if (!file.exists()) {
            System.out.println("해당 날짜에 존재하는 데이터가 없습니다.");
            return;
        }

        if (file.delete()) {
            System.out.println("삭제되었습니다.");
        } else {
            System.out.println("삭제 실패");
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("숫자로 다시 입력해주세요.");
            }
        }
    }

    private String[] listDates() {
        File folder = new File(DIR);
        String[] files = folder.list();

        if (files == null) {
            return new String[0];
        }

        List<String> dates = new ArrayList<>();
        for (String name : files) {
            if (name.endsWith(".txt")) {
                dates.add(name.replace(".txt", ""));
            }
        }
        Collections.sort(dates, Collections.reverseOrder());

        return dates.toArray(new String[0]);
    }
}

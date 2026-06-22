package com.example.spring.springtheory.ch01.SOLID_5원칙;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Journal {
    private ArrayList<String> entries = new ArrayList<>();

    void add(String text) {
        entries.add(text);
    }

    String getText() {
        StringBuilder sb = new StringBuilder();

        for (String e : entries) {
            sb.append("- ").append(e).append("\n");
        }
        return sb.toString();
    }
}

class JournalSaver {
    void print(Journal j) {
        System.out.println(j.getText());
    }

    void saveToFile(Journal j, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(j.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Srp {
    public void run() {
        System.out.println("===== SRP: 단일 책임 =====");

        Journal journal = new Journal();

        journal.add("오늘은 자바를 배웠다.");
        journal.add("SOLID는 어렵지만 재밌다.");

        JournalSaver js = new JournalSaver();

        js.print(journal);
        js.saveToFile(journal, "일기장.txt");
    }
}

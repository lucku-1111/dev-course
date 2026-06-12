package MyLinkedList;

public class MyLinkedList {

    // [Step 1] 노드 한 칸: 데이터 + 앞/뒤 노드의 주소
    static class Node {
        String data;
        Node prev;   // 앞 노드
        Node next;   // 뒤 노드
        Node(String data) {
            // TODO: this.data 를 설정하세요.
            this.data = data;
        }
    }

    // [Step 2] 필드 (작성돼 있음)
    private Node head;   // 첫 노드
    private Node tail;   // 마지막 노드
    private int size;

    // [Step 3] 맨 뒤에 추가
    void addLast(String data) {
        Node node = new Node(data);
        // TODO: head == null 이면 head = tail = node;
        //       아니면 node.prev = tail;  tail.next = node;  tail = node;
        //       마지막에 size++
        if (head == null) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    // [Step 4] 연결 상태 출력 (제공됨 — 읽고 이해만 하세요)
    void printLinks() {
        Node cur = head;
        while (cur != null) {
            String p = (cur.prev == null) ? "null" : cur.prev.data;
            String n = (cur.next == null) ? "null" : cur.next.data;
            System.out.print("[" + p + " <- " + cur.data + " -> " + n + "] ");
            cur = cur.next;
        }
        System.out.println();
    }

    // [Step 5] 맨 앞에 추가
    void addFirst(String data) {
        Node node = new Node(data);
        // TODO: head == null 이면 head = tail = node;
        //       아니면 node.next = head;  head.prev = node;  head = node;
        //       마지막에 size++
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    // [Step 6] index번째 노드 찾기
    private Node nodeAt(int index) {
        // TODO: head 부터 시작해서 next 로 index 번 이동한 노드를 반환하세요.
        Node node = head;

        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    String get(int index) {
        // TODO: nodeAt(index).data 를 반환하세요.
        return nodeAt(index).data;
    }

    // [Step 7] index 위치에 삽입 (양옆 연결만 바꾸기)  ★핵심★
    void insert(int index, String data) {
        // TODO: index == 0 이면 addFirst, index == size 이면 addLast 로 처리.
        //       그 외:
        //         Node next = nodeAt(index);  Node prev = next.prev;
        //         Node node = new Node(data);
        //         node.prev = prev;  node.next = next;
        //         prev.next = node;  next.prev = node;
        //         size++
        if (index == 0) {
            addFirst(data);
        } else if (index == size - 1) {
            addLast(data);
        } else {
            Node next = nodeAt(index);
            Node prev = next.prev;
            Node node = new Node(data);
            node.prev = prev;
            node.next = next;
            prev.next = node;
            next.prev = node;
        }
        size++;
    }

    // [도전] index 위치 노드 삭제
    void remove(int index) {
        // TODO (도전): 삭제할 노드의 prev 와 next 를 서로 연결하고 size--
        //              (맨 앞/맨 뒤 삭제 시 head/tail 갱신 주의)
        if (index == 0) {
            Node next = head.next;
            head.next = null;
            next.prev = null;
            head = next;
        } else if (index == size - 1) {
            Node prev = tail.prev;
            prev.next = null;
            tail.prev = null;
            tail = prev;
        } else {
            Node node = nodeAt(index);
            Node next = node.next;
            Node prev = node.prev;
            node.next = null;
            node.prev = null;
            prev.next = next;
            next.prev = prev;
        }
        size--;
    }

    int size() { return size; }
}

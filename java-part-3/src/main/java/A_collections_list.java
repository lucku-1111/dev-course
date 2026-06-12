import java.util.*;

public class A_collections_list {

    // 1. ArrayList
    public static void exam1() {

        List<String> list = new ArrayList<>();

        // 요소 추가
        list.add("apple");
        list.add("banana");
        list.add("orange");
        list.add("grape");
        list.add("watermelon");

        // 특정 인덱스에  요소 추가
        list.add(1, "lemon");

        // 리스트의 크기 확인
        System.out.println("List size : " + list.size());

        // 인덱스를 사용하여 요소 접근
        System.out.println("Apple : " + list.get(0));

        // 요소 제거
        list.remove(2);

        // 특정 요소가 리스트에 포함되어 있는지 확인
        if (list.contains("banana")) {
            System.out.println("banana is in the list");
        }

        // 리스트의 모든 요소 제거
        list.clear();

        // 순회 방법 1 : for 루프 사용
        for ( int i = 0; i < list.size(); i++ ) {
            System.out.println(list.get(i));
        }

        // 순회 방법 2 : 향상된 for 루프 사용
        for ( String fruit : list ) {
            System.out.println(fruit);
        }

        // 순회 방법 3 : Iterator 사용
        Iterator<String> iterator = list.iterator();
        while ( iterator.hasNext() ) {
            String element = iterator.next();
            System.out.println(element);
        }

        // 순회 방법 4 : ListIterator 사용 (양방향 순회 가능)
        ListIterator<String> stringListIterator = list.listIterator();
        // 정방향
        while (stringListIterator.hasNext()) {
            String element = stringListIterator.next();
            System.out.println(element);
        }
        // 역방향
        while (stringListIterator.hasPrevious()) {
            String element = stringListIterator.previous();
            System.out.println(element);
        }

    }

    // 2. LinkedList
    public static void exam2() {

        List<String> list = new LinkedList<>();

        // 추가
        list.add("apple");
        list.add(1, "banana");

        list.removeLast();

        // 해당 값을 인덱스로 가져온다.
        System.out.println(list.get(0));

        // 순회 방법 1 : for 루프 사용
        for ( int i = 0; i < list.size(); i++ ) {
            System.out.println(list.get(i));
        }

        // 순회 방법 2 : 향상된 for 루프 사용
        for ( String fruit : list ) {
            System.out.println(fruit);
        }

        // 순회 방법 3 : Iterator 사용
        Iterator<String> iterator = list.iterator();
        while ( iterator.hasNext() ) {
            String element = iterator.next();
            System.out.println(element);
        }

        // 순회 방법 4 : ListIterator 사용 (양방향 순회 가능)
        ListIterator<String> stringListIterator = list.listIterator();
        // 정방향
        while (stringListIterator.hasNext()) {
            String element = stringListIterator.next();
            System.out.println(element);
        }
        // 역방향
        while (stringListIterator.hasPrevious()) {
            String element = stringListIterator.previous();
            System.out.println(element);
        }
    }

    // 3. Stack
    // 설명 : LIFO(Last In First Out) 후입선출 구조를 따른다.
    // Vector 기반 : Stack은 Vector 클래스를 상속받아 구현되었다.
    // 주요 메서드 : push() 요소삽입, pop() 요소제거, peek() 맨 위의 요소를 확인, empty() 스택이 비어 있는지를 확인
    public static void exam3() {
        Stack<String> stack = new Stack<>();

        // 추가
        stack.push("apple");
        stack.push("banana");

        // POP
        String topElement = stack.pop();
        System.out.println("topElement : " + topElement);

        // PEEK
        String peekElement = stack.peek();
        System.out.println("peekElement : " + peekElement);

        // EMPTY
        boolean isEmpty = stack.empty();
        System.out.println("isEmpty : " + isEmpty);

        // 순회 방법 1 : for 루프 사용
        for ( int i = 0; i < stack.size(); i++ ) {
            System.out.println( stack.get(i) );
        }

        // 순회 방법 2 : 향상된 for 루프 사용
        for (String element : stack) {
            System.out.println("element : " + element);
        }

        // 순회 방법 3 : Iterator 사용
        Iterator<String> iterator = stack.iterator();
        while ( iterator.hasNext() ) {
            String element = iterator.next();
            System.out.println("element : " + element);
        }

        // 순회 방법 4 : ListIterator 사용 (양방향 순회 가능)
        ListIterator<String> stringListIterator = stack.listIterator();
        // 정방향
        while (stringListIterator.hasNext()) {
            String element = stringListIterator.next();
            System.out.println("element : " + element);
        }
        // 역방향
        while (stringListIterator.hasPrevious()) {
            String element = stringListIterator.previous();
            System.out.println("element : " + element);
        }

        // 순회 방법 5 : pop()을 사용한 순회 (스택의 특성 활용)
        while ( !stack.isEmpty() ) {
            String element = stack.pop();
            System.out.println("element : " + element);
        }
    }

    public static void main(String[] args) {
        exam3();
    }
}

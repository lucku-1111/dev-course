package MyHashMap;

public class Main {

    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();

        map.put("apple", 1);
        map.put("banana", 2);
        System.out.println(map.get("apple"));    // 1
        System.out.println(map.get("cherry"));   // null
        System.out.println("size: " + map.size()); // 2

        map.remove("apple");
        System.out.println(map.get("apple"));    // null
        System.out.println(map.remove("seokwoo")); // null
        System.out.println("size: " + map.size()); // 1
    }
}

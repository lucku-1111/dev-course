import java.util.Map;
import java.util.HashMap;

public class A_collections_map {

    // 1. HashMap
    // 설명
    //	•	해시 기반: HashMap은 내부적으로 해시 테이블을 사용하여 데이터를 저장한다.
    //	•	빠른 성능: 일반적으로 키를 통한 검색, 삽입, 삭제 작업이 O(1)의 시간 복잡도를 가진다.
    //	•	순서 보장 없음: 요소들이 저장된 순서를 보장하지 않는다.
    public static void exam1() {
        Map<String, Integer> hashMap = new HashMap<>();

        // 요소 추가
        hashMap.put("apple", 10);
        hashMap.put("banana", 20);
        hashMap.put("orange", 30);
        hashMap.put("apple", 40);

        System.out.println(hashMap);

        // 특정 키의 값 얻기
        int value = hashMap.get("apple");
        System.out.println("value : " + value);

        // 요소 제거
        hashMap.remove("apple");
        System.out.println(hashMap);

        // 키의 존재 여부
        boolean containsed = hashMap.containsKey("orange");
        System.out.println("containsed : " + containsed);

        // 순회 방법 1 : entrySet() 방식
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // 순회 방법 2 : keySet() 방식
        for (String key : hashMap.keySet()) {
            System.out.println(key + " : " + hashMap.get(key));
        }

        // 순회 방법 3 : values() 방법
        for ( Integer val : hashMap.values() ) {
            System.out.println(" value : " + val);
        }
    }

    // 2. TreeMap
    // 설명
    //  •  이진 검색 트리 기반: TreeMap은 내부적으로 레드-블랙 트리 구조를 사용하여 데이터를 저장합니다.
    //  •  정렬된 순서: 키에 따라 요소들이 자동으로 정렬됩니다(기본적으로 오름차순).
    //  •  성능: 삽입, 삭제, 검색 작업이 O(log n)의 시간 복잡도를 가집니다.
    // 대부분의 경우는 HashMap이 기본 선택입니다. "키로 빠르게 찾고 저장만 하면 된다, 순서는 상관없다" → HashMap.
    // 반면 키가 정렬돼 있어야 하거나, 범위/이웃 키 탐색이 필요하면 TreeMap 을 씁니다.
    public static void exam2() {

    }

    public static void main(String[] args) {

    }
}

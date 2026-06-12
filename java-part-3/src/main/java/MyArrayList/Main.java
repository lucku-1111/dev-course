package MyArrayList;

public class Main {
    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();

        // --- Step 2~4 확인 ---  
        list.addLast("가");
        list.addLast("나");
        list.addLast("다");
        System.out.println("size = " + list.size());                 // 기대: 3  
        System.out.println("0,1,2 = " + list.get(0) + ", "
                + list.get(1) + ", "
                + list.get(2));                // 기대: 가, 나, 다  

        list.addFirst("앞");
        System.out.println("addFirst 후 0,1 = " + list.get(0) + ", " + list.get(1)); // 기대: 앞, 가  
        System.out.println("size = " + list.size());                 // 기대: 4

        list.insert(1, "두번째");
        // 기대: insert 후 0,1,2,3 = 앞, 두번째, 가, 나
        System.out.println("insert 후 0,1,2,3 = " + list.get(0) + ", " + list.get(1) + ", " + list.get(2) + ", " + list.get(3));
        System.out.println("size = " + list.size()); // 기대: 5

        list.remove(0);
        // 기대: remove 후 0,1,2,3 = 두번째, 가, 나, 다
        System.out.println("remove 후 0,1,2,3 = " + list.get(0) + ", " + list.get(1) + ", " + list.get(2) + ", " + list.get(3));
        System.out.println("size = " + list.size()); // 기대: 4
        // 기대: remove 후 0,1,2,3 = 두번째, 가, 나, 다
        list.remove(3);
        System.out.println("remove 후 0,1,2 = " + list.get(0) + ", " + list.get(1) + ", " + list.get(2));
        System.out.println("size = " + list.size()); // 기대: 3
    }
}
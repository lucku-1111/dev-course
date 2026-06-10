public class F_constructor {
    public static void main(String[] args) {
        // 기본 생성자를 사용하여 객체 생성
        F_person1 person1 = new F_person1();
        person1.display();

        // 매개변수가 있는 생성자를 사용하여 객체 생성
        F_person2 person2 = new F_person2("Allice", 30);
        person2.display();

        // 기본 생성자 호출
        F_person3 person3 = new F_person3();
        person3.display();

        // 이름만 초기화하는 생성자
        F_person3 person3_1 = new F_person3("Bob");
        person3_1.display();

        // 이름, 나이 초기화
        F_person3 person3_2 = new F_person3("David", 20);
        person3_2.display();

        // 원본 객체
        F_person4 person4 = new F_person4("Charlie", 25);

        // 복사 생성자를 사용하여 객체 복사
        F_person4 person4_1 = new F_person4(person4);

        // 생성자에서 다른 생성자 호출하기 -> this()
        // - 생성자의 이름으로 클래스 대신 this를 사용한다.
        // - 한 생성자에서 다른 생성자를 호출할 때는 반드시 첫 줄에서만 호출이 가능하다.
        F_person5 person5 = new F_person5();
    }
}

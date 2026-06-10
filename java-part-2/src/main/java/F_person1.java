public class F_person1 {
    // 멤버 변수, 필드
    String name;
    int age;

    // 기본 생성자
    public F_person1() {
        System.out.println("기본 생성자");
        // 초기화
        name = "Paul";
        age = 20;
    }

    public void display() {
        System.out.println(name + " " + age);
    }
}

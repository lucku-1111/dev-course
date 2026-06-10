class SuperPoint {
    int x = 10;
    int y = 20;

    public SuperPoint(int x, int y) {
        // super();는 Point의 조상인 Object클래스의 기본생성자인 Object()를 의미한다.
        System.out.println("나는 부모이다.");
        this.x = x;
        this.y = y;
    }
}

class SubPoint3D extends SuperPoint {
    int z = 30;

    public SubPoint3D(int x, int y, int z) {
        super(x, y);
        System.out.println("나는 자식이다.");
        this.z = z;
    }
}

public class I_super_constructor {
    public static void main(String[] args) {
        SubPoint3D subPoint3D = new SubPoint3D(10, 20, 30);

    }
}

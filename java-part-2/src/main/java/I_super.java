class SuperParent {
    int x = 10;
    int y = 20;

    public String getLocation() {
        return "x = " + x + ", y = " + y;
    }
}

class SuperChild extends SuperParent {
    int y = 30;
    int z = 40;

    void method() {
        System.out.println( "x = " + x );
        System.out.println("this.x = " + this.x );
        System.out.println("super.x = " + super.x );

        System.out.println( "y = " + y );
        System.out.println("this.y = " + this.y );
        System.out.println("super.y = " + super.y );
    }

    @Override
    public String getLocation() {
        return super.getLocation() + ", z = " + z;
    }
}

public class I_super {
    public static void main(String[] args) {
        SuperChild superChild = new SuperChild();
        superChild.method();
        System.out.println(superChild.getLocation());
    }
}

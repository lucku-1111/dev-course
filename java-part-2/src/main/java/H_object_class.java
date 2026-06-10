class Radio { // extends Object 가 생략되어 있다(컴파일러가 자동으로 추가)

}

public class H_object_class {
    public static void main(String[] args) {
        Radio radio = new Radio();

        System.out.println(radio.equals(radio));
        System.out.println(radio.toString());
        System.out.println(radio.getClass());
        //...
    }
}

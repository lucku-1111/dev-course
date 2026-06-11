// 추상 클래스: 인스턴스화 할 수 없으며, 공통된 속성이나 메서드를 정의하고, 일부 메서드는 자식 클래스에서 구현하도록 강제한다.
// 추상 메서드: 선언만 있고, 구현은 없으며, 반드시 자식 클래스에서 오버라이딩되어야 한다.

/* 단점
1. 상속의 제약
- 단일 상속의 제약
자바는 단일 상속만 지원하므로, 한 클래스가 두 개 이상의 추상 클래스를 상속받을 수 없다.
이는 복잡한 다중 상속 구조를 만들고자 할 때 제약될 수 있다.
이 경우 인터페이스를 사용해야 하지만, 인터페이스는 구현을 가질 수 없다는 다른 제약이 있다.
2. 구현의 강제성
- 모든 자식 클래스에서 구현 강제
추상 메서드를 상속받는 모든 자식 클래스는 해당 메서드를 반드시 구현해야 한다.
때로는 모든 자식 클래스에서 동일한 메서드 구현이 필요하지 않을 수도 있다.
이런 경우에도 모든 자식 클래스에서 해당 메서드(추상 메서드)를 구현해야 하는 부담이 있다.
불필요한 오버라이딩이 발생할 수 있다.
3. 추상 클래스의 유연성 부족
- 추상 클래스의 역할 중첩
추상 클래스는 주로 공통기능을 제공하기 위해 사용되지만, 때로는 이 공통 기능이 모든 하위 클래스에 적합하지 않을 수 있다.
이 경우 추상 클래스가 불필요하게 많은 역할을 담당하게 되어 설계가 복잡해질 수 있다.
- 구현 상속의 어려움
추상 클래스에서 공통 구현을 제공하는 것은 유용할 수 있지만, 이는 동시에 코드 재사용을 강제하여 하위 클래스에서 원하지 않는 동작이 상속될 수 있다.
또한, 공통된 코드가 적절히 분리되지 않으면 추상 클래스의 유지보수가 어려워질 수 있다.
 */

abstract class L_animal {
    abstract void makeSound();

    void breathe() {
        System.out.println("animal breathing");
    }
}

class L_dog extends L_animal {
    @Override
    void makeSound() {
        System.out.println("dog sound");
    }
}

class L_cat extends L_animal {
    @Override
    void makeSound() {
        System.out.println("cat sound");
    }
}


public class L_abstract {
    public static void main(String[] args) {
        // 추상 클래스는 인스턴스화할 수 없다.
        // L_animal animal = new L_animal();

        L_animal dog = new L_dog();
        L_animal cat = new L_cat();

        dog.makeSound();
        dog.breathe();

        cat.makeSound();
        cat.breathe();
    }
}

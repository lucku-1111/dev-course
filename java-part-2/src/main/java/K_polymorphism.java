class K_animal {
    public void sound() {
        System.out.println("Animal sound");
    }
}

class K_dog extends K_animal {
    public void fetch() {
        System.out.println("Dog fetches a ball");
    }

    @Override
    public void sound() {
        System.out.println("Dog sound");
    }
}

class K_cat extends K_animal {
    @Override
    public void sound() {
        System.out.println("Cat sound");
    }
}

public class K_polymorphism {
    public static void main(String[] args) {
        K_animal myAnimal = new K_animal();
        K_animal myDog = new K_dog(); // 업캐스팅
        K_animal myCat = new K_cat(); // 업캐스팅

        myAnimal.sound();
        myDog.sound();
        myCat.sound();

        // 다운캐스팅 - 실질적인 인스턴스는 animal -> dog(x)
//        K_dog myDog2 = (K_dog) myAnimal;
        K_dog myDog2 = (K_dog) myDog; // 실질적인 인스턴스가 dog이므로 다운캐스팅 가능
        myDog2.fetch();
    }
}

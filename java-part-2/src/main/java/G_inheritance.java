public class G_inheritance {
    public static void main(String[] args) {
        G_dog dog = new G_dog();
        dog.kind = "korean dog";
        dog.eat();
        dog.walk();
        dog.bark(); // 확장

        G_bird bird = new G_bird();
        bird.kind = "american bird";
        bird.eat();
        bird.walk();
        bird.fly();
    }
}

class TV {
    String Color;
    boolean power;
    int volume;
    int channel;

    public void power() {
        power = !power;
        System.out.println("TV 전원을 " + (power ? "켰다" : "껐다"));
    }

    public void volumeUp() {
        volume++;
        System.out.println("볼륨을 올렸다.");
    }

    public void volumeDown() {
        volume--;
        System.out.println("볼륨을 내렸다.");
    }

    public void channelUp() {
        channel++;
        System.out.println("채널을 올렸다.");
    }

    public void channelDown() {
        channel--;
        System.out.println("채널을 내렸다.");
    }
}

public class A_object_class {

    public static void main(String[] args) {
        TV tv = new TV();

        // 초기화 -> 멤버변수 접근
        tv.channel = 7;
        tv.Color = "black";
        tv.power = false;
        tv.volume = 8;

        tv.power();
        tv.volumeUp();
        tv.volumeDown();
        tv.channelUp();
        tv.channelDown();
    }
}

public abstract class L_drink {

    protected String name;
    protected int price;

    public L_drink(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public abstract void dispense();
}

class G_circle extends G_shape {
    G_point center; //원의 원점좌표
    int r; // 반지름

    public G_circle() {
        this(new G_point(0, 0), 100);
    }

    public G_circle(G_point center, int r) {
        this.center = center;
        this.r = r;
        setColor("red");
    }

    @Override
    public void draw() {
        System.out.println("Center is (" + center.getX() + ", " + center.getY() + "), r is " + r + ", color is " + getColor());
    }
}

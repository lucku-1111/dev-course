public class G_draw_shape {
    public static void main(String[] args) {
        G_point[] p = new G_point[3];
        p[0] = new G_point(100, 100);
        p[1] = new G_point(140, 50);
        p[2] = new G_point(200, 100);

        G_triangle t = new G_triangle(p);
        G_circle c = new G_circle(new G_point(150, 150), 50);

        t.draw();
        c.draw();
    }
}
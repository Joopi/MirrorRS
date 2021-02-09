package types.shapes;

import java.awt.*;

public class ExtRectangle extends Rectangle implements ShapeRandom {

    public ExtRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ExtRectangle(Point point, int width, int height) {
        super(point.x, point.y, width, height);
    }

    public Point middle() {
        return new Point(x + width / 2, y + height / 2);
    }

    public Point uniformRandom() {
        if (x < 0 || y < 0 || width <= 0 || height <= 0)
            return null;

        return new Point(x + (int) (Math.random() * width), y + (int) (Math.random() * height));
    }

}

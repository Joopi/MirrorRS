package types.shapes;

import utils.Gauss;
import utils.Geometry;

import java.awt.*;

public class ExtRectangle extends Rectangle {

    public static int BRUTE_ATTEMPTS = 100;

    public ExtRectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Point middle() {
        return new Point(x + width / 2, y + height / 2);
    }

    public Point random() {
        assert (x >= 0 && y >= 0 && width > 0 && height > 0);

        return new Point(x + (int) (Math.random() * width), y + (int) (Math.random() * height));
    }

    public Point gaussian(Point peak) {
        assert (x >= 0 && y >= 0 && width > 0 && height > 0) && contains(peak);

        for (int iterations = 0; iterations < BRUTE_ATTEMPTS; iterations++) {
            Point result = new Point((int) Gauss.random(peak.x, width / 4.0D), (int) Gauss.random(peak.y, height / 4.0D));
            if (contains(result))
                return result;
        }
        //resort to an evenly distributed point.
        //make sure this warning is not repeated too often, or else your interactions are evenly distributed which is undesirable.
        System.out.println("WARNING: had to resort an evenly distributed point in ExtRectangle");
        return random();
    }


    public Point gaussianMagnet(Point magnet, double force) {
        assert (x >= 0 && y >= 0 && width > 0 && height > 0);

        Point middle = new Point(x + width / 2, y + height / 2);

        //offset towards magnet.
        if (!contains(magnet)) {
            Point intersect = intersection(magnet, middle);
            middle.translate((int) ((intersect.x - middle.x) * force), (int) ((intersect.y - middle.y) * force));
        } else {
            middle.translate((int) ((magnet.x - middle.x) * force), (int) ((magnet.y - middle.y) * force));
        }

        return gaussian(middle);
    }

    public Point intersection(Point from, Point midPoint) {
        Point intersect;
        int[][] corners = {{x + width, y}, {x, y}, {x, y + height}, {x + width, y + height}, {x + width, y}};
        for (int i = 1; i < corners.length; i++) {
            intersect = Geometry.linesIntersect(corners[i - 1][0], corners[i - 1][1], corners[i][0], corners[i][1], from.x, from.y, midPoint.x, midPoint.y);
            if (intersect != null)
                return intersect;
        }

        System.out.println("WARNING: found no intersection, resorting to mid point in ExtRectangle");
        return midPoint;
    }
}

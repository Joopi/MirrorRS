package types.shapes;

import api.input.Mouse;
import utils.Gauss;
import utils.Geometry;

import java.awt.*;

public interface ShapeRandom extends Shape {

    public Point uniformRandom();

    default Point gravityMouse() {
        return gravityMagnet(Mouse.getPosition(), 0.3);
    }

    /*
        Assumes that peak point is inside shape.
        Could generate bad results otherwise
     */
    default Point gravityPeak(Point peak) {
        Rectangle rect = getBounds();
        if (rect.x < 0 || rect.y < 0 || rect.width <= 0 || rect.height <= 0 || !contains(peak))
            return null;

        for (int iterations = 0; iterations < Gauss.MAX_TRIES; iterations++) {
            Point result = new Point((int) Gauss.random(peak.x, rect.width / 4.0D), (int) Gauss.random(peak.y, rect.height / 4.0D));
            if (contains(result))
                return result;
        }
        //resort to an evenly distributed point.
        //make sure this warning is not repeated too often, or else your interactions are evenly distributed which is undesirable.
        System.out.println("WARNING: had to resort an evenly distributed point");
        return uniformRandom();
    }

    /*
        Inner point should be a point inside this shape. For instance center point.
        Outer point can be either inside shape or outside.
     */
    default Point gravityFromInner(Point inner, Point magner, double force) {
        Rectangle rect = getBounds();
        if (rect.x < 0 || rect.y < 0 || rect.width <= 0 || rect.height <= 0 || !contains(inner))
            return null;

        //offset towards magnet.
        if (!contains(magner)) {
            Point intersect = Geometry.intersection(rect, magner, inner);
            inner.translate((int) ((intersect.x - inner.x) * force), (int) ((intersect.y - inner.y) * force));
        } else {
            inner.translate((int) ((magner.x - inner.x) * force), (int) ((magner.y - inner.y) * force));
        }

        return gravityPeak(inner);
    }

    /*
        Generates a gravity gaussian point from center of this shape towards magnet with force(%).
     */
    default Point gravityMagnet(Point magnet, double force) {
        Rectangle rect = getBounds();
        if (rect.x < 0 || rect.y < 0 || rect.width <= 0 || rect.height <= 0)
            return null;

        Point center = new Point(rect.x + rect.width / 2, rect.y + rect.height / 2);

        //offset towards magnet.
        if (!contains(magnet)) {
            Point intersect = Geometry.intersection(rect, magnet, center);
            center.translate((int) ((intersect.x - center.x) * force), (int) ((intersect.y - center.y) * force));
        } else {
            center.translate((int) ((magnet.x - center.x) * force), (int) ((magnet.y - center.y) * force));
        }

        return gravityPeak(center);
    }
}

package utils;

import java.awt.*;

public class Geometry {

    public static int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.hypot(x2 - x1, y2 - y1);
    }

    public static Point randomPointTriangle(int Ax, int Ay, int Bx, int By, int Cx, int Cy) {
        double r1 = Math.random();
        double r2 = Math.random();

        double sq1 = Math.sqrt(r1);

        int x = (int) ((1 - sq1) * Ax + (sq1 * (1 - r2)) * Bx + (sq1 * r2) * Cx);
        int y = (int) ((1 - sq1) * Ay + (sq1 * (1 - r2)) * By + (sq1 * r2) * Cy);

        return new Point(x, y);
    }

    public static Point randomPointTriangle(Point a, Point b, Point c) {
        return randomPointTriangle(a.x, a.y, b.x, b.y, c.x, c.y);
    }

    public static Point linesIntersect(int Ax, int Ay, int Bx, int By, int Cx, int Cy, int Dx, int Dy) {
        final int deltaABx = Bx - Ax,
                deltaABy = By - Ay,
                deltaCDx = Dx - Cx,
                deltaCDy = Dy - Cy;

        double det = (-deltaCDx * deltaABy + deltaABx * deltaCDy);
        if (det != 0) {
            double s = (-deltaABy * (Ax - Cx) + deltaABx * (Ay - Cy)) / det;
            double t = (deltaCDx * (Ay - Cy) - deltaCDy * (Ax - Cx)) / det;
            if ((s >= 0) && (s <= 1) && (t >= 0) && (t <= 1))
                return new Point((int) Math.round(Ax + (t * deltaABx)), (int) Math.round(Ay + (t * deltaABy)));
        }
        return null;
    }

    public static Point intersection(Rectangle bounds, Point from, Point midPoint) {
        Point intersect;
        int[][] corners = {{bounds.x + bounds.width, bounds.y}, {bounds.x, bounds.y}, {bounds.x, bounds.y + bounds.height}, {bounds.x + bounds.width, bounds.y + bounds.height}, {bounds.x + bounds.width, bounds.y}};
        for (int i = 1; i < corners.length; i++) {
            intersect = Geometry.linesIntersect(corners[i - 1][0], corners[i - 1][1], corners[i][0], corners[i][1], from.x, from.y, midPoint.x, midPoint.y);
            if (intersect != null)
                return intersect;
        }

        System.out.println("WARNING: found no intersection, resorting to mid point");
        return midPoint;
    }
}

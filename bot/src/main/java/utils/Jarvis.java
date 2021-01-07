package utils;

import types.shapes.ConvexHull;

public class Jarvis {

    public static ConvexHull convexHull(int[] xs, int[] ys) {
        int length = xs.length;
        assert length > 4;

        int left = findLeftMost(xs, ys, length);
        int current = left;
        int next, c;
        ConvexHull convex = new ConvexHull(12);

        for (c = 1; ; c++) {
            convex.addPoint(xs[current], ys[current]);
            next = 0;

            for (int i = 1; i < length; i++) {
                long cp = crossProduct(xs[current], ys[current], xs[i], ys[i], xs[next], ys[next]);
                if (cp > 0 || (cp == 0 && square(xs[current] - xs[i]) + square(ys[current] - ys[i]) > square(xs[current] - xs[next]) + square(ys[current] - ys[next])))
                    next = i;
            }

            if (next == left)
                break;

            current = next;
        }

        convex.setLength(c);
        return convex;
    }

    private static int square(int x) {
        return x * x;
    }


    private static int findLeftMost(int[] xs, int[] ys, int length) {
        int idx = 0;
        int x = xs[idx];
        int y = ys[idx];

        for (int i = 1; i < length; i++) {
            int ix = xs[i];
            if (ix < x || ix == x && ys[i] < y) {
                idx = i;
                x = xs[idx];
                y = ys[idx];
            }
        }

        return idx;
    }

    private static long crossProduct(int px, int py, int qx, int qy, int rx, int ry) {
        long val = (long) (qy - py) * (rx - qx)
                - (long) (qx - px) * (ry - qy);
        return val;
    }

}

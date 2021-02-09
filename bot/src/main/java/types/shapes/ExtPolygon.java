package types.shapes;

import utils.Geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExtPolygon extends Polygon implements ShapeRandom {

    public ExtPolygon(int[] xs, int[] ys, int n) {
        super(xs, ys, n);
    }

    //Allows us to pre-allocate a reasonable number of indices for the Polygon
    //We can then resize it down to its real size.
    public ExtPolygon(int initialSize) {
        xpoints = new int[initialSize];
        ypoints = new int[initialSize];
        npoints = 0;
    }

    public void setLength(int n) {
        xpoints = Arrays.copyOf(xpoints, n);
        ypoints = Arrays.copyOf(ypoints, n);
        npoints = n;
        if (bounds != null)
            calculateBounds(xpoints, ypoints, n);
    }

    void calculateBounds(int[] xpoints, int[] ypoints, int npoints) {
        int boundsMinX = Integer.MAX_VALUE;
        int boundsMinY = Integer.MAX_VALUE;
        int boundsMaxX = Integer.MIN_VALUE;
        int boundsMaxY = Integer.MIN_VALUE;

        for (int i = 0; i < npoints; i++) {
            int x = xpoints[i];
            boundsMinX = Math.min(boundsMinX, x);
            boundsMaxX = Math.max(boundsMaxX, x);
            int y = ypoints[i];
            boundsMinY = Math.min(boundsMinY, y);
            boundsMaxY = Math.max(boundsMaxY, y);
        }
        bounds = new Rectangle(boundsMinX, boundsMinY,
                boundsMaxX - boundsMinX,
                boundsMaxY - boundsMinY);
    }

    public ExtRectangle getBounds() {
        Rectangle rect = super.getBounds();
        return new ExtRectangle(rect.x, rect.y, rect.width, rect.height);
    }

    public boolean valid() {
        return (npoints > 0);
    }

    public Point mean() {
        int x = Math.round(Arrays.stream(xpoints).sum() / npoints);
        int y = Math.round(Arrays.stream(ypoints).sum() / npoints);
        return new Point(x, y);
    }

    public Point uniformRandom() {
        if (valid()) {
            int triangles = npoints - 2;
            List<Integer> areas = new ArrayList<>(triangles);
            int totalArea = 0;

            /*
                 Polygons are comprised of N triangles.
                 We weigh each individual triangle by its area
                 This will result in an even distribution.
             */
            for (int i = 2; i < npoints; i++) {
                totalArea += (xpoints[0] * (ypoints[i] - ypoints[i - 1]) + xpoints[i - 1] * (ypoints[0] - ypoints[i]) + xpoints[i] * (ypoints[i - 1] - ypoints[0])) / 2;
                areas.add(totalArea);
            }

            double cutoff = (totalArea * Math.random());
            for (int i = 0; i < triangles; i++) {
                if (areas.get(i) >= cutoff) { //areas goes from low to high values.
                    return Geometry.randomPointTriangle(xpoints[0], ypoints[0], xpoints[i + 1], ypoints[i + 1], xpoints[i + 2], ypoints[i + 2]);
                }
            }
        }

        return null;
    }
}

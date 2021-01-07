package internals;

import api.geometry.Projection;
import types.shapes.ConvexHull;
import types.shapes.ExtPolygon;
import types.tile.LocalTile;
import utils.Jarvis;

import static utils.Reflection.*;

public class RSModel extends RSInternal {

    public static final String CLASS_NAME = "Model";

    public RSModel(Object ref) {
        super(ref);
    }

    public ConvexHull convexHull(int rotate, int scaleWidth, int scaleHeight, LocalTile localTile, int tileHeight) {
        ExtPolygon polygon = Projection.pointsToCanvas(verticesX(), verticesZ(), verticesY(), verticesCount(), rotate, scaleWidth, scaleHeight, localTile, tileHeight);
        return polygon.valid() ? Jarvis.convexHull(polygon.xpoints, polygon.ypoints) : null;
    }

    public ConvexHull convexHull(int rotate, int scaleWidth, int scaleHeight, LocalTile localTile) {
        return convexHull(rotate, scaleWidth, scaleHeight, localTile, Projection.getTileHeight(localTile, RSClient.plane()));
    }

    public ConvexHull convexHull(int rotate, LocalTile localTile) {
        return convexHull(rotate, 128, 128, localTile);
    }

    public int indicesCount() {
        return getInt(CLASS_NAME, "indicesCount", ref);
    }

    public int verticesCount() {
        return getInt(CLASS_NAME, "verticesCount", ref);
    }

    public int[] verticesX() {
        return (int[]) getRef(CLASS_NAME, "verticesX", ref);
    }

    public int[] verticesY() {
        return (int[]) getRef(CLASS_NAME, "verticesY", ref);
    }

    public int[] verticesZ() {
        return (int[]) getRef(CLASS_NAME, "verticesZ", ref);
    }

    public int[] indicesX() {
        return (int[]) getRef(CLASS_NAME, "indices1", ref);
    }

    public int[] indicesY() {
        return (int[]) getRef(CLASS_NAME, "indices2", ref);
    }

    public int[] indicesZ() {
        return (int[]) getRef(CLASS_NAME, "indices3", ref);
    }

    public int centerX() {
        return getInt(CLASS_NAME, "xMid", ref);
    }

    public int centerY() {
        return getInt(CLASS_NAME, "yMid", ref);
    }

    public int centerZ() {
        return getInt(CLASS_NAME, "zMid", ref);
    }

    public int midOffsetX() {
        return getInt(CLASS_NAME, "xMidOffset", ref);
    }

    public int midOffsetY() {
        return getInt(CLASS_NAME, "yMidOffset", ref);
    }

    public int midOffsetZ() {
        return getInt(CLASS_NAME, "zMidOffset", ref);
    }

    public int radius() {
        return getInt(CLASS_NAME, "boundsType", ref);
    }

    public boolean isSingleTile() {
        return getBoolean(CLASS_NAME, "isSingleTile", ref);
    }

}

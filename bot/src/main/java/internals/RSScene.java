package internals;

import types.tile.SceneTile;

import static utils.Reflection.getRefArray;

public class RSScene extends RSInternal {

    public static final int SCENE_SIZE = 104;
    public static final String CLASS_NAME = "Scene";

    public RSScene(Object ref) {
        super(ref);
    }

    public Object[] tilesXRefArray(int plane) {
        return (Object[]) getRefArray(CLASS_NAME, "tiles", ref)[plane];
    }

    //plane,x,y order
    public RSTile[][] tiles(int lowX, int lowY, int width, int height, int plane) {

        int highX = lowX + width;
        int highY = lowY + height;

        if (lowX < 0 || lowY < 0 || highX > SCENE_SIZE || highY > SCENE_SIZE)
            throw new IndexOutOfBoundsException("improper scene boundaries");

        RSTile[][] result = new RSTile[width][height];

        Object[] tilesX = tilesXRefArray(plane);
        for (int x = lowX; x < highX; x++) {
            Object[] tilesY = (Object[]) tilesX[x];
            for (int y = lowY; y < highY; y++)
                result[x - lowX][y - lowY] = new RSTile(tilesY[y]);
        }

        return result;
    }

    public RSTile[][] tiles(int plane) {
        return tiles(0, 0, SCENE_SIZE, SCENE_SIZE, plane);
    }

    public RSTile[][] tiles(SceneTile sceneTile, int xRadius, int yRadius, int plane) {
        return tiles(plane, sceneTile.x - xRadius, sceneTile.y - yRadius, xRadius * 2, yRadius * 2);
    }

    public RSTile[][] tiles(SceneTile sceneTile, int radius, int plane) {
        return tiles(sceneTile, radius, radius, plane);
    }

    public RSTile tile(SceneTile sceneTile, int plane) {
        if (sceneTile.x < 0 || sceneTile.y < 0 || sceneTile.x >= SCENE_SIZE || sceneTile.y >= SCENE_SIZE)
            return null;

        Object[] tilesY = (Object[]) tilesXRefArray(plane)[sceneTile.x];
        return new RSTile(tilesY[sceneTile.y]);
    }
}

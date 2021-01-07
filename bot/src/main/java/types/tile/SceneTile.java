package types.tile;

import api.geometry.Projection;

import java.awt.*;

public class SceneTile extends Point {

    public SceneTile(int x, int y) {
        super(x, y);
    }

    public GlobalTile toGlobal() {
        return new GlobalTile(this);
    }

    //return center of tile in local coordinate.
    public LocalTile toLocal() {
        return new LocalTile(x * Projection.TILE_SIZE + Projection.HALF_TILE_SIZE, y * Projection.TILE_SIZE + Projection.HALF_TILE_SIZE);
    }

    public boolean matches(SceneTile other) {
        return (x == other.x && y == other.y);
    }
}

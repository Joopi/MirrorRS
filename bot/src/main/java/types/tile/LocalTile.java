package types.tile;

import api.geometry.Projection;

import java.awt.*;

public class LocalTile extends Point {

    public LocalTile(int x, int y) {
        super(x, y);
    }

    public SceneTile toScene() {
        return new SceneTile(x / 128, y / 128);
    }

    public GlobalTile toGlobal() {
        return toScene().toGlobal();
    }

    //matches is more strict/granular than onSameTile.
    public boolean matches(LocalTile other) {
        return (x == other.x && y == other.y);
    }

    public boolean onSameTile(LocalTile other) {
        int deltaX = Math.abs(other.x - x) >> Projection.LOCAL_COORD_BITS;
        int deltaY = Math.abs(other.y - y) >> Projection.LOCAL_COORD_BITS;
        return (deltaX == 0 && deltaY == 0);
    }
}

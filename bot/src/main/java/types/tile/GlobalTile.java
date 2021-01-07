package types.tile;

import api.geometry.Projection;
import internals.RSClient;

import java.awt.*;

public class GlobalTile extends Point {

    public GlobalTile(int baseX, int baseY) {
        super(baseX, baseY);
    }

    public GlobalTile(int baseX, int baseY, SceneTile sceneTile) {
        super(baseX + sceneTile.x, baseX + sceneTile.y);
    }

    public GlobalTile(SceneTile sceneTile) {
        super(RSClient.baseX() + sceneTile.x, RSClient.baseY() + sceneTile.y);
    }

    public LocalTile toLocal() {
        return toLocal(RSClient.baseX(), RSClient.baseY());
    }

    public LocalTile toLocal(int baseX, int baseY) {
        return new LocalTile((x - baseX) * Projection.TILE_SIZE + Projection.HALF_TILE_SIZE, (y - baseY) * Projection.TILE_SIZE + Projection.HALF_TILE_SIZE);
    }

    public SceneTile toScene() {
        return toScene(RSClient.baseX(), RSClient.baseY());
    }

    public SceneTile toScene(int baseX, int baseY) {
        return new SceneTile(x - baseX, y - baseY);
    }

    public boolean matches(GlobalTile other) {
        return (x == other.x && y == other.y);
    }
}

package api;

import internals.RSClient;
import internals.RSTile;
import types.tile.SceneTile;

public class Scene {

    public static RSTile getTile(SceneTile sceneTile) {
        return RSClient.scene().tile(sceneTile, RSClient.plane());
    }
}



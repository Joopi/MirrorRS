package internals;

import api.geometry.Projection;
import types.shapes.ConvexHull;
import types.tile.LocalTile;

import static utils.Reflection.getRef;

public class RSNPC extends RSActor {

    public static final String CLASS_NAME = "Npc";
    private int index;
    private RSNPCInfo cachedInfo;

    public RSNPC(Object ref, int index) {
        super(ref);
        this.index = index;
        cachedInfo = null;
    }

    public RSNPCInfo info() {
        if (cachedInfo == null || cachedInfo.ref == null) {
            Object infoRef = getRef(CLASS_NAME, "type", this.ref);
            cachedInfo = infoRef != null ? (new RSNPCInfo(infoRef)).transform() : null;
        }

        return cachedInfo;
    }

    public ConvexHull convexHull() {
        RSNPCInfo info = info();

        if (info != null) {
            RSModel model = info.model();
            if (model != null) {
                LocalTile localTile = localTile();

                int size = info.modelTileSize();
                if (size > 0)
                    size = size * 64 - 64;

                return model.convexHull(rotation(), info.scaleWidth(), info.scaleHeight(), localTile, Projection.getTileHeight(new LocalTile(localTile.x + size, localTile.y + size), RSClient.plane()));
            }
        }

        return null;
    }

    public String name() {
        RSNPCInfo info = info();
        return info != null ? info.name() : "";
    }

    public int index() {
        return index;
    }
}

package internals;

import types.shapes.ConvexHull;
import types.tile.LocalTile;

import java.util.Arrays;
import java.util.stream.Stream;

import static utils.Reflection.*;

public class RSPlayer extends RSActor {

    public static final String CLASS_NAME = "Player";

    private RSPlayerInfo info;

    public RSPlayer(Object ref) {
        super(ref);
        info = null;
    }

    public RSPlayerInfo info() {
        if (info == null || info.ref == null)
            info = new RSPlayerInfo(getRef(CLASS_NAME, "appearance", ref));

        return info;
    }

    public ConvexHull convexHull() {
        LocalTile tile = localTile();
        RSPlayerInfo info = info();
        if (info != null) {
            RSModel model = info.model();
            if (model != null)
                return model.convexHull(rotation(), tile);

        }

        return null;
    }

    public String name() {
        Object nameRef = getRef(CLASS_NAME, "username", ref);
        return nameRef != null ? (new RSUsernameProvider(nameRef)).name() : "";
    }

    public Stream<String> actions() {
        return Arrays.stream(getRefArray(CLASS_NAME, "actions", ref))
                .map(action -> action != null ? (String) action : "");
    }

    public RSModel model() {
        return info().model();
    }

    public boolean isAnimating() {
        return getBoolean(CLASS_NAME, "isUnanimated", ref);
    }

    public int plane() {
        return getInt(CLASS_NAME, "plane", ref);
    }

    public int tileHeight() {
        return getInt(CLASS_NAME, "tileHeight", ref);
    }

    public int combatLevel() {
        return getInt(CLASS_NAME, "combatLevel", ref);
    }

    public int headPrayer() {
        return getInt(CLASS_NAME, "headIconPrayer", ref);
    }

    public int pkSkull() {
        return getInt(CLASS_NAME, "headIconPk", ref);
    }

    public int index() {
        return getInt(CLASS_NAME, "index", ref);
    }

    public boolean isHidden() {
        return getBoolean(CLASS_NAME, "isHidden", ref);
    }

    public boolean isFriend() {
        return getBoolean(CLASS_NAME, "isFriend", ref);
    }
}

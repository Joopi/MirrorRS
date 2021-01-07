package internals;

import types.tile.SceneTile;

import static utils.Reflection.*;

public class RSClient {

    public static final String CLASS_NAME = "Client";

    public static int loginState() {
        return getInt(CLASS_NAME, "loginState", null);
    }

    public static int gameState() {
        return getInt(CLASS_NAME, "gameState", null);
    }

    public static int gameCycle() {
        return getInt(CLASS_NAME, "cycle", null);
    }

    public static int NPCCount() {
        return getInt(CLASS_NAME, "npcCount", null);
    }

    public static Object[] NPCRefArray() {
        return getRefArray(CLASS_NAME, "npcs", null);
    }

    public static int[] NPCIndices() {
        return getIntArray(CLASS_NAME, "npcIndices", null);
    }

    public static RSCache NPCInfoCache() {
        return new RSCache(getRef(CLASS_NAME, "NPCType_cached", null));
    }

    public static RSCache NPCModelCache() {
        return new RSCache(getRef(CLASS_NAME, "NPCType_cachedModels", null));
    }

    public static RSCache objInfoCache() {
        return new RSCache(getRef(CLASS_NAME, "LocType_cached", null));
    }

    public static RSCache objModelCache() {
        return new RSCache(getRef(CLASS_NAME, "LocType_cachedModels", null));
    }

    public static RSCache sequenceInfoCache() {
        return new RSCache(getRef(CLASS_NAME, "SeqType_cached", null));
    }

    public static Object[] playersRefArray() {
        return getRefArray(CLASS_NAME, "players", null);
    }

    public static int playerCount() {
        return getInt(CLASS_NAME, "Players_count", null);
    }

    public static int[] playersIndices() {
        return getIntArray(CLASS_NAME, "Players_indices", null);
    }

    public static int[] playersOrientations() {
        return getIntArray(CLASS_NAME, "Players_orientations", null);
    }

    public static RSPlayer localPlayer() {
        return new RSPlayer(getRef(CLASS_NAME, "localPlayer", null));
    }

    public static int localPlayerIndex() {
        return getInt(CLASS_NAME, "localPlayerIndex", null);
    }

    public static RSCache hitmarkInfoCache() {
        return new RSCache(getRef(CLASS_NAME, "HitmarkType_cached", null));
    }

    public static int cameraPitch() {
        return getInt(CLASS_NAME, "cameraPitch", null);
    }

    public static int cameraYaw() {
        return getInt(CLASS_NAME, "cameraYaw", null);
    }

    public static int cameraX() {
        return getInt(CLASS_NAME, "cameraX", null);
    }

    public static int cameraY() {
        return getInt(CLASS_NAME, "cameraZ", null);
    }

    public static int cameraZ() {
        return getInt(CLASS_NAME, "cameraY", null);
    }

    public static int zoom() {
        return getInt(CLASS_NAME, "getZoomLevel", null);
    }

    public static int viewportZoom() {
        return getInt(CLASS_NAME, "viewportZoom", null);
    }

    public static int viewportX() {
        return getInt(CLASS_NAME, "viewportOffsetX", null);
    }

    public static int viewportY() {
        return getInt(CLASS_NAME, "viewportOffsetY", null);
    }

    public static int viewportWidth() {
        return getInt(CLASS_NAME, "viewportWidth", null);
    }

    public static int viewportHeight() {
        return getInt(CLASS_NAME, "viewportHeight", null);
    }

    public static int[][][] tileHeights() {
        return (int[][][]) getRef(CLASS_NAME, "Tiles_heights", null);
    }

    public static byte[][][] tileSettings() {
        return (byte[][][]) getRef(CLASS_NAME, "Tiles_renderFlags", null);
    }

    public static int plane() {
        return getInt(CLASS_NAME, "plane", null);
    }

    public static int baseX() {
        return getInt(CLASS_NAME, "baseX", null);
    }

    public static int baseY() {
        return getInt(CLASS_NAME, "baseY", null);
    }

    public static int destinationX() {
        return getInt(CLASS_NAME, "destinationX", null);
    }

    public static int destinationY() {
        return getInt(CLASS_NAME, "destinationY", null);
    }

    public static SceneTile destination() {
        return new SceneTile(destinationX(), destinationY());
    }

    public static RSScene scene() {
        return new RSScene(getRef(CLASS_NAME, "scene", null));
    }

    public static RSNodeHashTable itemContainers() {
        return new RSNodeHashTable(getRef(RSClient.CLASS_NAME, "itemContainers", null));
    }

    public static RSItemContainer itemContainer(int ID) {
        return new RSItemContainer(itemContainers().get(ID).ref);
    }

    public static Object[] componentsRefArray() {
        return getRefArray(CLASS_NAME, "interfaceComponents", null);
    }

    public static RSNodeHashTable componentParents() {
        return new RSNodeHashTable(getRef(CLASS_NAME, "interfaceParents", null));
    }

    public static int[] rootComponentXs() {
        return getIntArray(RSClient.CLASS_NAME, "rootComponentXs", null);
    }

    public static int[] rootComponentYs() {
        return getIntArray(RSClient.CLASS_NAME, "rootComponentYs", null);
    }

    public static RSRasterProvider rasterProvider() {
        return new RSRasterProvider(getRef(CLASS_NAME, "rasterProvider", null));
    }

}

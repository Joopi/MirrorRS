package internals;

import api.*;
import api.Interact;
import types.shapes.ConvexHull;
import types.tile.GlobalTile;
import types.tile.LocalTile;
import types.tile.SceneTile;
import utils.Reflection;

import java.util.List;
import java.util.stream.Stream;

import static utils.Reflection.*;

public abstract class RSActor extends RSInternal {

    public static final String CLASS_NAME = "Actor";
    private static final int INDEX_CUTOFF = Short.MAX_VALUE + 1;

    public RSActor(Object ref) {
        super(ref);
    }

    public RSHealthbarInfo healthbarInfo() {
        Object cacheRef = getRef(CLASS_NAME, "headbars", ref);
        if (cacheRef != null) {

            RSCombatInfoCache combatInfoCache = new RSCombatInfoCache(cacheRef);
            RSNode head = combatInfoCache.head();

            if (head != null) {
                RSNode next = head.next();

                //object next to head will be replaced by an instance of RSCombatInfo when the health bar appears.
                if (next != null && Reflection.instanceOf(next.ref, RSCombatInfo.CLASS_NAME)) {

                    RSCombatInfo combatInfo = new RSCombatInfo(next.ref);
                    combatInfoCache = combatInfo.updates();

                    head = combatInfoCache.head(); //then we need to get the head and next node...
                    if (head != null) {

                        next = head.next();
                        if (next != null && Reflection.instanceOf(next.ref, RSHealthbarInfo.CLASS_NAME)) {

                            return new RSHealthbarInfo(next.ref);
                        }
                    }

                }
            }
        }

        return null;
    }

    public int health() {
        RSHealthbarInfo healthbarInfo = healthbarInfo();
        return (healthbarInfo != null) ? healthbarInfo.health() : -1;
    }

    public int healthRatio() {
        RSHealthbarInfo healthbarInfo = healthbarInfo();
        return (healthbarInfo != null) ? healthbarInfo.healthRatio() : -1;

    }

    public Stream<RSActor> targetingActors(List<RSActor> actors) {
        int idx = index();
        return actors.stream()
                .filter(actor -> actor.targetIndex() == idx);
    }

    public RSActor targetActor() {
        int targetIdx = targetIndex();
        if (targetIdx == -1)
            return null;

        return (targetIdx < INDEX_CUTOFF) ? NPCs.get(targetIdx) : Players.get(targetIdx - INDEX_CUTOFF);
    }

    public Stream<RSProjectile> incomingProjectiles() {
        int index = index();
        return Projectiles.getAll()
                .filter(projectile -> projectile.targetIndex() == index);
    }

    public boolean isAttackable(boolean checkIsTarget, boolean checkHealthbar) {
        int index = index();

        return (targetIndex() == -1)
                && !(checkIsTarget && Players.getAll().anyMatch(player -> player.targetIndex() == index))
                && !(checkHealthbar && (health() > -1));
    }

    public boolean interact(boolean leftClick) {
        String name = name();
        if (name.isEmpty())
            return false;

        return Interact.with(convexHull(), Client.mainscreen().getBounds(), () -> Menu.getUptext().contains(name), leftClick);
    }

    public int rotation() {
        return getInt(CLASS_NAME, "orientation", ref);
    }

    public int localX() {
        return getInt(CLASS_NAME, "x", ref);
    }

    public int localY() {
        return getInt(CLASS_NAME, "y", ref);
    }

    public LocalTile localTile() {
        return new LocalTile(localX(), localY());
    }

    public GlobalTile globalTile() {
        return new GlobalTile(localTile().toScene());
    }

    public SceneTile sceneTile() {
        return localTile().toScene();
    }

    public int targetIndex() {
        return getInt(CLASS_NAME, "targetIndex", ref);
    }

    public int movementID() {
        return getInt(CLASS_NAME, "movementSequence", ref);
    }

    public boolean isMoving() {
        return movementID() > 808;
    }

    public int animationID() {
        return getInt(CLASS_NAME, "sequence", ref);
    }

    public int animationDelay() {
        return getInt(CLASS_NAME, "sequenceDelay", ref);
    }

    public int animationFrame() {
        return getInt(CLASS_NAME, "sequenceFrame", ref);
    }

    public int animationFrameCycle() {
        return getInt(CLASS_NAME, "sequenceFrameCycle", ref);
    }

    public int size() {
        return getInt(CLASS_NAME, "size", ref);
    }

    public byte lastHitmarkCount() {
        return getByte(CLASS_NAME, "hitmarkCount", ref);
    }

    public int[] lastHitmarkCycles() {
        return getIntArray(CLASS_NAME, "hitmarkCycles", ref);
    }

    public int[] lastHitmarkValues() {
        return getIntArray(CLASS_NAME, "hitmarkValues", ref);
    }

    public int[] lastHitmarkTypes() {
        return getIntArray(CLASS_NAME, "hitmarkTypes", ref);
    }

    public String overheadText() {
        return getString(CLASS_NAME, "overheadText", ref);
    }

    public abstract String name();

    public abstract int index();

    public abstract ConvexHull convexHull();
}

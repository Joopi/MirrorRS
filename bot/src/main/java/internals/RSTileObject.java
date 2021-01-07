package internals;

import types.tile.LocalTile;

import static utils.Reflection.*;

public class RSTileObject extends RSInternal {

    private String className;
    private RSObjectInfo cachedInfo;

    public RSTileObject(Object ref, String className) {
        super(ref);
        this.className = className;
        cachedInfo = null;
    }

    public RSObjectInfo info() {
        if (cachedInfo == null || cachedInfo.ref == null)
            cachedInfo = RSObjectInfo.info((long) ID());

        return cachedInfo;
    }

    public RSModel model() {
        RSRenderable renderable = renderable();
        if (renderable.isModel())
            return new RSModel(renderable.ref);

        if (renderable.isAnimableNode()) {
            RSAnimableNode animableNode = new RSAnimableNode(renderable.ref);
            long ID = animableNode.rotation() + (((long) animableNode.ID()) << 10);

            RSObjectInfo info = info();
            if (info != null) {
                info.transform();
                if (info.models().length > 0)
                    ID += (animableNode.flags() << 3);

                return info.model(ID);
            }
        }

        return null;
    }

    public RSRenderable renderable() {
        Object entityRef = getRef(className, "entity", ref);
        return entityRef != null ? new RSRenderable(entityRef) : null;
    }

    public int realAnimationID() {
        RSObjectInfo info = info();
        if (info != null) {

            RSRenderable entity = renderable();
            if (entity != null && entity.isAnimableNode()) {

                RSAnimableNode animableNode = new RSAnimableNode(entity.ref);

                RSSequenceInfo sequenceInfo = animableNode.sequenceInfo();
                if (sequenceInfo != null) {
                    return (int) sequenceInfo.key();
                }
                return info.defaultAnimationID();
            }
        }
        return -1;
    }

    public boolean isAnimating() {
        RSObjectInfo info = info();
        return realAnimationID() != info.defaultAnimationID();
    }

    public int ID() {
        return (int) (getLong(className, "tag", ref) >>> 17 & 0xFFFFFFFFL);
    }

    public int localX() {
        return getInt(className, "x", ref);
    }

    public int localY() {
        return getInt(className, "y", ref);
    }

    public LocalTile localTile() {
        return new LocalTile(localX(), localY());
    }

    public int rotation() {
        return getInt(className, "orientation", ref);
    }

    public int flags() {
        return getInt(className, "flags", ref);
    }

}

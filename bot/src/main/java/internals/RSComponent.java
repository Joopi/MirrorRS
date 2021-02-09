package internals;

import types.shapes.ExtRectangle;
import utils.Text;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static utils.Reflection.*;

public class RSComponent extends RSNode {

    public static final String CLASS_NAME = "Component";

    private static boolean parentCaching = false;
    private static Map<Object, RSComponent> childParentCache = new HashMap<>();

    public RSComponent(Object ref) {
        super(ref);
    }

    public static void enableParentCaching(boolean enable) {
        parentCaching = enable;
    }

    public static RSComponent get(int container, int parent) {
        Object[] containers = RSClient.componentsRefArray();
        if (container < containers.length && containers[container] != null) {
            Object[] parents = (Object[]) containers[container];
            if (parent < parents.length && parents[parent] != null)
                return new RSComponent(parents[parent]);
        }

        return null;
    }

    public static RSComponent get(int container, int parent, int child) {
        RSComponent component = get(container, parent);
        return component != null ? component.child(child) : null;
    }

    public static RSComponent parent(int ID) {
        return get(ID >> 16, ID & 0xFFFF);
    }

    public int parentID() {
        int parentId = getInt(CLASS_NAME, "parentId", ref);
        if (parentId > 0)
            return parentId;

        int id = ID();

        if (id > 0) {
            //To check if this is loaded into cache
            RSNodeHashTable nodeHashTable = RSClient.componentParents();
            if (nodeHashTable.contains(id))
                return id;

            int groupId = id >> 16;

            RSNode[] nodes = nodeHashTable.buckets();
            RSComponentParent component = new RSComponentParent(this.ref);
            for (RSNode sentinel : nodes) {
                for (RSNode node = sentinel.next(); !node.ref.equals(sentinel.ref); node = node.next()) {
                    component.ref = node.ref;
                    if (component.groupId() == groupId)
                        return (int) node.key();
                }
            }
        }


        return -1;
    }

    public RSComponent parent() {

        RSComponent parent;

        if (parentCaching) {
            parent = childParentCache.get(this.ref);
            if (parent == null || parent.ref == null) {
                int parentID = parentID();
                parent = parentID != -1 ? parent(parentID) : null;
                childParentCache.put(this.ref, parent);
            }
        } else {
            int parentID = parentID();
            parent = parentID != -1 ? parent(parentID) : null;
        }

        return parent;
    }

    public Object[] childrenRefArray() {
        return getRefArray(CLASS_NAME, "children", ref);
    }

    public Stream<RSComponent> children() {
        Object[] refs = childrenRefArray();
        return Arrays.stream(refs).map(o -> o != null ? new RSComponent(o) : null);
    }

    public RSComponent child(int index) {
        Object[] refs = childrenRefArray();
        return (index < refs.length && refs[index] != null) ? new RSComponent(refs[index]) : null;
    }

    public Point position() {
        Point result = relativePosition();

        for (RSComponent component = this.parent(); component != null; component = component.parent()) {
            Point relativePos = component.relativePosition();
            Point scrollPosition = component.scrollPosition();
            result.translate(relativePos.x - scrollPosition.x, relativePos.y - scrollPosition.y);
        }

        return result;
    }

    public ExtRectangle bounds() {
        Point pos = position();
        return new ExtRectangle(pos.x, pos.y, width(), height());
    }

    public int width() {
        return getInt(CLASS_NAME, "width", ref);
    }

    public int height() {
        return getInt(CLASS_NAME, "height", ref);
    }

    public Point relativePosition() {
        return new Point(getInt(CLASS_NAME, "x", ref), getInt(CLASS_NAME, "y", ref));
    }

    public Point scrollPosition() {
        return new Point(getInt(CLASS_NAME, "scrollX", ref), getInt(CLASS_NAME, "scrollY", ref));
    }

    protected int rootIndex() {
        return getInt(CLASS_NAME, "rootIndex", ref);
    }

    public int childIndex() {
        return getInt(CLASS_NAME, "childIndex", ref);
    }

    public int ID() {
        return getInt(CLASS_NAME, "id", ref);
    }

    public int itemID() {
        return getInt(CLASS_NAME, "itemId", ref);
    }

    public int[] inventoryIDs() {
        return getIntArray(CLASS_NAME, "itemIds", ref);
    }

    public int itemQuantity() {
        return getInt(CLASS_NAME, "itemQuantity", ref);
    }

    public Stream<String> itemActions() {
        return Arrays.stream(getRefArray(CLASS_NAME, "itemActions", ref))
                .map(action -> action != null ? (String) action : "");
    }

    public String itemName() { return Text.stripHTML(getString(CLASS_NAME, "opbase", ref)); }

    public boolean isHidden() {
        return getBoolean(CLASS_NAME, "isHidden", ref);
    }

    public String buttonText() {
        return getString(CLASS_NAME, "buttonText", ref);
    }

    public String targetVerb() { return getString(CLASS_NAME, "targetVerb", ref); }

    public String text() {
        return getString(CLASS_NAME, "text", ref);
    }

    public String text2() {
        return getString(CLASS_NAME, "text2", ref);
    }

    public String name() {
        return getString(CLASS_NAME, "spellName", ref);
    }
}

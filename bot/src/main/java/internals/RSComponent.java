package internals;

import types.shapes.ExtRectangle;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static utils.Reflection.*;

public class RSComponent extends RSInternal {

    public static final String CLASS_NAME = "Component";
    private int index;

    public RSComponent(Object ref, int index) {
        super(ref);
        this.index = index;
    }

    public static RSComponent get(int container, int parent) {
        Object[] containers = RSClient.componentsRefArray();
        if (container < containers.length && containers[container] != null) {
            Object[] parents = (Object[]) containers[container];
            if (parent < parents.length && parents[parent] != null)
                return new RSComponent(parents[parent], parent);
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
        }
        return -1;
    }

    public RSComponent parent() {
        int parentID = parentID();
        return parentID != -1 ? parent(parentID) : null;
    }

    public Object[] childrenRefArray() {
        return getRefArray(CLASS_NAME, "children", ref);
    }

    public Stream<RSComponent> children() {
        Object[] refs = childrenRefArray();
        return IntStream.range(0, refs.length)
                .mapToObj(i -> refs[i] != null ? new RSComponent(refs[i], i) : null);
    }

    public RSComponent child(int index) {
        Object[] refs = childrenRefArray();
        return (index < refs.length && refs[index] != null) ? new RSComponent(refs[index], index) : null;
    }

    public Point position() {
        Point result = relativePosition();

        for (RSComponent p = this.parent(); p != null; p = p.parent()) {
            Point relativePos = p.relativePosition();
            Point scrollPosition = p.scrollPosition();
            result.translate(relativePos.x + scrollPosition.x, relativePos.y + scrollPosition.y);
        }

        int rootIndex = rootIndex();
        if (rootIndex > -1)
            result.translate(RSClient.rootComponentXs()[rootIndex], RSClient.rootComponentYs()[rootIndex]);

        return result;
    }

    public ExtRectangle bounds() {
        Point pos = position();
        return new ExtRectangle(pos.x, pos.y, getInt(CLASS_NAME, "width", ref), getInt(CLASS_NAME, "height", ref));
    }

    public Stream<String> actions() {
        return Arrays.stream(getRefArray(CLASS_NAME, "itemActions", ref))
                .map(action -> action != null ? (String) action : "");
    }

    protected Point relativePosition() {
        return new Point(getInt(CLASS_NAME, "x", ref), getInt(CLASS_NAME, "y", ref));
    }

    protected Point scrollPosition() {
        return new Point(getInt(CLASS_NAME, "scrollX", ref), getInt(CLASS_NAME, "scrollY", ref));
    }

    protected int rootIndex() {
        return getInt(CLASS_NAME, "rootIndex", ref);
    }

    public int index() {
        return index;
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

    public Stream<String> itemActioons() {
        Object[] actionsRef = getRefArray(CLASS_NAME, "itemActions", ref);
        return Arrays.stream(actionsRef)
                .map(action -> action != null ? (String) action : "");
    }

    public boolean isHidden() {
        return getBoolean(CLASS_NAME, "isHidden", ref);
    }

    public String buttonText() {
        return getString(CLASS_NAME, "buttonText", ref);
    }

    public String text() {
        return getString(CLASS_NAME, "text", ref);
    }

    public String name() {
        return getString(CLASS_NAME, "spellName", ref);
    }
}

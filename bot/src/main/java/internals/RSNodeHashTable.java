package internals;

import static utils.Reflection.*;

public class RSNodeHashTable extends RSInternal {

    public static final String DEFAULT_CLASS_NAME = "NodeHashTable";

    private String className;

    public RSNodeHashTable(Object ref) {
        super(ref);
        className = DEFAULT_CLASS_NAME;
    }

    public RSNodeHashTable(Object ref, String className) {
        super(ref);
        this.className = className;
    }

    public RSNode[] buckets() {
        Object[] objects = (Object[]) getRef(className, "buckets", ref);
        RSNode[] result = new RSNode[objects.length];
        for (int i = 0; i < objects.length; i++) {

            if (objects[i] == null) {
                result[i] = null;
            } else {
                result[i] = new RSNode(objects[i]);
            }
        }
        return result;
    }

    public RSNode bucket(int idx) {
        Object[] objects = getRefArray(className, "buckets", ref);
        if (idx < 0 || idx >= objects.length || objects[idx] == null)
            return null;

        return new RSNode(objects[idx]);
    }

    public RSNode current() {
        return new RSNode(getRef(className, "current", ref));
    }

    public int index() {
        return getInt(className, "index", ref);
    }

    public int size() {
        return getInt(className, "size", ref);
    }

    public RSNode get(long key) {
        RSNode head = bucket((int) (key & ((long) (size() - 1))));
        RSNode currentNode = head.next();
        long headKey = head.key();
        long currentKey = currentNode.key();

        while (currentKey != headKey) {
            if (currentKey == key) {
                return currentNode;
            }
            currentNode = currentNode.next();
            currentKey = currentNode.key();
        }

        return null;
    }

    public boolean contains(long key) {
        return get(key) != null;
    }


    public void debug() {
        RSNode[] buckets = buckets();
        for (int i = 0; i < buckets.length; i++) {
            long currentKey;
            RSNode currentNode = buckets[i].previous();
            long headKey = buckets[i].key();
            currentKey = currentNode.key();

            while (currentKey != headKey) {
                System.out.println(i + " " + currentKey);
                currentNode = currentNode.previous();
                currentKey = currentNode.key();
            }
        }
    }
}

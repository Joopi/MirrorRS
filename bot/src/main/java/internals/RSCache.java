package internals;

import static utils.Reflection.getRef;

public class RSCache extends RSInternal {

    public static final String CLASS_NAME = "EvictingDualNodeHashTable";

    public RSCache(Object ref) {
        super(ref);
    }

    public RSIterableHashTable hashtable() {
        Object tableRef = getRef(CLASS_NAME, "hashTable", ref);
        return tableRef != null ? new RSIterableHashTable(tableRef) : null;
    }

    public RSNode get(long key) {
        RSIterableHashTable hashTable = hashtable();
        return hashTable != null ? hashTable.get(key) : null;
    }
}

package internals;

public class RSIterableHashTable extends RSNodeHashTable {

    public static final String CLASS_NAME = "IterableNodeHashTable";

    //Runescape's NodeHashTable & IterableNodeHashTable classes are functionally the same for our purposes.
    public RSIterableHashTable(Object ref) {
        super(ref, CLASS_NAME);
    }
}

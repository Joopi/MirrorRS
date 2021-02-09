package hooks;

import java.lang.reflect.Field;

public class FieldMultiplier {
    private Field field;
    private long multiplier;

    public FieldMultiplier(Field field, long multiplier) {
        this.field = field;
        this.multiplier = multiplier;
    }

    public Field getField() {
        return field;
    }

    public long getMultiplier() {
        return multiplier;
    }
}

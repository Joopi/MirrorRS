package api;

import internals.RSComponent;

public class Minimap {
    public static int orbHealth() {
        return Integer.parseInt(componentRoot().text());
    }

    public static RSComponent componentRoot() {
        return RSComponent.get(160, 5);
    }
}

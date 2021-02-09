package types.components;

import types.shapes.ExtRectangle;

public class InventoryItem {
    private int id;
    private int slot;
    private int quantity;
    private ExtRectangle bounds;

    public InventoryItem(int id, int slot, int quantity, ExtRectangle bounds) {
        this.id = id;
        this.slot = slot;
        this.quantity = quantity;
        this.bounds = bounds;
    }


    public int quantity() {
        return quantity;
    }

    public int slot() {
        return slot;
    }

    public int id() {
        return id;
    }

    public ExtRectangle bounds() {
        return bounds;
    }
}

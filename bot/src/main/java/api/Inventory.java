package api;

import internals.RSClient;
import internals.RSComponent;
import internals.RSItemContainer;
import types.components.InventoryItem;
import types.shapes.ExtRectangle;
import utils.IntArray;

import java.awt.*;
import java.util.stream.Stream;

public class Inventory {

    private static final int ITEM_SLOT_SIZE = 32;
    private static final int ITEM_X_PADDING = 10;
    private static final int ITEM_Y_PADDING = 4;

    public static int inventoryCount() {
        return itemContainer().IDs().length;
    }

    public static ExtRectangle slotBounds(int componentContainerWidth, Point componentContainerPosition, int slot) {
        if (componentContainerWidth <= 0 || componentContainerPosition == null)
            throw new NullPointerException("Inventory not initialized");

        int row = slot / componentContainerWidth;
        int col = slot % componentContainerWidth;

        int x = componentContainerPosition.x + ((ITEM_SLOT_SIZE + ITEM_X_PADDING) * col);
        int y = componentContainerPosition.y + ((ITEM_SLOT_SIZE + ITEM_Y_PADDING) * row);

        return new ExtRectangle(x, y, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
    }

    public static ExtRectangle slotBounds(int slot) {
        RSComponent componentContainer = componentContainer();
        return slotBounds( componentContainer.width(), componentContainer.position(), slot);
    }

    public static Stream<InventoryItem> items() {
        Stream.Builder<InventoryItem> builder = Stream.builder();

        RSComponent componentContainer = componentContainer();
        int columns = componentContainer.width();
        Point position = componentContainer.position();

        RSItemContainer itemContainer = itemContainer();
        int[] ids = itemContainer.IDs();
        int[] quantities = itemContainer.quantities();

        for (int i = 0; i < ids.length; i++) {
            if (ids[i] > 0) {
                builder.accept(new InventoryItem(ids[i], i, quantities[i], slotBounds(columns, position, i)));
            }
        }

        return builder.build();
    }

    public static InventoryItem item(int id) {
        RSItemContainer itemContainer = itemContainer();
        int idx = IntArray.indexOf(itemContainer.IDs(), id);

        if (idx == -1)
            return null;

        return new InventoryItem(id, idx, itemContainer.quantities()[idx], slotBounds(idx));
    }

    public static InventoryItem slotItem(int slot) {
        if (slot < 0 || slot > 27)
            throw new IndexOutOfBoundsException("Invalid slot: " + slot);

        RSItemContainer itemContainer = itemContainer();
        int[] ids = itemContainer.IDs();

        if (slot >= ids.length)
            return null;

        return new InventoryItem(ids[slot], slot, itemContainer.quantities()[slot], slotBounds(slot));
    }

    public static Stream<InventoryItem> items(int... ids) {
        RSComponent componentContainer = componentContainer();
        int columns = componentContainer.width();
        Point position = componentContainer.position();

        RSItemContainer itemContainer = itemContainer();
        int[] invIds = itemContainer.IDs();
        int[] quantities = itemContainer.quantities();

        Stream.Builder<InventoryItem> builder = Stream.builder();

        for (int id : ids) {
            int idx = IntArray.indexOf(invIds, id);

            if (idx > -1)
                builder.accept(new InventoryItem(id, idx, quantities[idx], slotBounds(columns, position, idx)));
        }

        return builder.build();
    }

    public static Stream<InventoryItem> slotItems(int... slots) {
        RSComponent componentContainer = componentContainer();
        int columns = componentContainer.width();
        Point position = componentContainer.position();

        RSItemContainer itemContainer = itemContainer();
        int[] invIds = itemContainer.IDs();
        int[] quantities = itemContainer.quantities();

        Stream.Builder<InventoryItem> builder = Stream.builder();

        for (int slot : slots) {
            if (slot < 0 || slot > 27)
                throw new IndexOutOfBoundsException("Invalid slot: " + slot);

            if (slot < invIds.length)
                builder.accept(new InventoryItem(invIds[slot], slot, quantities[slot], slotBounds(columns, position, slot)));
        }

        return builder.build();
    }

    public static RSComponent componentContainer() {
        return RSComponent.get(149, 0);
    }

    public static RSItemContainer itemContainer() {
        return RSClient.itemContainer(93);
    }
}

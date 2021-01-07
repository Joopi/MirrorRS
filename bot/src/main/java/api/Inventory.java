package api;

import internals.RSClient;
import internals.RSComponent;
import internals.RSItemContainer;

import java.util.stream.Stream;

public class Inventory {

    public static int[] itemIDs() {
        return itemContainer().IDs();
    }

    public static Stream<RSComponent> items() {
        return componentContainer()
                .child(0)
                .children();
    }

    public static Stream<RSComponent> items(int id) {
        int[] ids = itemContainer().IDs();
        Object[] refs = componentContainer().childrenRefArray();

        Stream.Builder<RSComponent> builder = Stream.builder();
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id && refs[i] != null)
                builder.accept(new RSComponent(refs[i], i));
        }

        return builder.build();
    }

    public static RSComponent slotItem(int slot) {
        return componentContainer()
                .child(0)
                .child(slot);
    }

    public static RSComponent componentContainer() {
        return RSComponent.get(149, 0);
    }

    public static RSItemContainer itemContainer() {
        return RSClient.itemContainer(93);
    }
}

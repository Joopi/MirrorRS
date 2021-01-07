package api;

import internals.RSClient;
import internals.RSComponent;
import internals.RSItemContainer;
import utils.IntArray;

import java.util.List;
import java.util.stream.Stream;

public class Bank {

    public static RSComponent item(int ID) {
        RSComponent bankContainer = componentContainer();
        int idx = IntArray.indexOf(itemContainer().IDs(), ID);
        return idx > -1 ? bankContainer.child(idx) : null;
    }

    public static Stream<RSComponent> items(List<Integer> IDs) {
        int[] itemIDs = itemContainer().IDs();

        //Slight optimization to get raw objects and create wrappers lazily
        Object[] refs = componentContainer().childrenRefArray();

        return IDs.stream()
                .map(i -> {
                    int idx = IntArray.indexOf(itemIDs, i);
                    return (idx > -1 && refs[idx] != null) ? new RSComponent(refs[idx], idx) : null;
                });
    }

    public static Stream<RSComponent> items() {
        return componentContainer()
                .children()
                .limit(RSClient.itemContainer(95).IDs().length);
    }

    public static RSItemContainer itemContainer() {
        return RSClient.itemContainer(95);
    }

    public static RSComponent componentContainer() {
        return RSComponent.get(12, 12);
    }
}

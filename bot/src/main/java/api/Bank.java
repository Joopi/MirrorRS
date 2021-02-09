package api;

import api.input.Mouse;
import api.util.Sleep;
import internals.RSClient;
import internals.RSComponent;
import internals.RSItemContainer;
import types.shapes.ExtRectangle;
import utils.Gauss;
import utils.IntArray;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bank {
    private static int VAR_CURRENT_BANK_TAB = 4150;
    private static int VAR_BANK_TAB_COUNT = 4170;

    public static int currentTab() {
        return Varbit.get(VAR_CURRENT_BANK_TAB);
    }

    public static int tabItemCount(int tab) {

        if (tab == 0) {
            int count = totalItemCount();
            for (int i = 9; i > 0; i--) {
                count -= tabItemCount(i);
            }
            return count;
        }

        return Varbit.get(VAR_BANK_TAB_COUNT + tab);
    }

    public static int tabCount() {
        for (int i = 9; i > 0; i--) {
            if (tabItemCount(i) > 0)
                return i+1;
        }

        return 1;
    }

    public static int totalItemCount() {
        return itemContainer().IDs().length;
    }

    private static List<Integer> tabsItemIndices() {
        List<Integer> result = new LinkedList<>();
        int totalCount = 0;

        for (int i = 1; i < 10; i++) {
            int slotCount = tabItemCount(i);
            if (slotCount == 0)
                break;

            totalCount += slotCount;
            result.add(totalCount);

        }
        return result;
    }

    private static int getItemTab(List<Integer> itemIndices, RSComponent item) {
        int childIndex = item.childIndex();
        for (int i = 0; i < itemIndices.size(); i++) {
            if (itemIndices.get(i) > childIndex)
                return i+1;
        }

        return 0;
    }

    public static boolean withdraw(RSComponent item) {
        if (item != null) {
            int tab = getItemTab(tabsItemIndices(), item);

            if (openTab(tab) && scrollToItem(item) && Interact.componentItem(item, true)) {
                Sleep.range(80, 340, 140, 20);
                return true;
            }
        }

        return false;
    }

    public static boolean withdraw(int id) {
        return withdraw(item(id));
    }

    public static boolean withdrawIndex(int idx) {
        return withdraw(itemIndex(idx));
    }

    public static boolean withdraw(String name) {
        return withdraw(item(name));
    }

    public static boolean withdraw(List<RSComponent> items) {
        List<Integer> itemIndices = tabsItemIndices();

        for (RSComponent item : items) {
            if (!openTab(getItemTab(itemIndices, item)) || !scrollToItem(item) || !Interact.componentItem(item, true))
                return false;

            Sleep.range(80, 340, 140, 20);
        }

        return true;
    }

    public static boolean withdrawIndices(int... indices) {
        return withdraw(itemIndices(indices).collect(Collectors.toList()));
    }

    public static boolean withdraw(int... ids) {
        return withdraw(items(ids).collect(Collectors.toList()));
    }

    public static boolean withdraw(String... names) {
        return withdraw(items(names).collect(Collectors.toList()));
    }

    public static boolean openTab(int tab) {
        if (currentTab() == tab)
            return true;

        if (tabCount() <= tab)
            return false;

        RSComponent tabContainer = tabContainer();
        if (tabContainer != null) {

            ExtRectangle bounds = tabContainer.child(10 + tab).bounds();

            for (int i = 0; i < 3; i++) {
                Mouse.click(bounds.gravityMouse(), MouseEvent.BUTTON1);
                if (Sleep.until(() -> currentTab() == tab, 40, (int) Gauss.constrain(1700, 2500, 2000, 50))) {
                    Sleep.range(40, 400, 140, 20);
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean scrollToItem(RSComponent item) {
        if (item != null) {

            Point position = item.position();
            ExtRectangle bounds = item.parent().bounds();
            Point scrollPosition = bounds.gravityMagnet(position, 0.5);

            for (int i = 0; i < 10; i++) {
                if (bounds.contains(position))
                    return true;

                Mouse.scroll(scrollPosition, (int) Gauss.constrain(1, 7, 4, 2), bounds.y < position.y);
                Sleep.range(120, 470, 215, 24);
                position = item.position();

                if (Math.random() < 0.3)
                    scrollPosition = bounds.gravityFromInner(scrollPosition, position, 0.3);
            }
        }

        return false;
    }

    public static RSComponent item(int ID) {
        RSComponent bankContainer = componentContainer();
        int idx = IntArray.indexOf(itemContainer().IDs(), ID);
        return idx > -1 ? bankContainer.child(idx) : null;
    }

    public static RSComponent item(String name) {
        return items()
                .filter(item -> item.itemName().matches(name))
                .findAny()
                .orElse(null);
    }

    public static RSComponent itemIndex(int index) {
        return componentContainer()
                .child(index);
    }

    public static Stream<RSComponent> items() {
        return componentContainer()
                .children();
    }

    public static Stream<RSComponent> items(int id) {
        int[] ids = itemContainer().IDs();
        Object[] refs = componentContainer().childrenRefArray();

        Stream.Builder<RSComponent> builder = Stream.builder();
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == id && refs[i] != null)
                builder.accept(new RSComponent(refs[i]));
        }

        return builder.build();
    }

    public static Stream<RSComponent> items(int... ids) {
        int[] itemIDs = itemContainer().IDs();

        //Slight optimization to get raw objects and create wrappers lazily
        Object[] refs = componentContainer().childrenRefArray();

        Stream.Builder<RSComponent> builder = Stream.builder();
        for (int id = 0; id < ids.length; id++) {
            int idx = IntArray.indexOf(itemIDs, id);
            builder.add((idx > -1 && refs[idx] != null) ? new RSComponent(refs[idx]) : null);
        }

        return builder.build();
    }

    public static Stream<RSComponent> items(String... names) {
        Predicate<String> contains = s -> {
            for (String name : names) {
                if (name.matches(s))
                    return true;
            }
            return false;
        };

        return items()
                .filter(item -> contains.test(item.itemName()))
                .sorted(Comparator.comparingInt(item -> {
                    String itemName = item.itemName();
                    for (int i = 0; i < names.length; i++) {
                        if (names[i].matches(itemName)) {
                            return i;
                        }
                    }
                    return Integer.MAX_VALUE;
                }));
    }

    public static Stream<RSComponent> itemIndices(int... indices) {
        //Slight optimization to get raw objects and create wrappers lazily
        Object[] refs = componentContainer().childrenRefArray();

        return Arrays.stream(indices)
                .mapToObj(idx -> (idx > -1 && refs[idx] != null) ? new RSComponent(refs[idx]) : null);
    }

    public static RSItemContainer itemContainer() {
        return RSClient.itemContainer(95);
    }

    public static RSComponent componentContainer() {
        return RSComponent.get(12, 12);
    }

    public static RSComponent tabContainer() {
        return RSComponent.get(12, 10);
    }
}

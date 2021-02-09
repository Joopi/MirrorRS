package api;

import api.input.Mouse;
import api.util.Sleep;
import internals.RSComponent;
import types.shapes.ShapeRandom;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BooleanSupplier;

public class Interact {

    public static boolean point(Point clickPoint, BooleanSupplier clickCondition, boolean leftClick) {
        Mouse.move(clickPoint);
        if (!Sleep.until(clickCondition, 20, 140))
            return false;
            
        return leftClick ? Mouse.click(clickPoint, MouseEvent.BUTTON1) : Mouse.click(clickPoint, MouseEvent.BUTTON3) && Sleep.until(Menu::isOpen, 20, 250);
    }

    public static boolean shape(ShapeRandom shape, Rectangle boundaries, BooleanSupplier clickCondition, boolean leftClick) {
        Point clickPoint = shape.gravityMouse();
        if (!boundaries.contains(clickPoint))
            return false;

        return point(clickPoint, clickCondition, leftClick);
    }

    public static boolean shape(ShapeRandom shape, BooleanSupplier clickCondition, boolean leftClick) {
        return shape(shape, shape.getBounds(), clickCondition, leftClick);
    }

    public static boolean component(RSComponent component, boolean leftClick) {
        BooleanSupplier uptextMatches = () -> Menu.isUptext(component.name());
        return shape(component.bounds(), uptextMatches, leftClick);

    }

    public static boolean componentItem(RSComponent componentItem, boolean leftClick) {
        BooleanSupplier uptextMatches = () ->  Menu.isUptext(componentItem.itemName());
        return shape(componentItem.bounds(), uptextMatches, leftClick);
    }
}
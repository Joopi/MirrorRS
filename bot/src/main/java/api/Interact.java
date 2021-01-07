package api;

import api.input.Mouse;
import api.util.Sleep;
import types.shapes.ConvexHull;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.BooleanSupplier;

public class Interact {

    public static boolean with(ConvexHull hull, Rectangle boundaries, BooleanSupplier uptextMatches, boolean leftClick) {
        if (!hull.valid())
            return false;

        Point clickPoint = hull.gaussianMagnet(Mouse.getPosition(), 0.3);

        if (!boundaries.contains(clickPoint) || !Sleep.until(uptextMatches, 20, 140))
            return false;

        if (leftClick) {
            return Mouse.clicked(clickPoint, true);
        } else {
            return Mouse.click(clickPoint, MouseEvent.BUTTON3) && Sleep.until(Menu::isOpen, 20, 250);
        }
    }

    public static boolean with(ConvexHull hull, BooleanSupplier uptextMatches, boolean leftClick) {
        return with(hull, hull.getBounds(), uptextMatches, leftClick);
    }

}
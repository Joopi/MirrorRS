package api;

import api.geometry.Projection;
import api.input.Mouse;
import api.util.Sleep;
import internals.RSClient;
import types.shapes.ExtPolygon;
import types.tile.LocalTile;
import types.tile.SceneTile;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Tiles {

    public static ExtPolygon polygon(LocalTile localTile) {
        return Projection.tileToCanvas(localTile);
    }

    public static boolean hoverTile(LocalTile localTile) {
        ExtPolygon polygon = polygon(localTile);
        if (polygon.valid()) {

            Point point = polygon.gravityMagnet(Mouse.getPosition(), 0.3);
            if (point.x != -1) {
                Mouse.move(point);
                return true;
            }
        }
        return false;
    }

    public static boolean walkTo(LocalTile localTile, boolean leftClick) {
        if (hoverTile(localTile)) {
            Sleep.range(20, 90, 35, 8);
            if (leftClick && Menu.isUptext("Walk here")) {
                Mouse.click(MouseEvent.BUTTON1);
                return true;
            } else {
                Mouse.click(MouseEvent.BUTTON3);
                if (Sleep.until(Menu::isOpen, 30, 250)) {
                    Sleep.range(140, 230, 160, 12);
                    return Menu.chooseOption("Walk here");
                }
            }
        }
        return false;
    }

    public static boolean isDestination(SceneTile tile) {
        return RSClient.destination().matches(tile);
    }

    public static boolean waitDestination(SceneTile tile, int interval, long max) {
        return Sleep.until(() -> isDestination(tile), interval, max);
    }
}

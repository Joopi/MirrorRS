package api.input;

import api.Client;
import api.draw.MouseDraw;
import api.events.OnBeforeClick;
import api.events.OnMove;
import api.util.Sleep;
import internals.RSClient;
import utils.Gauss;
import utils.Geometry;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static utils.Reflection.getInt;

public class Mouse {
    public static final int CLICK_YELLOW = 1;
    public static final int CLICK_RED = 2;
    public static int CONNECT = 6;
    public static int MAX_MOVE_INSTRUCTIONS = 1000;
    public static OnMove onMove;
    public static OnBeforeClick onBeforeClick;
    private static EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
    private static int cachedX, cachedY;
    private static boolean leftDown, rightDown, scrollDown;

    public static boolean isHeld(int clickType) {
        switch (clickType) {
            case MouseEvent.BUTTON1:
                return (leftDown);
            case MouseEvent.BUTTON2:
                return (rightDown);
            case MouseEvent.BUTTON3:
                return (scrollDown);
        }
        return false;
    }

    private static void updateButtons(int clickType, boolean newValue) {
        switch (clickType) {
            case (MouseEvent.BUTTON1):
                leftDown = newValue;
                break;
            case (MouseEvent.BUTTON2):
                rightDown = newValue;
                break;
            case (MouseEvent.BUTTON3):
                scrollDown = newValue;
                break;
        }
    }

    private static boolean isDragging() {
        return (leftDown || rightDown || scrollDown);
    }

    public static void exitCanvas(int x, int y) {
        assert (!isDragging());

        move(x, y);
        eventQueue.postEvent(new MouseEvent(Client.getCanvas(), MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, x, y, 0, false, 0));
    }

    public static void enterCanvas(int x, int y) {
        assert (!isDragging());

        jump(x, y);
        eventQueue.postEvent(new MouseEvent(Client.getCanvas(), MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, x, y, 0, false, 0));
    }

    public static Point getPosition() {
        return new Point(cachedX, cachedY);
    }

    public static boolean jump(int x, int y) {
        Canvas canvas = Client.getCanvas();
        if ((x == cachedX && y == cachedY) || !canvas.contains(x, y))
            return false;

        int btnMask = (leftDown ? MouseEvent.BUTTON1_DOWN_MASK : 0) | (rightDown ? (MouseEvent.BUTTON3_DOWN_MASK | MouseEvent.META_DOWN_MASK) : 0);
        eventQueue.postEvent(new MouseEvent(canvas, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), btnMask, x, y, 0, false, 0));
        cachedX = x;
        cachedY = y;
        MouseDraw.move(new Point(x, y));
        return true;
    }

    public static void move(int x, int y) {
        MouseDraw.resetTrace();
        int dist = Geometry.distance(cachedX, cachedY, x, y);
        double step = Gauss.constrain(2.5, 7, 6, 1);
        double windX = 0;
        double windY = 0;
        double stepX;
        double stepY;
        int slowdown = 40 + (int) (Math.random() * (dist / 5));
        int attemtps = 0;

        while (dist > CONNECT && attemtps++ < MAX_MOVE_INSTRUCTIONS) {
            windX = Gauss.random((windX > 0) ? windX - 0.35 : windX + 0.35, 0.7);
            windY = Gauss.random((windY > 0) ? windY - 0.35 : windY + 0.35, 0.7);

            stepX = ((double) (x - cachedX) / (double) Math.abs(y - cachedY));
            if (stepX > 2.5) {
                stepX = 2.5;
            } else if (stepX < -2.5) {
                stepX = -2.5;
            }

            stepY = ((double) (y - cachedY) / (double) Math.abs(x - cachedX));
            if (stepY > 2.5) {
                stepY = 2.5;
            } else if (stepY < -2.5) {
                stepY = -2.5;
            }

            step = Gauss.random(dist < slowdown ? Math.max(3.5, step - 0.5) : Math.min(7, step + 0.4), 0.6);
            if (step < 3)
                step = 3;

            stepX = Gauss.random(stepX * step, 0.8) + windX;
            stepY = Gauss.random(stepY * step, 0.8) + windY;

            if ((Math.abs(stepX) > 1 || Math.abs(stepY) > 1)) {
                if ((onMove != null) && !onMove.call(dist))
                    return;

                jump(cachedX + (int) stepX, cachedY + (int) stepY);
                dist = Geometry.distance(cachedX, cachedY, x, y);
                Sleep.range(15, 23);
            }
        }

        if ((onMove == null || onMove.call(dist)) && (cachedX != x || cachedY != y))
            jump(x, y);
    }

    public static void move(Point point) {
        move(point.x, point.y);
    }

    public static int crossColor() {
        return getInt(RSClient.CLASS_NAME, "mouseCrossColor", null);
    }

    public static boolean click(int clickType, int sleepDuration) {
        if (onBeforeClick != null && !onBeforeClick.call(clickType))
            return false;

        updateButtons(clickType, true);
        int btnMask = (leftDown ? MouseEvent.BUTTON1_DOWN_MASK : 0) | (rightDown ? (MouseEvent.BUTTON3_DOWN_MASK | MouseEvent.META_DOWN_MASK) : 0);
        Canvas canvas = Client.getCanvas();
        Client.setFocus(true);

        eventQueue.postEvent(new MouseEvent(canvas, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), btnMask, cachedX, cachedY, 1, false, clickType));

        Sleep.duration(sleepDuration);

        long time = System.currentTimeMillis();
        eventQueue.postEvent(new MouseEvent(canvas, MouseEvent.MOUSE_RELEASED, time, btnMask, cachedX, cachedY, 1, false, clickType));
        eventQueue.postEvent(new MouseEvent(canvas, MouseEvent.MOUSE_CLICKED, time, btnMask, cachedX, cachedY, 1, false, clickType));
        updateButtons(clickType, false);
        return true;
    }

    public static boolean click(int clickType) {
        return click(clickType, (int) Gauss.constrain(50, 110, 80, 12));
    }

    public static boolean click(int x, int y, int clickType) {
        assert (!isHeld(clickType));

        move(x, y);
        Sleep.range(21, 45, 30, 5);
        return click(clickType);
    }

    public static boolean click(Point point, int clickType) {
        return click(point.x, point.y, clickType);
    }

    public static boolean clicked(int x, int y, boolean red) {
        if (click(x, y, MouseEvent.BUTTON1)) {
            int col;

            long ms = System.currentTimeMillis() + 250 + (int) (60 * Math.random());
            do {
                col = Mouse.crossColor();
                if (col == (red ? CLICK_RED : CLICK_YELLOW))
                    return true;

            } while (System.currentTimeMillis() < ms && col == 0);
        }
        return false;
    }

    public static boolean clicked(Point point, boolean red) {
        return clicked(point.x, point.y, red);
    }

    public static void scroll(Point location, int steps, boolean down) {
        Point scrollLoc = getPosition();

        if (scrollLoc.distance(location) > 0) {
            move(location);
            Sleep.range(20, 90, 40, 7);
            scrollLoc = getPosition();
        }

        Canvas canvas = Client.getCanvas();
        int btnMask = (Keyboard.isKeyHeld(KeyEvent.VK_SHIFT) ? KeyEvent.SHIFT_MASK : 0) | (Keyboard.isKeyHeld(KeyEvent.VK_ALT) ? KeyEvent.ALT_MASK : 0) | (Keyboard.isKeyHeld(KeyEvent.VK_CONTROL) ? KeyEvent.CTRL_MASK : 0);
        for (int i = 0; i < steps; i++) {
            eventQueue.postEvent(new MouseWheelEvent(canvas, MouseWheelEvent.MOUSE_WHEEL, System.currentTimeMillis(), btnMask, scrollLoc.x, scrollLoc.y, 0, false, MouseWheelEvent.WHEEL_UNIT_SCROLL, 3, down ? 1 : -1));
            Sleep.range(70, 180, 110, 21);
        }
    }
}

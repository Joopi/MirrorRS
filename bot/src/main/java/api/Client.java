package api;

import handler.RSHandler;
import internals.RSClient;
import types.shapes.ExtRectangle;

import java.awt.*;
import java.awt.event.FocusEvent;

public class Client extends RSClient {
    private static ExtRectangle mainscreen;

    public static Canvas getCanvas() {
        return (Canvas) RSHandler.applet.getComponent(0);
    }

    public static ExtRectangle mainscreen() {
        if (mainscreen == null) {
            mainscreen = new ExtRectangle(RSClient.viewportX(), RSClient.viewportY(), RSClient.viewportWidth(), RSClient.viewportHeight());
        }

        return mainscreen;
    }

    public static boolean hasFocus() {
        return getCanvas().isFocusOwner();
    }

    public static void setFocus(boolean focus) {
        System.out.println("Setting focus: " + focus);
        if (focus && !hasFocus()) {
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new FocusEvent(getCanvas(), FocusEvent.FOCUS_GAINED));
        } else if (hasFocus()) {
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new FocusEvent(getCanvas(), FocusEvent.FOCUS_LOST));
        }
    }

    public static boolean isLoggedIn() {
        return RSClient.gameState() == 30;
    }

}

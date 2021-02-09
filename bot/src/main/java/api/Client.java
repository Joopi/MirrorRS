package api;

import handler.RSHandler;
import internals.RSClient;
import sun.awt.CausedFocusEvent;
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
            mainscreen = new ExtRectangle(viewportX(), viewportY(), viewportWidth(), viewportHeight());
        }

        return mainscreen;
    }

    public static boolean hasFocus() {
        return getCanvas().isFocusOwner();
    }

    public static void setFocus(boolean focus) {
        if (focus != hasFocus()) {
            System.out.println("Setting focus: " + focus);
            CausedFocusEvent e = new CausedFocusEvent(getCanvas(), focus ? FocusEvent.FOCUS_GAINED : FocusEvent.FOCUS_LOST, focus, null, CausedFocusEvent.Cause.ACTIVATION);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(e);
        }
    }

    public static boolean isLoggedIn() {
        return gameState() == 30;
    }

}

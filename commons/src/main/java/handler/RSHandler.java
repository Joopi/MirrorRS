package handler;

import java.applet.Applet;

public class RSHandler {

    public static Applet applet;

    public static void killClient() {
        applet.destroy();
        applet = null;
    }
}

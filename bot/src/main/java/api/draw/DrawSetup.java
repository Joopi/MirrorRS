package api.draw;

import api.Client;
import sun.java2d.SunGraphics2D;
import sun.java2d.pipe.ValidatePipe;

import java.lang.reflect.Field;

public class DrawSetup {
    public static boolean init() {
        try {
            SunGraphics2D sunGraphics2D = (SunGraphics2D) Client.getCanvas().getGraphics();
            Field invalidpipe = SunGraphics2D.class.getDeclaredField("invalidpipe");
            invalidpipe.setAccessible(true);
            invalidpipe.set(sunGraphics2D, new DrawImage((ValidatePipe) invalidpipe.get(sunGraphics2D)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

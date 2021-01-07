package api.draw;

import api.events.OnDraw;
import sun.java2d.SunGraphics2D;
import sun.java2d.pipe.ValidatePipe;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.LinkedHashSet;

public class DrawImage extends ValidatePipe {

    //insertion order matters
    private static LinkedHashSet<OnDraw> callbacks = new LinkedHashSet<>();
    private ValidatePipe original;

    public DrawImage(ValidatePipe originalPipe) {
        super();
        original = originalPipe;
    }

    public static void addCallback(OnDraw event) {
        callbacks.add(event);
    }

    public static void removeCallback(OnDraw event) {
        callbacks.remove(event);
    }

    @Override
    public boolean copyImage(SunGraphics2D sg, Image img, int x, int y, Color bgColor, ImageObserver observer) {
        try {
            if (img != null && img.getGraphics() != null && img.getGraphics() instanceof SunGraphics2D) {
                SunGraphics2D g2d = (SunGraphics2D) img.getGraphics();
                for (OnDraw callback : callbacks) {
                    callback.draw(g2d);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (super.copyImage(sg, img, x, y, bgColor, observer));
    }

    /*
        We override these two methods incase the game checks for hashcodes for these fields.
     */
    @Override
    public int hashCode() {
        return original.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return original.equals(obj);
    }
}

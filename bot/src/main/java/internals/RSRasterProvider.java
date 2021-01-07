package internals;

import java.awt.*;

import static utils.Reflection.getRef;

public class RSRasterProvider extends RSAbstractRasterProvider {

    public static final String CLASS_NAME = "RasterProvider";

    public RSRasterProvider(Object ref) {
        super(ref);
    }

    public static RSRasterProvider instance() {
        return RSClient.rasterProvider();
    }

    public Image image() {
        return (Image) getRef(CLASS_NAME, "image", ref);
    }

    public Component component() {
        return (Component) getRef(CLASS_NAME, "component0", ref);
    }
}

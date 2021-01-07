import handler.RSClassLoader;
import handler.RSHandler;

import java.applet.Applet;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class GameLoader {

    private static URLClassLoaderSpy spy;

    public static boolean LaunchGame(Object appletViewerArgs) {
        try {
            Optional<Path> jar = Files.list(Paths.get("").toAbsolutePath())
                    .filter(file -> file.toString().endsWith("jagexappletviewer.jar"))
                    .findAny();
            if (jar.isPresent()) {
                spy = new URLClassLoaderSpy(new URL[]{jar.get().toUri().toURL()}, GameLoader.class.getClassLoader());

                Class classToLoad = Class.forName("jagexappletviewer", true, spy);
                Method method = classToLoad.getDeclaredMethod("main", String[].class);
                Object instance = classToLoad.getDeclaredConstructor().newInstance();
                method.invoke(instance, appletViewerArgs);

                Thread.sleep(2000);
                RSHandler.applet = getAppletInstance();

                //We need to use the applet's classLoader to reflect in-game objects.
                RSClassLoader.setup(RSHandler.applet.getClass().getClassLoader());
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static Applet getAppletInstance() {

        try {
            Class appletViewer = spy.loadClass("app.appletviewer");

            if (appletViewer != null) {
                Field field = appletViewer.getDeclaredField("j");
                if (field != null) {
                    field.setAccessible(true);
                    return (Applet) field.get(null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

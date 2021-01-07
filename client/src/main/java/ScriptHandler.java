import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ScriptHandler {

    private static Class scriptMain;
    private static Object instance;

    public static void runScript(String name) {

        String jarName = name.endsWith(".jar") ? name : name.concat(".jar"); //make sure the file name ends with .jar

        if (scriptMain != null && scriptMain.getPackage().getName().endsWith(jarName.substring(0, jarName.length() - 4))) {
            System.out.println("We are already running " + jarName + ", use -6 command instead.");
            return;
        }

        try {

            Optional<Path> jar = Files.list(Paths.get(""))
                    .filter(path -> path.toString().endsWith(jarName))
                    .findAny();

            if (jar.isPresent()) {
                URLClassLoader loader = new URLClassLoader(new URL[]{jar.get().toUri().toURL()}, ScriptHandler.class.getClassLoader());

                JarInputStream inputStream = new JarInputStream(new FileInputStream(jar.get().toFile()));
                JarEntry jarEntry = inputStream.getNextJarEntry();

                while (jarEntry != null) {
                    System.out.println(jarEntry.getName());
                    if (jarEntry.getName().endsWith(".class")) {

                        String className = jarEntry.getName().replaceAll("/", "\\.");
                        className = className.substring(0, className.length() - 6);
                        Class clazz = loader.loadClass(className);

                        if (className.endsWith("Main")) {
                            scriptMain = clazz;
                            System.out.println(Arrays.toString(clazz.getDeclaredMethods()));
                            System.out.println(Arrays.toString(clazz.getMethods()));
                        }

                    }
                    jarEntry = inputStream.getNextJarEntry();
                }

                if (scriptMain != null)
                    instance = scriptMain.getConstructor().newInstance();

                inputStream.close();
                loader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void terminateScript() {
        try {
            if (instance != null && scriptMain != null) {
                Method isRunning = scriptMain.getMethod("isRunning");
                if ((boolean) isRunning.invoke(instance)) {
                    Method stop = scriptMain.getMethod("stop");
                    stop.invoke(instance);
                    System.out.println("Terminating script");

                    Method getThread = scriptMain.getMethod("getThread");
                    Thread scriptThread = (Thread) getThread.invoke(instance);
                    scriptThread.join();
                }
                instance = null;
                scriptMain = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

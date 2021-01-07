package handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RSClassLoader {

    private static Map<String, Class> map = new ConcurrentHashMap<>();
    private static ClassLoader instance;

    public static void setup(ClassLoader classLoader) {
        instance = classLoader;
    }

    public static ClassLoader get() {
        return instance;
    }

    public static Class loadClass(String className) throws ClassNotFoundException {
        Class res = map.get(className);
        if (res == null) {
            res = get().loadClass(className);
            map.put(className, res);
        }
        return res;
    }
}

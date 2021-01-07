import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

public class URLClassLoaderSpy extends URLClassLoader {
    HashMap<String, Class> map = new HashMap<>();

    public URLClassLoaderSpy(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public synchronized Class loadClass(String name) throws ClassNotFoundException {
        Class res = map.get(name);
        if (res == null) {
            res = super.loadClass(name);
            map.put(name, res);
        }
        return res;
    }
}

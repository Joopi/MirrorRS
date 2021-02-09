package hooks;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Hooks {

    private static Map<String, Hook> hookMap;
    private static Map<String, String> classNameMap;

    public static boolean setupHooks() {
        try {
            JSONTokener tokener = new JSONTokener(new FileReader(System.getProperty("user.dir") + "/hooks.json"));
            JSONArray array = new JSONArray(tokener);

            hookMap = new HashMap<>(1 << 13);
            classNameMap = new HashMap<>(array.length());

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                String className = object.getString("class");
                classNameMap.put(className, object.getString("name"));

                JSONArray fields = object.getJSONArray("fields");
                for (int j = 0; j < fields.length(); j++) {
                    JSONObject field = fields.getJSONObject(j);

                    String keyName = toClassFieldKey(className, field.getString("field"));
                    Hook hook = new Hook(field.getString("owner"), field.getString("name"), field.optLong("decoder", 1));
                    hookMap.put(keyName, hook);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Hook getHook(String classFieldName) {
        return hookMap.get(classFieldName);
    }

    public static String getObfuscatedClassName(String className) {
        return classNameMap.get(className);
    }

    public static String toClassFieldKey(String className, String fieldName) {
        return className + fieldName;
    }
}

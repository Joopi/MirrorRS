package hooks;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class Hooks {
    private static HashMap<String, GameClass> map;

    public static boolean setupHooks() {
        try {
            JSONTokener tokener = new JSONTokener(new FileReader(System.getProperty("user.dir") + "/hooks.json"));
            JSONArray array = new JSONArray(tokener);
            map = new HashMap<>(array.length());
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                map.put(object.getString("class"), new GameClass(object));
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static GameClass get(String className) {
        return map.get(className);
    }
}

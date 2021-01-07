package hooks;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class GameClass {
    private String superClass;
    private String obfuscatedName;
    private HashMap<String, Hook> fields;

    public GameClass(JSONObject object) {
        superClass = object.getString("super");
        obfuscatedName = object.getString("name");
        JSONArray JSONFields = object.getJSONArray("fields");
        fields = new HashMap<>(JSONFields.length());
        for (int i = 0; i < JSONFields.length(); i++) {
            JSONObject field = JSONFields.optJSONObject(i);
            if (field != null) {
                //use default value 1 for multipliers, because multiplying by one is same as not multiplying at all.
                Hook hook = new Hook(field.getString("owner"), field.getString("name"), field.optLong("decoder", 1), field.optInt("access"));
                fields.put(field.getString("field"), hook);
            }
        }
    }

    public String getSuperClass() {
        return superClass;
    }

    public String getObfuscatedName() {
        return obfuscatedName;
    }

    public int getFieldsSize() {
        return fields.size();
    }

    public Hook getHook(String fieldName) {
        return fields.get(fieldName);
    }
}

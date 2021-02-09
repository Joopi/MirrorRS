package utils;

import handler.RSClassLoader;
import hooks.FieldMultiplier;
import hooks.Hook;
import hooks.Hooks;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Wrapper class for reflection
 * uses "hooks" for obfuscated class/fieldName as well as integer overflow multipliers.
 */

public class Reflection {

    private static HashMap<String, FieldMultiplier> cachedFieldMultipliers = new HashMap<String, FieldMultiplier>();
    private static HashMap<String, Field> cachedFields = new HashMap<String, Field>();


    private static Class loadClass(String obfuscatedName) {
        return RSClassLoader.loadClass(obfuscatedName);
    }

    private static Field getField(String obfuscatedClassName, String obfuscatedFieldName) {
        try {
            Class clazz = loadClass(obfuscatedClassName);
            if (clazz != null) {
                Field result = clazz.getDeclaredField(obfuscatedFieldName);
                result.setAccessible(true);
                return result;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Field not found");
    }

    //checks if object "ref" refers to the ingame class.
    //className is not obfuscated.
    public static boolean instanceOf(Object ref, String className) {
        String obfuscatedName = Hooks.getObfuscatedClassName(className);
        if (obfuscatedName != null) {
            Class clazz = loadClass(obfuscatedName);
            if (clazz != null) {
                return clazz.isInstance(ref);
            }
        }

        throw new ClassCastException("instaceOf failed");
    }

    public static FieldMultiplier getFieldMultiplier(String className, String fieldName) {
        String keyName = Hooks.toClassFieldKey(className, fieldName);
        FieldMultiplier result = cachedFieldMultipliers.get(keyName);
        if (result == null) {
            Hook hook = Hooks.getHook(keyName);
            result = new FieldMultiplier(getField(hook.getObfuscatedOwner(), hook.getObfuscatedField()), hook.getMultiplier());
            cachedFieldMultipliers.put(keyName, result);
        }
        return result;
    }

    public static Field getCachedField(String className, String fieldName) {
        String keyName = Hooks.toClassFieldKey(className, fieldName);
        Field result = cachedFields.get(keyName);
        if (result == null) {
            Hook hook = Hooks.getHook(keyName);
            result = getField(hook.getObfuscatedOwner(), hook.getObfuscatedField());
            cachedFields.put(keyName, result);
        }
        return result;
    }


    public static int getInt(String className, String fieldName, Object ref) {
        FieldMultiplier fm = getFieldMultiplier(className, fieldName);

        try {
            return (int)fm.getField().get(ref) * (int)fm.getMultiplier();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte getByte(String className, String fieldName, Object ref) {
        Field field = getCachedField(className, fieldName);

        try {
            return (byte)field.get(ref);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(String className, String fieldName, Object ref) {
        Field field = getCachedField(className, fieldName);

        try {
            Object val = field.get(ref);
            return val != null ? (String) val : "";
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getBoolean(String className, String fieldName, Object ref) {
        Field field = getCachedField(className, fieldName);

        try {
            Object val = field.get(ref);
            return (boolean) val;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getLong(String className, String fieldName, Object ref) {
        FieldMultiplier fm = getFieldMultiplier(className, fieldName);

        try {
            return (long)fm.getField().get(ref) * fm.getMultiplier();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getRef(String className, String fieldName, Object ref) {
        Field field = getCachedField(className, fieldName);

        try {
            return field.get(ref);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object[] getRefArray(String className, String fieldName, Object ref) {
        Field field = getCachedField(className, fieldName);

        try {
            Object value = field.get(ref);
            if (value == null)
                return new Object[]{};

            return (Object[])value;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] getIntArray(String className, String fieldName, Object ref) {
        Field field = getCachedField(className, fieldName);

        try {
            Object value = field.get(ref);
            if (value == null)
                return new int[]{};

            return (int[])value;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

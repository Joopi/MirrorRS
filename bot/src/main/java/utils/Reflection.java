package utils;

import handler.RSClassLoader;
import hooks.GameClass;
import hooks.Hook;
import hooks.Hooks;

import java.lang.reflect.Field;

/**
 * Wrapper class for reflection
 * uses "hooks" for obfuscated class/fieldName as well as integer overflow multipliers.
 */

public class Reflection {

    private static Hook getHook(String className, String fieldName) {
        GameClass gameClass = Hooks.get(className);
        return (gameClass != null) ? gameClass.getHook(fieldName) : null;
    }

    private static Class loadClass(String className) throws ClassNotFoundException {
        return RSClassLoader.loadClass(className);
    }

    private static Class loadClass(Hook hook) throws ClassNotFoundException {
        return (hook != null) ? RSClassLoader.loadClass(hook.getOwner()) : null;
    }

    private static Field getField(Hook hook) {
        try {
            if (hook != null) {
                Class clazz = loadClass(hook);
                if (clazz != null) {
                    Field field = clazz.getDeclaredField(hook.getName());
                    return field;
                }
            }
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    //checks if object "ref" refers to the ingame class.
    //className is not obfuscated.
    public static boolean instanceOf(Object ref, String className) {
        try {
            GameClass gameClass = Hooks.get(className);
            if (gameClass != null) {
                Class clazz = loadClass(gameClass.getObfuscatedName());
                if (clazz != null) {
                    return clazz.isInstance(ref);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Object getValue(Hook hook, Object ref) {
        Field field = getField(hook);
        if (field != null) {
            boolean accessible = field.isAccessible();

            if (!accessible)
                field.setAccessible(true);

            try {
                Object returnValue = field.get(ref);
                if (!accessible)
                    field.setAccessible(false);

                return returnValue;

            } catch (IllegalAccessException e) {
                if (!accessible) field.setAccessible(false);
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int getInt(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        Object object = getValue(hook, ref);
        if (object != null)
            return ((int) object) * (int) hook.getMultiplier();
        return -1;
    }

    public static byte getByte(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        Object object = getValue(hook, ref);
        if (object != null)
            return ((byte) object);
        return -1;
    }

    public static String getString(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        Object object = getValue(hook, ref);
        return (object != null) ? (String) object : "";
    }

    public static boolean getBoolean(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        Object object = getValue(hook, ref);
        return (object != null) && (boolean) object;
    }

    public static long getLong(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        Object object = getValue(hook, ref);
        if (object != null) {
            return ((long) object) * hook.getMultiplier();
        }
        return -1;
    }

    public static Object getRef(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        return getValue(hook, ref);
    }

    public static Object[] getRefArray(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        Object[] result = {};
        Object object = getValue(hook, ref);
        if (object != null)
            result = (Object[]) object;

        return result;
    }

    public static int[] getIntArray(String className, String fieldName, Object ref) {
        Hook hook = getHook(className, fieldName);
        int[] result = {};
        Object object = getValue(hook, ref);
        if (object != null)
            result = (int[]) object;

        return result;
    }
}

package api.input;

import api.Client;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Keyboard {

    public static final String special = "~!@#$%^&*()_+|{}:\"<>?";

    private static Map<Integer, Long> heldKeys = new HashMap<>();
    private static boolean shiftDown = false;

    private static EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();

    private static int[] typable_vk_keycode = new int[0xff];
    static {
        typable_vk_keycode[32] = 32;
        for (int c = (int)'A'; c <= (int)'Z'; c++) typable_vk_keycode[c] = c;
        for (int c = (int)'0'; c <= (int)'9'; c++) typable_vk_keycode[c] = c;
        typable_vk_keycode[186] = ';'; //  ;:
        typable_vk_keycode[187] = '='; //  =+
        typable_vk_keycode[188] = ','; // hack: ,
        typable_vk_keycode[189] = '-'; //  -_
        typable_vk_keycode[190] = '.'; //  .>
        typable_vk_keycode[191] = '/'; //  /?
        typable_vk_keycode[192] = '`'; //  `~
        typable_vk_keycode[219] = '['; //  [{
        typable_vk_keycode[220] = '\\';//  \|
        typable_vk_keycode[221] = ']'; //  ]}
        typable_vk_keycode[222] = '\'';//  '"
        typable_vk_keycode[226] = ','; // hack: <
    }

    public static synchronized boolean isKeyHeld(int keyCode) {
        return heldKeys.containsKey(keyCode);
    }

    public static synchronized void holdKey(int keyCode) {
        if (!isKeyHeld(keyCode)) {
            Client.setFocus(true);

            if (keyCode == KeyEvent.VK_SHIFT)
                shiftDown = true;

            long time = System.currentTimeMillis();
            eventQueue.postEvent(new KeyEvent(Client.getCanvas(), KeyEvent.KEY_PRESSED, time, shiftDown ? KeyEvent.SHIFT_DOWN_MASK : 0, keyCode, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD));
            if (isTypableCode(keyCode)) {
                eventQueue.postEvent(new KeyEvent(Client.getCanvas(), KeyEvent.KEY_TYPED, time, shiftDown ? KeyEvent.SHIFT_DOWN_MASK : 0, keyCode, toChar(keyCode, shiftDown), KeyEvent.KEY_LOCATION_UNKNOWN));
            }

            heldKeys.put(keyCode, time);
        }
    }

    public static synchronized void releaseKey(int keyCode) {
        if (isKeyHeld(keyCode)) {
            Client.setFocus(true);

            eventQueue.postEvent(new KeyEvent(Client.getCanvas(), KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, keyCode, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD));
            if (keyCode == KeyEvent.VK_SHIFT)
                shiftDown = false;
        }
    }

    private static char toChar(int vk, boolean shift) {
        int code = typable_vk_keycode[vk];
        final String special = "~!@#$%^&*()_+|{}:\"<>?";
        final String normal = "`1234567890-=\\[];',./";
        int index = normal.indexOf((char)code);
        if (index == -1) {
            return shift ? Character.toUpperCase((char)code) : Character.toLowerCase((char)code);
        } else {
            return shift ? special.charAt(index) : (char)code;
        }
    }

    private static boolean isTypableCode(int vk) {
        return vk < 0xff && typable_vk_keycode[vk] != 0;
    }

    private static boolean isShiftChar(char c) {
        int lexDist = c - 'A';
        return ((lexDist >= 0 && lexDist <= 25) || special.indexOf(c) != -1);
    }

    public Map<Integer, Long> getHeldKeysCopy() {
        return new HashMap<Integer, Long>(heldKeys);
    }
}

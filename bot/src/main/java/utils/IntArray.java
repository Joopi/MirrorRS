package utils;

public class IntArray {

    public static int indexOf(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value)
                return i;
        }
        return -1;
    }

    public static boolean contains(int[] array, int value) {
        return (indexOf(array, value) > -1);
    }
}

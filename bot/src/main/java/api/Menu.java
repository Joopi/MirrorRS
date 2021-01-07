package api;

import api.input.Mouse;
import api.util.Sleep;
import internals.RSClient;
import types.shapes.ExtRectangle;
import utils.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static utils.Reflection.*;

public class Menu {

    public static final int HEADER_HEIGHT = 19;
    public static final int OPTION_HEIGHT = 15;

    public static Stream<String> getOptions() {
        Object[] actions = getRefArray(RSClient.CLASS_NAME, "menuActions", null);
        Object[] targets = getRefArray(RSClient.CLASS_NAME, "menuTargetNames", null);

        return IntStream.range(0, optionsCount())
                .mapToObj(i -> (actions[i] != null ? Text.stripHTML((String) actions[i]) : "") + " " + (targets[i] != null ? Text.stripHTML((String) targets[i]) : ""));
    }

    public static String getUptext() {
        return getOptions()
                .findFirst()
                .orElse("");
    }

    public static boolean chooseOption(String option) {
        if (isOpen()) {

            List<String> options = getOptions().collect(Collectors.toList());
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i).contains(option)) {
                    ExtRectangle clickBox = getClickBox(i);
                    Point offset = clickBox.middle();
                    offset.translate(80, 12);
                    Mouse.move(clickBox.gaussianMagnet(offset, 0.4));
                    if (!isOpen())
                        return false;

                    Mouse.click(MouseEvent.BUTTON1);
                    return (Sleep.until(() -> !isOpen(), 20, 200));
                }
            }
        }
        return false;
    }

    public static ExtRectangle getClickBox(int i) {
        int x = getInt(RSClient.CLASS_NAME, "menuX", null) + 1;
        int y = getInt(RSClient.CLASS_NAME, "menuY", null) + HEADER_HEIGHT + OPTION_HEIGHT * i;
        int with = getInt(RSClient.CLASS_NAME, "menuWidth", null);

        return new ExtRectangle(x, y, with - 1, OPTION_HEIGHT);
    }

    public static boolean isOpen() {
        return getBoolean(RSClient.CLASS_NAME, "isMiniMenuOpen", null);
    }

    public static int optionsCount() {
        return getInt(RSClient.CLASS_NAME, "menuOptionsCount", null);
    }

}

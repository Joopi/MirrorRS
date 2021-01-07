package api.draw;

import api.draw.DrawImage;
import api.events.OnDraw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MouseDraw {
    public static Color CURSOR_COLOR = Color.green;
    public static Color TRACE_COLOR = new Color(0, 180, 0);
    private static List<Point> path = new ArrayList<>();
    private static Point current = new Point(0, 0);

    public static OnDraw mousePosition = g -> {
        if (current.x != -1 && current.y != -1) {
            g.setColor(CURSOR_COLOR);
            g.drawLine(current.x - 10, current.y, current.x + 10, current.y);
            g.drawLine(current.x, current.y - 10, current.x, current.y + 10);
        }
    };

    public static OnDraw traceMouse = g -> {
        g.setColor(TRACE_COLOR);

        List<Point> pathCopy = new ArrayList<>(path);
        for (int i = 1; i < pathCopy.size(); i++) {
            Point previous = pathCopy.get(i - 1);
            Point current = pathCopy.get(i);
            if (previous != null && current != null)
                g.drawLine(previous.x, previous.y, current.x, current.y);
        }
    };

    static {
        DrawImage.addCallback(mousePosition);
        DrawImage.addCallback(traceMouse);
    }

    public static void move(Point p) {
        path.add(p);
        current = p;
    }

    public static void resetTrace() {
        path.clear();
    }
}

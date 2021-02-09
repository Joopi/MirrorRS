package Main;

import api.Client;
import api.Inventory;
import api.Players;
import api.draw.DrawImage;
import api.draw.DrawSetup;
import api.events.OnDraw;
import api.util.Sleep;
import internals.RSPlayer;
import script.Script;
import types.shapes.ExtRectangle;

import java.awt.*;

public class Main extends Script {

    private RSPlayer localPlayer;

    private OnDraw drawItems = g -> {
        g.setColor(Color.CYAN);
        Inventory.items().forEach(item -> {
            ExtRectangle rectangle = item.bounds();
            g.draw(rectangle);
            g.drawString(Integer.toString(item.id()), (int) rectangle.getCenterX() - 15, (int) rectangle.getCenterY());
        });
    };

    @Override
    public boolean onStart() {
        if (!Client.isLoggedIn())
            return false;

        DrawSetup.init();
        DrawImage.addCallback(drawItems);

        localPlayer = Players.localPlayer();
        System.out.println("Player name: " + localPlayer.name());
        return true;
    }

    @Override
    public void onTerminate() {
        DrawImage.removeCallback(drawItems);
    }

    @Override
    public boolean onLoop() {
        System.out.println("We are currently on tile: " + localPlayer.globalTile());
        Sleep.duration(2000);
        return true;
    }
}
import api.Client;
import api.Players;
import api.util.Sleep;
import internals.RSPlayer;
import script.Script;

public class Main extends Script {

    private RSPlayer localPlayer;

    @Override
    public boolean onStart() {
        if (Client.isLoggedIn()) {
            System.out.println("Starting instance of script!");
            localPlayer = Players.localPlayer();
            System.out.println("Player name: " + localPlayer.name());
            return true;
        }

        return false;
    }

    @Override
    public void onTerminate() {
        System.out.println("Terminating script!");
    }

    @Override
    public boolean onLoop() {
        System.out.println("We are currently on tile: " + localPlayer.globalTile());
        Sleep.duration(2000);
        return true;
    }
}
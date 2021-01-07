# MirroredRS
A reflection client for OSRS.

## What is MirroredRS? ##
A fairly simple client that loads the jagexappletviewer and reads fields from the client using Java Reflection.
MirroredRS exposes an easy-to-use API for interacting with game entities, while also providing access to raw fields in the game.

## Features ##
* Model rendering
* Bank/Inventory interfaces
* NPCs/Players
* Scenery objects
* Drawing on canvas
* Unique mouse pathing algorithm
* Hot-swapping scripts by using a custom class loader.

...and more

## Todo ##
The project is still unfinished and fairly unpolished.
</br> Future updates include:
* Documentation
* More extensive API/Utility methods
* Lazy creation of wrapper objects
* Query API for chained object getters

## Usage ##
The Loader directory needs to contain the **jagexappletviewer** as well as **hooks.json**. You must also supply **jav_config.ws** to the loader so that the jagexappletviewer can parse arguments and download the gamepack. These things will be automated in the future.

Any script must extend the Script class in **bot/src/main/java/api/script/script**. Each script must also contain one class **Main** which must implement three methods **onStart(), onLoop(), onTerminate()**

Example script:

```java
package examplescript;

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
```

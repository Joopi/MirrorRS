import handler.RSHandler;
import hooks.Hooks;

public class Main {

    public static void main(String[] args) {
        if (Hooks.setupHooks()) {

            while (GameLoader.LaunchGame(args)) {

                //this should run until user wants to relaunch client using our Cli interface.
                while (true) {
                    String input = System.console().readLine();

                    if (input.startsWith("-q")) {
                        System.exit(0);
                    } else if (input.startsWith("-6")) {
                        break;
                    } else if (input.startsWith("-t")) {
                        ScriptHandler.terminateScript();
                    } else if (input.startsWith("-r")) {
                        System.out.println("relaunching script!");
                    } else if (input.startsWith("-s") && input.length() > 2) {
                        ScriptHandler.terminateScript();
                        ScriptHandler.runScript(input.substring(3));
                    } else if (input.startsWith("-p")) {

                    }
                }

                RSHandler.killClient();
            }
        }
    }
}

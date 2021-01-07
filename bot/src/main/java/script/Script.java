package script;

public abstract class Script implements Runnable {

    private Thread thread;
    private boolean doStop = false;

    public Script() {
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        doStop = true;
    }

    public synchronized boolean isRunning() {
        return (!doStop);
    }

    public void run() {
        if (onStart())
            while (isRunning())
                if (!onLoop())
                    stop();

        onTerminate();
    }

    public Thread getThread() {
        return thread;
    }

    //onStart triggers upon running the script.
    //Should return true if setup succeeded and we should start looping onLoop().
    public abstract boolean onStart();

    //onTerminate triggers when the script ends.
    public abstract void onTerminate();

    //onLoop is the script loop.
    //should return true as long as we wish to continue running the script.
    public abstract boolean onLoop();

}

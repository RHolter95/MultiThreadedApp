

public final class LockingThread extends Thread {

    private final String threadName;
    private final Station station;

    /**
     * Initialize a LockingThread
     *
     * @param threadName    The current thread name
     * @param station The Station that can be used to lower the counter
     */
    public LockingThread(final String threadName, final Station station) {
        if (station == null) throw new NullPointerException("Locked counter cannot be null!");
        if (threadName == null) throw new NullPointerException("Thread name cannot be null!");
        if (threadName.trim().length() == 0) throw new IllegalArgumentException("Thread name cannot be empty!");

        this.threadName = threadName;
        this.station = station;
    }

    public Station getStation() {
        return station;
    }

    @Override
    public void run() {
        while(getStation().getWorkLoad() > 0){
            station.DoWork();
        }
    }
}
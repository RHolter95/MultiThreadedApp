import java.util.concurrent.locks.ReentrantLock;

public final class Station {

    private final ReentrantLock lock;
    private int workLoad;
    private Conveyor connectedConveyorOne;
    private Conveyor connectedConveyorTwo;
    private String name;

    public Station(int workLoad, Conveyor connectedConveyorOne, Conveyor connectedConveyorTwo, String name) {
        this.workLoad = workLoad;
        this.connectedConveyorOne = connectedConveyorOne;
        this.connectedConveyorTwo = connectedConveyorTwo;
        lock = new ReentrantLock();
        this.name = name;
    }

    /*
    public Station(int startIndex, Conveyor connectedConveyorOne, Conveyor connectedConveyorTwo) {
        this.connectedConveyorOne = connectedConveyorOne;
        this.connectedConveyorTwo = connectedConveyorTwo;
        workLoad = startIndex;
        lock = new ReentrantLock();
    }

     */


    public void DoWork() {
            if (getConnectedConveyorOne() != null && getConnectedConveyorTwo() != null && getConnectedConveyorOne().isActive() == false && getConnectedConveyorTwo().isActive() == false) {
                lock.lock();
                try {
                    System.out.println(name + " In-connection set to Conveyor: " + getConnectedConveyorOne().getName());
                    System.out.println(name + " Out-connection set to Conveyor: " + getConnectedConveyorTwo().getName());
                    getConnectedConveyorOne().setActive(true);
                    getConnectedConveyorOne().setActive(true);
                    //Announce work being done
                    System.out.println(name + " Workload Set. " + name + " has " + getWorkLoad() + " package groups to work on.");
                    //Do work
                    workLoad--;
                    System.out.println(name + " successfully moves packages on " + getConnectedConveyorOne().getName());
                    System.out.println(name + " has " + getWorkLoad() + " to move ");

                    if(getWorkLoad() == 0){
                        System.out.println("* * " + name + " workload successfully completed * *");
                    }

                    //Disable conveyor belt
                    System.out.println(name + " holds lock on input conveyor: " + getConnectedConveyorOne().getName());
                    System.out.println(name + " holds lock on output conveyor: " + getConnectedConveyorTwo().getName());
                    getConnectedConveyorOne().setActive(false);
                    getConnectedConveyorOne().setActive(false);
                    //Release conveyor belt for use on dif Station
                    System.out.println(name + " unlocks conveyor " + getConnectedConveyorOne().getName());
                    System.out.println(name + " unlocks conveyor " + getConnectedConveyorTwo().getName());
                    setConnectedConveyorOne(null);
                    setConnectedConveyorTwo(null);
                } finally {
                    lock.unlock();

                    try{
                        Thread.sleep(500);
                    }
                    catch(InterruptedException e){
                        System.out.println("main thread interrupted");
                    }
                }
            }
        }

    public int getWorkLoad() {
        return workLoad;
    }

    public Conveyor getConnectedConveyorTwo() {
        return connectedConveyorTwo;
    }

    public void setConnectedConveyorTwo(Conveyor connectedConveyorTwo) {
        this.connectedConveyorTwo = connectedConveyorTwo;
    }

    public Conveyor getConnectedConveyorOne() {
        return connectedConveyorOne;
    }

    public void setConnectedConveyorOne(Conveyor connectedConveyorOne) {
        this.connectedConveyorOne = connectedConveyorOne;
    }

    public void setWorkLoad(int workLoad) {
        this.workLoad = workLoad;
    }
}
public class Conveyor {

    private Station station;
    private boolean active;
    private String name;

    public Conveyor(Station station, boolean active, String name) {
        this.station = station;
        this.active = active;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}

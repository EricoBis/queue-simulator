package entities;

public class Event implements Comparable<Event> {

    private EventType type;
    private double time;

    public Event(EventType type, double time) {
        this.type = type;
        this.time = time;
    }

    public EventType getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    @Override
    public int compareTo(Event other) {
        return Double.compare(this.time, other.time);
    }
}

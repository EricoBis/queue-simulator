import java.util.PriorityQueue;

import entities.Event;
import entities.EventType;
import utils.RandomGenerator;

public class Simulator {

    // Simulation Parameters
    private final double minArrivalTime = 1.0;
    private final double maxArrivalTime = 2.0;
    private final double minServiceTime = 3.0;
    private final double maxServiceTime = 4.0;
    private final double firstArrivalTime = 1.0;

    // Objects
    private PriorityQueue<Event> scheduler;
    private RandomGenerator randomGenerator;

    // Runtime Parameters
    private int count = 100000;
    private double globalTime = 0.0;
    
    private double[] queueTimes;

    public Simulator() {
        Queue.reset();
        
        scheduler = new PriorityQueue<>();
        randomGenerator = new RandomGenerator();
        queueTimes = new double[Queue.capacity() + 1];

        scheduler.add(new Event(EventType.IN, firstArrivalTime));
    }

    public void start() {

        while (count > 0) {
            Event event = nextEvent();

            if (event.getType() == EventType.IN) {
                checkin(event);
            } else if (event.getType() == EventType.OUT) {
                checkout(event);
            }

            count--;
        }
    }

    private Event nextEvent() {
        return scheduler.poll();
    }

    private void checkin(Event event) {
        acumulateTime(event.getTime());

        if (Queue.status() < Queue.capacity()) {
            Queue.in();

            if (Queue.status() <= Queue.servers()) {
                scheduler.add(
                        new Event(EventType.OUT,
                                globalTime + randomTimeBetween(minServiceTime, maxServiceTime)));
            }
        } else {
            Queue.loss();
        }

        scheduler.add(new Event(EventType.IN,
                globalTime + randomTimeBetween(minArrivalTime, maxArrivalTime)));
    }

    private void checkout(Event event) {
        acumulateTime(event.getTime());

        Queue.out();
        if (Queue.status() >= Queue.servers()) {
            scheduler.add(new Event(EventType.OUT,
                    globalTime + randomTimeBetween(minServiceTime, maxServiceTime)));
        }
    }

    private double randomTimeBetween(double minTime, double maxTime) {
        return (maxTime - minTime) * randomGenerator.nextRandom() + minTime;
    }

    private void acumulateTime(double eventTime) {
        double delta = eventTime - globalTime;
        globalTime = eventTime;

        queueTimes[Queue.status()] += delta;
    }

    public void displayResults() {
        System.out.printf("Total Simulation Time: %.2f u.t\n\n", globalTime);

        System.out.println("Times in each queue state:");
        for (int i = 0; i < queueTimes.length; i++) {
            System.out.format("\tState %d: %.2f u.t\n", i, queueTimes[i]);
        }
        System.out.println("");

        System.out.println("Probability of each queue state:");
        for (int i = 0; i < queueTimes.length; i++) {
            System.out.format("\tProbability of state %d: %.3f%s \n", i, queueTimes[i] / globalTime * 100, "%");
        }
        System.out.println("");

        System.out.println("Lost Clients: " + Queue.getLossCounter());
    }

}

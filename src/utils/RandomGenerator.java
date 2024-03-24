package utils;
public class RandomGenerator {

    private final int seed = 12;
    private final int a = 25;
    private final int c = 5;
    private final int M = 24560;

    private int previous = seed;

    public double nextRandom() {
        previous = ((a * previous) + c) % M;
        return (float) previous / M;
    }

}

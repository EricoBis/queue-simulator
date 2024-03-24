public class Queue {

    private static final int serversQuantity = 2;
    private static final int capacity = 4;

    private static int size = 0;
    private static int lossCounter = 0;

    public static void in() {
        size++;
    }

    public static void out() {
        size--;
    }

    public static int status() {
        return size;
    }

    public static int servers() {
        return serversQuantity;
    }

    public static int capacity() {
        return capacity;
    }

    public static void loss(){
        lossCounter++;
    }

    public static int getLossCounter(){
        return lossCounter;
    }

    public static void reset(){
        size = 0;
        lossCounter = 0;
    }

}

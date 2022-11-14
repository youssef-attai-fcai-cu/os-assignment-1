import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
//        The buffer size (i.e. the limit for the blocking queue)
        int bufferSize = 8;
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(bufferSize);

        Producer producer = new Producer(queue, 100000);
        Consumer consumer = new Consumer(queue, "outputFile.txt", producer);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

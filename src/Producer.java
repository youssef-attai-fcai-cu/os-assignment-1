import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final int N;
    private boolean done;

    public boolean isDone() {
        return this.done;
    }

    public Producer(BlockingQueue<Integer> queue, int n) {
        this.done = false;

        this.queue = queue;
        this.N = n;
    }

    @Override
    public void run() {
//        Iterate over all integers from 0 to N
        for (int i = 0; i <= this.N; i++) {
//            If the number is prime
            if (this.isPrime(i)) {
                try {
//                    Put it in the blocking queue
                    this.queue.put(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
//        Producer is done producing, Consumer can now terminate (i.e. stop waiting).
        this.done = true;
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}

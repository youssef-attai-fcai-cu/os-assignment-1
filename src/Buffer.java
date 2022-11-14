import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final Queue<Integer> queue;
    private final int bufferSize;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition bufferIsNotEmpty = lock.newCondition();
    private final Condition bufferIsNotFull = lock.newCondition();

    public Buffer(int bufferSize) {
        this.queue = new LinkedList<>();
        this.bufferSize = bufferSize;
    }

    public void put(int prime) {
        this.lock.lock();
        try {
            if (this.queue.size() == this.bufferSize) {
                this.bufferIsNotFull.await();
            }
            this.queue.add(prime);
            this.bufferIsNotEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.lock.unlock();
        }
    }

    public int take() {
        this.lock.lock();
        try {
            if (this.queue.size() == 0) {
                this.bufferIsNotEmpty.await();
            }
            int prime = this.queue.remove();
            this.bufferIsNotFull.signalAll();
            return prime;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.lock.unlock();
        }
    }
}

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private Queue<Integer> queue;
    private int bufferSize;
    //    This is the lock that is going to act as a guard on the encapsulated queue.
//    preventing access when the queue is not ready for certain actions,
//    like trying to remove a prime number from it when it is empty,
//    or adding a prime number to it when it is full.
    private final ReentrantLock lock = new ReentrantLock(true);

    //    These are the conditions that the Producer and Consumer threads
//    are going to wait for until they are signaled to be true.
//    Otherwise, the waiting thread will stay blocked.
    private final Condition bufferIsFull = lock.newCondition();
    private final Condition bufferIsEmpty = lock.newCondition();

    public Buffer() {}

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public void setBufferSize(int bufferSize) {
        this.queue = new LinkedList<>();
        this.bufferSize = bufferSize;
    }

    //    Put a prime number to the queue
    public void put(int prime) {
//        First, lock the queue
        this.lock.lock();
        try {
//            If the queue is full
            if (this.queue.size() == this.bufferSize) {
//                Block the current thread until a bufferIsNotFull signal is fired
                this.bufferIsEmpty.await();
            }
//            When the bufferIsNotFull signal is fired,
//            add the prime number to the queue
            this.queue.add(prime);
//            At this point, the buffer knows for sure that
//            the encapsulated queue is not empty, since it JUST added a prime number to it.
//            So, it signals all threads that it is ready to have prime numbers be taken from it
            this.bufferIsFull.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
//            The "finally" block will always execute, even after the return statement,
//            So we know for sure that when the prime number is put, the lock is unlocked,
//            allowing others to use it.
            this.lock.unlock();
        }
    }

    //    Take a prime number from the queue
    public int take() {
//        First, lock the queue
        this.lock.lock();
        try {
//            If the queue is empty
            if (this.queue.size() == 0) {
//                Block the current thread until a bufferIsNotEmpty signal is fired
                this.bufferIsFull.await();
            }
//            When the bufferIsNotEmpty signal is fired,
//            take a prime number from the queue
            int prime = this.queue.remove();
//            At this point, the buffer knows for sure that
//            the encapsulated queue is not full, since it JUST took a prime number from it.
//            So, it signals all threads that it is ready to have prime numbers added to it
            this.bufferIsEmpty.signalAll();
            return prime;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
//            The "finally" block will always execute, even after the return statement,
//            So we know for sure that when the prime number is taken, the lock is unlocked,
//            allowing others to use it.
            this.lock.unlock();
        }
    }
}

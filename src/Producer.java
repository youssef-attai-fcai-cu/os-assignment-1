public class Producer implements Runnable {
    private final Buffer buffer;
    private final int N;
    private boolean done;

    public boolean isDone() {
        return this.done;
    }

    public Producer(Buffer buffer, int n) {
        this.done = false;

        this.buffer = buffer;
        this.N = n;
    }

    @Override
    public void run() {
//      Iterate over all integers from 0 to N
        for (int i = 0; i <= this.N; i++) {
//          If the number is prime
            if (this.isPrime(i)) {
//              Put it in the buffer
                this.buffer.put(i);
            }
        }
//      Producer is done producing, Consumer can now terminate (i.e. stop waiting).
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

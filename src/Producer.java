public class Producer implements Runnable {
    private final Buffer buffer;
    private int N;
    private boolean done;

    private int largestPrime;
    private int numberOfPrimesGenerated;
    public void setDone(boolean done, Object by) {
//        System.out.println("DONE: " + done + ", BY: " + by.toString());
        this.done = done;
    }

    public boolean isDone() {
        return this.done;
    }

    public Producer(Buffer buffer) {
        this.setDone(false, this);
        this.buffer = buffer;
    }

    public void setN(int n) {
        this.N = n;
    }

    @Override
    public void run() {
        this.largestPrime = 0;
        this.numberOfPrimesGenerated = 0;

        try {
//      Iterate over all integers from 0 to N
            for (int i = 0; i <= this.N; i++) {
//          If the number is prime
                if (this.isPrime(i)) {
//                  Update largest prime
                    this.largestPrime = Math.max(this.largestPrime, i);
//                  Update number of primes generated
                    this.numberOfPrimesGenerated++;
//                  Put it in the buffer
                    this.buffer.put(i);
                }
            }
        } finally {
//      Producer is done producing, Consumer can now terminate (i.e. stop waiting).
            this.setDone(true, this);
        }
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int getLargestPrime() {
        return this.largestPrime;
    }

    public int getNumberOfPrimesGenerated() {
        return this.numberOfPrimesGenerated;
    }
}

class Producer extends Thread {
    int N;
    Buffer buf;

    int largestPrime = 0;
    int numberOfPrimesGenerated = 0;

    public Producer(Buffer buf, int n) {
        this.buf = buf;
        this.N = n;
    }

    public void run() {
        for (int i = 0; i <= this.N; i++) {
            if (this.isPrime(i)) {
                this.buf.produce(i);
                this.numberOfPrimesGenerated++;
                this.largestPrime = i;
            }
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
}
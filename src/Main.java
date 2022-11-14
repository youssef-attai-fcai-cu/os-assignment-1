public class Main {
    public static void main(String[] args) {
//        The buffer size (i.e. the limit for the blocking queue)
        int bufferSize = 8;
        Buffer buffer = new Buffer(bufferSize);

        Producer producer = new Producer(buffer, 100000);
        Consumer consumer = new Consumer(buffer, "outputFile.txt", producer);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}

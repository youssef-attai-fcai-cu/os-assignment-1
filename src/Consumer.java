import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;
    private final String outputFileName;
    private final Producer producer;


    public Consumer(BlockingQueue<Integer> queue, String outputFileName, Producer producer) {
        this.queue = queue;
        this.outputFileName = outputFileName;
        this.producer = producer;


//        Create the output file for the prime numbers
        this.createFile();
    }

    @Override
    public void run() {
//        As long as the producer is not done producing
        while (!this.producer.isDone()) {
            try {
//                Keep taking prime numbers from the buffer and write them to the output file
                int nextPrime = queue.take();
                this.writeToOutputFile(nextPrime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createFile() {
        try {
            File myObj = new File(this.outputFileName);
            if (myObj.createNewFile()) {
                System.out.println("Created file: " + myObj.getName());
            } else {
                System.out.println(this.outputFileName + " already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void writeToOutputFile(Integer prime) {
        try {
            FileWriter myWriter = new FileWriter(this.outputFileName, true);
            myWriter.write("\"" + prime + "\", ");
            myWriter.close();
            System.out.println("Added: " + prime);
        } catch (IOException e) {
            System.out.println("Error: " + prime);
            e.printStackTrace();
        }
    }
}

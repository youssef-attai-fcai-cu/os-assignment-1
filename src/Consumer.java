import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private final String outputFileName;
    private final Producer producer;


    public Consumer(Buffer buffer, String outputFileName, Producer producer) {
        this.buffer = buffer;
        this.outputFileName = outputFileName;
        this.producer = producer;


//        Create the output file for the prime numbers
        this.createFile();
    }

    @Override
    public void run() {
//      As long as the producer is not done producing
        while (!this.producer.isDone()) {
//          Keep taking prime numbers from the buffer and write them to the output file
            int nextPrime = buffer.take();
            this.writeToOutputFile(nextPrime);
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

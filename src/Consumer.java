import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private String outputFileName;
    private final Producer producer;
    private Callback updateCallback;
    private Callback doneCallback;

    public void setUpdateCallback(Callback updateCallback) {
        this.updateCallback = updateCallback;
    }

    public void setDoneCallback(Callback doneCallback) {
        this.doneCallback = doneCallback;
    }

    public Consumer(Buffer buffer, Producer producer) {
        this.buffer = buffer;
        this.producer = producer;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    @Override
    public void run() {
//        Always reset the producer
        this.producer.setDone(false, this);

//        Create the output file for the prime numbers
        this.createFile();

//      As long as the Producer is not done producing
        while (!this.producer.isDone() || !this.buffer.isEmpty()) {
//          Keep taking prime numbers from the buffer and write them to the output file
            int nextPrime = buffer.take();
            this.writeToOutputFile(nextPrime);
            this.updateCallback.call();
        }
        this.doneCallback.call();
    }

    private void createFile() {
        File outFile = new File(this.outputFileName);
        try {
            boolean success = outFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//            if (myObj.createNewFile()) {
//                System.out.println("Created file: " + myObj.getName());
//            } else {
//                System.out.println(this.outputFileName + " already exists.");
//            }
    }

    private void writeToOutputFile(Integer prime) {
        try {
            FileWriter myWriter = new FileWriter(this.outputFileName, true);
            myWriter.write("\"" + prime + "\", ");
            myWriter.close();
//            System.out.println("Consumed: " + prime);
        } catch (IOException e) {
            System.out.println("Consumer Error: " + prime);
            e.printStackTrace();
        }
    }
}

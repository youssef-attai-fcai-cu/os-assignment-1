import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Consumer extends Thread {
    Buffer buf;
    String outputFileName;
    Callback updateCallback;

    public Consumer(Buffer buf, String outputFileName) {
        this.buf = buf;
        this.outputFileName = outputFileName;
    }

    public void run() {
        this.createFile();
        while (true) {
            this.writeToOutputFile(buf.consume());
            this.updateCallback.call();
        }
    }

    private void createFile() {
        File outFile = new File(this.outputFileName);
        try {
            boolean success = outFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToOutputFile(Integer prime) {
        try {
            FileWriter myWriter = new FileWriter(this.outputFileName, true);
            myWriter.write("\"" + prime + "\", ");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Consumer Error: " + prime);
            e.printStackTrace();
        }
    }

    public void setUpdateCallback(Callback callback) {
        this.updateCallback = callback;
    }
}
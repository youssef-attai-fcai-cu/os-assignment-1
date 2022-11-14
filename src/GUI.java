import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JTextField tfN;
    private JTextField tfBufferSize;
    private JTextField tfOutputFile;
    private JButton startProducerButton;
    private JPanel mainPanel;
    private JLabel largestPrime;
    private JLabel generatedPrime;
    private JLabel timeElapsed;

    long startTime;

    public GUI() {
//        The buffer size (i.e. the limit for the blocking queue)
        Buffer buffer = new Buffer();

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer, producer);

        consumer.setUpdateCallback(new Callback() {
            @Override
            public void call() {
                long elapsedTime = (System.nanoTime() - startTime) / 1000000;
                timeElapsed.setText(Long.toString(elapsedTime) + " ms");
                largestPrime.setText(Integer.toString(producer.getLargestPrime()));
                generatedPrime.setText(Integer.toString(producer.getNumberOfPrimesGenerated()));
            }
        });

        consumer.setDoneCallback(new Callback() {
            @Override
            public void call() {
                startProducerButton.setEnabled(true);
            }
        });

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Prime Production");
        setSize(420, 420);
        setResizable(false);
        setVisible(true);
        pack();

        startProducerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startProducerButton.setEnabled(false);

                if (e.getSource() == startProducerButton) {
                    int N = Integer.parseInt(tfN.getText());
                    int bufferSize = Integer.parseInt(tfBufferSize.getText());
                    String outputFileName = tfOutputFile.getText();

                    buffer.setBufferSize(bufferSize);
                    producer.setN(N);
                    consumer.setOutputFileName(outputFileName);

                    Thread producerThread = new Thread(producer);
                    Thread consumerThread = new Thread(consumer);

                    startTime = System.nanoTime();

                    producerThread.start();
                    consumerThread.start();
                }
            }
        });
    }
}




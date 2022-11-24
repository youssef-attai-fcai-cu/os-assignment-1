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

    public GUI() {
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
                if (e.getSource() == startProducerButton) {
                    long startTime = System.nanoTime();

                    int N = Integer.parseInt(tfN.getText());
                    int bufferSize = Integer.parseInt(tfBufferSize.getText());
                    String outputFileName = tfOutputFile.getText();

                    Buffer buf = new Buffer(bufferSize);
                    Producer P = new Producer(buf, N);
                    Consumer C = new Consumer(buf, outputFileName);

                    C.setUpdateCallback(new Callback() {
                        @Override
                        public void call() {
                            long elapsedTime = (System.nanoTime() - startTime) / 1000000;

                            timeElapsed.setText(elapsedTime + " ms");
                            largestPrime.setText(Integer.toString(P.largestPrime));
                            generatedPrime.setText(Integer.toString(P.numberOfPrimesGenerated));
                        }
                    });

                    P.start();
                    C.start();
                }
            }
        });
    }
}




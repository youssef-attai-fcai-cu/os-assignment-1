import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Threads_GUI extends JFrame {
    private JTextField tfN;
    private JTextField tfBufferSize;
    private JTextField tfOutputFile;
    private JButton startProducerButton;
    private JPanel mainPanel;
    private JLabel largestPrime;
    private JLabel generatedPrime;
    private JLabel timeElapsed;

    public Threads_GUI() {

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
                if(e.getSource()== startProducerButton){
//                what happens when the button is clicked
//                System.out.println("kill");
                }

            }
        });
    }


        public static void main(String[] args) {
            new Threads_GUI();

        }
    }




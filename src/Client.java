import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUI{
    private Client client;
    GUI(Client client){
        this.client=client;
        JFrame frame = new JFrame("My Swing Window");
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        JTextField textField = new JTextField();
        textField.setBounds(100, 100, 1720, 500);

        JButton button = new JButton("Send");
        button.setBounds(960, 800, 100, 40);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text=textField.getText();
                System.out.println(text);
                textField.setText("");
            }
        });
        frame.add(textField);
        frame.add(button);
        frame.setVisible(true);
    }

}


public class Client{
    public void startGUI(){
        GUI gui=new GUI(this);
    }

}
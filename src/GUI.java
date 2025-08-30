import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    Client client =  new Client();
    GUI(Client client){
        JFrame frame = new JFrame("Chat Window");
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(100, 100, 1720, 500);

        JTextField chatInput = new JTextField();
        chatInput.setBounds(100, 650, 1620, 50);

        JButton button = new JButton("Send");
        button.setBounds(1720, 650, 100, 50);

        ActionListener sendAction = new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String text = chatInput.getText();
                System.out.println(text);
                chatArea.append(text + "\n");
                chatInput.setText("");
            }
        };
        button.addActionListener(sendAction);
        chatInput.addActionListener(sendAction);

        frame.add(scrollPane);
        frame.add(chatInput);
        frame.add(button);
        frame.setVisible(true);
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

class DrawPanel extends JPanel {
    private final java.util.List<Point> points = new ArrayList<>();
    private int prevX, prevY;

    public DrawPanel() {
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                // Draw line from previous point to current point
                points.add(new Point(prevX, prevY));
                points.add(new Point(x, y));
                System.out.println("Line: (" + prevX + "," + prevY + ") -> (" + x + "," + y + ")");
                prevX = x;
                prevY = y;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int i = 0; i < points.size(); i += 2) {
            if (i + 1 < points.size()) {
                Point p1 = points.get(i);
                Point p2 = points.get(i + 1);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    public void clear() {
        points.clear();
        repaint();
        System.out.println("Canvas cleared!");
    }
}

//--------------------------------------------//
public class GUI {
    Client client ;
    private JTextArea chatArea;
    GUI(Client client){
        JFrame frame = new JFrame("Chat Window");
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        String[] columns = {"Username","Score"};

        // Table model allows dynamic updates
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);

        // JTable using the model
        JTable table = new JTable(tableModel);

        // Scroll pane for the table
        JScrollPane tscrollPane = new JScrollPane(table);
        tscrollPane.setBounds(1600, 100, 200, 500);
        frame.add(tscrollPane);

        int i=0;
        String username = JOptionPane.showInputDialog(null,
                "Enter your username:", // Message
                "Login",               // Dialog title
                JOptionPane.QUESTION_MESSAGE);

        tableModel.addRow(new Object[]{username,i});

        JLabel clabel = new JLabel("Canvas");
        clabel.setBounds(100, 70, 100, 30);
        frame.add(clabel);

        DrawPanel canvas = new DrawPanel();
        canvas.setBounds(100, 100, 1400, 500); // x=20, y=20, width=600, height=300
        frame.add(canvas);

        // --- Clear Button ---
        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(100, 600, 100, 30); // below the canvas
        clearBtn.addActionListener(e -> canvas.clear());
        frame.add(clearBtn);

        JLabel blabel = new JLabel("Chatbox");
        blabel.setBounds(100, 670, 100, 30);
        frame.add(blabel);

         chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(100, 700, 1400, 200);

        JTextField chatInput = new JTextField();
        chatInput.setBounds(100, 900, 1300, 30);

        JButton button = new JButton("Send");
        button.setBounds(1400, 900, 100, 30);

        ActionListener sendAction = e -> {
            String text = chatInput.getText().trim();
            if (!text.isEmpty()) {
                // Show locally
                //chatArea.append(username + ": " + text + "\n");
                // Send to server
                client.sendMessage(username + ": " + text);
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
    public void addMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(message + "\n");
        });
    }
}

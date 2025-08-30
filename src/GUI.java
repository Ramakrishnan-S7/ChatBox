import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    Client client =  new Client();
    GUI(Client client){
        JFrame frame = new JFrame("Chat Window");
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        DrawPanel canvas = new DrawPanel();
        canvas.setBounds(20, 20, 600, 300); // x=20, y=20, width=600, height=300
        frame.add(canvas);

        // --- Clear Button ---
        JButton clearBtn = new JButton("Clear");
        clearBtn.setBounds(20, 330, 100, 30); // below the canvas
        clearBtn.addActionListener(e -> canvas.clear());
        frame.add(clearBtn);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(100, 800, 1720, 200);

        JTextField chatInput = new JTextField();
        chatInput.setBounds(100, 1000, 1620, 30);

        JButton button = new JButton("Send");
        button.setBounds(1720, 1000, 100, 30);

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

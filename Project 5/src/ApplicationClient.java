import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.IOException;
import java.net.*;


/**
 * Project 4 - Application
 * <p>
 * The Application class is the class that
 * does all the interactions with the user.
 *
 * @author Team #002, Section Y01
 * @version July 21, 2021
 */

public class ApplicationClient {

    private static boolean loop = true;
    private static String response;
    private static JButton enterButton;
    private static JTextField ans;

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 4244);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
            String completeLine = "";
            String line;
            while ((line = br.readLine()) != null) {
                completeLine += line + "\n";
                if (line.length() != 0 && line.charAt(line.length() - 1) == ' ') {
                    displayToGUI(completeLine);
                    pw.println(response);
                    pw.flush();
                    loop = true;
                    completeLine = "";
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static void displayToGUI(String prompt) {
        JFrame frame = new JFrame("PostStar");
        String[] lines = prompt.split("\n");

        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel p = new JPanel();
        for (String s : lines) {
            JLabel label = new JLabel(s);
            panel.add(label);
        }
        ans = new JTextField(10);
        panel.add(ans);
        enterButton = new JButton("Enter");
        enterButton.addActionListener(actionListener);
        p.add(enterButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        content.add(scroll, BorderLayout.NORTH);
        content.add(p, BorderLayout.SOUTH);
        frame.setVisible(true);
        try {
            while (loop) {
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void getResponse(String reply) {
        response = reply;
        loop = false;
        if (reply.equals(null)) {
            response = "";
        }
    }

    public static ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == enterButton) {
                String reply = ans.getText();
                getResponse(reply);
            }
        }
    };


}




import javax.swing.*;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;

/**
 * Project 5 - ApplicationClientSimpleGUI
 * <p>
 * The Application class client that uses
 * JOptionPanes
 *
 * @author Team #002, Section Y01
 * @version August 3, 2021
 */

public class ApplicationClientSimpleGUI {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4244);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        String line;

        while ((line = br.readLine()) != null) {
            String[] lines = line.split("\n");
            JPanel panel = new JPanel(new GridLayout());
            ArrayList<JLabel> labels = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {
                labels.add(new JLabel(lines[i]));
                panel.add(labels.get(i));
            }

            if (line.length() != 0 && line.charAt(line.length() - 1) == ' ') {
                String ans = JOptionPane.showInputDialog(null, panel);
                pw.println(ans);
                pw.flush();
            } else {
                JOptionPane.showMessageDialog(null, panel);
            }
        }
        pw.close();
        br.close();
    }
}
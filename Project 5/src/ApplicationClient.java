import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ApplicationClient implements Runnable {


    JButton posts;
    JButton myAccount;
    JButton logOut;
    JButton signIn;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ApplicationClient());
    }

    public void run() {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());
        frame.setSize(600,400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Application Client");
        frame.setVisible(true);

        posts = new JButton("All Posts");
        myAccount = new JButton("My Account");
        logOut = new JButton("Log Out");

        JPanel panelBottom = new JPanel();
        JPanel panelTop = new JPanel();
        panelTop.add(logOut);
        panelBottom.add(myAccount);
        panelBottom.add(posts);
        content.add(panelTop, BorderLayout.NORTH);
        content.add(panelBottom, BorderLayout.WEST);

    }

    public ApplicationClient() {

    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ApplicationClient implements Runnable {


    JButton posts;
    JButton myAccount;
    JButton logOut;
    JButton signIn;
    JButton logIn;
    JButton newAccount;
    JButton create = new JButton("Create");

    JLabel username = new JLabel("Username: ");
    JTextField userInput = new JTextField(10);
    JLabel password = new JLabel("Password: ");
    JPasswordField passInput = new JPasswordField(10);
    JLabel name = new JLabel("Name: ");
    JTextField nameInput = new JTextField(10);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ApplicationClient());
    }

    public void run() {
        JFrame frame = new JFrame();
        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.setTitle("Application Sign In");
        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        logIn = new JButton("Log In");
        newAccount = new JButton("Create Account");
        JPanel logInScreen = new JPanel();
        logInScreen.add(logIn);
        logInScreen.add(newAccount);
        content.add(logInScreen, BorderLayout.SOUTH);

        logIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                String username = userInput.getText();
//                String password = new String(passInput.getPassword());
                JFrame logInFrame = new JFrame();

                logInFrame.setTitle("Application Sign In");
                logInFrame.setSize(300, 150);
                logInFrame.setLocationRelativeTo(null);
                logInFrame.setVisible(true);

                Container loginContent = logInFrame.getContentPane();

                loginContent.setLayout(new BorderLayout());

                JPanel userFields = new JPanel();
                JPanel passFields = new JPanel();
                userFields.add(username);
                userFields.add(userInput);
                passFields.add(password);
                passFields.add(passInput);
                loginContent.add(userFields, BorderLayout.NORTH);
                loginContent.add(passFields, BorderLayout.CENTER);
            }
        });

        newAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                String username = userInput.getText();
//                String password = new String(passInput.getPassword());
                JFrame logInFrame = new JFrame();

                logInFrame.setTitle("Application Sign In");
                logInFrame.setSize(300, 200);
                logInFrame.setLocationRelativeTo(null);
                logInFrame.setVisible(true);

                Container loginContent = logInFrame.getContentPane();

                loginContent.setLayout(new BorderLayout());

                JPanel userFields = new JPanel();
                JPanel passFields = new JPanel();
                JPanel nameFields = new JPanel();
                JPanel accountInfo = new JPanel();
                JPanel createButton = new JPanel();
                userFields.add(username);
                userFields.add(userInput);
                passFields.add(password);
                passFields.add(passInput);
                nameFields.add(name);
                nameFields.add(nameInput);
                createButton.add(create);

                accountInfo.add(userFields, BorderLayout.NORTH);
                accountInfo.add(passFields, BorderLayout.CENTER);

                loginContent.add(nameFields, BorderLayout.NORTH);
                loginContent.add(accountInfo, BorderLayout.CENTER);
                loginContent.add(createButton, BorderLayout.SOUTH);
            }
        });

//        posts = new JButton("All Posts");
//        myAccount = new JButton("My Account");
//        logOut = new JButton("Log Out");
//
//        JMenuBar menu = new JMenuBar();
//        JPanel panelBottom = new JPanel();
//        JPanel panelTop = new JPanel();
//        JPanel panelLogOut = new JPanel();
//        JPanel post = new JPanel();
//
//        content.add(menu, BorderLayout.NORTH);
//        menu.add(logOut);
//        menu.add(myAccount);
//        menu.add(posts);


    }

    public ApplicationClient() {

    }
}

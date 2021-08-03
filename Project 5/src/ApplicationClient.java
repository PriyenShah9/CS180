import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.IOException;
import java.util.Scanner;
import java.net.*;

/**
 * Project 5 - ApplicationClient
 * <p>
 * The ApplicationClient class is the class that
 * does all the interactions with the user through the GUI.
 *
 * @author Team #002, Section Y01
 * @version August 2, 2021
 */

public class ApplicationClient extends JComponent implements Runnable {

    JButton logIn;
    JButton newAccount;
    JButton exit;
    JButton continueButton = new JButton("Continue");
    JButton posts;
    JButton account;
    JButton comments;
    JButton addPost;
    JButton addComment;
    JButton deletePost;
    JButton deleteComment;
    JButton editAccount;
    JButton deleteAccount;
    JButton logOut;
    JButton importPost;

    JLabel username = new JLabel("Username: ");
    JTextField userInput = new JTextField(10);
    JLabel password = new JLabel("Password: ");
    JPasswordField passInput = new JPasswordField(10);
    JLabel name = new JLabel("Name: ");
    JTextField nameInput = new JTextField(10);


    public static Socket socket;
    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(new ApplicationClient());
    }

    public void run() {

        try {
            startScreen();

            socket = new Socket("localhost", 4244);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            String line;

            br.close();
            pw.close();

            Clicklistener click= new Clicklistener();
            logIn.addActionListener(click);
            newAccount.addActionListener(click);
            exit.addActionListener(click);
            continueButton.addActionListener(click);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startScreen() {
        JFrame startFrame = new JFrame();
        JPanel startPanelTop = new JPanel();
        JPanel startPanelBottom = new JPanel();
        Container startContent = startFrame.getContentPane();

        startContent.setLayout(new BorderLayout());
        startFrame.setSize(300, 150);
        startFrame.setLocationRelativeTo(null);
        startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startFrame.setVisible(true);

        logIn = new JButton("Log In");
        newAccount = new JButton("New Account");
        exit = new JButton("Exit");

        startPanelTop.add(logIn);
        startPanelTop.add(newAccount);
        startPanelBottom.add(exit);

        startContent.add(startPanelTop, BorderLayout.NORTH);
        startContent.add(startPanelBottom, BorderLayout.SOUTH);

        // if any button is clicked, hide frame

    }

    public void logInScreen() {
        JFrame logFrame = new JFrame();

        Container logContent = logFrame.getContentPane();

        logContent.setLayout(new BorderLayout());
        logFrame.setSize(300, 150);
        logFrame.setLocationRelativeTo(null);
        logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logFrame.setVisible(true);

        JPanel userFields = new JPanel();
        JPanel passFields = new JPanel();

        userFields.add(username);
        userFields.add(userInput);
        passFields.add(password);
        passFields.add(passInput);

        logContent.add(userFields, BorderLayout.NORTH);
        logContent.add(passFields, BorderLayout.CENTER);
        logContent.add(continueButton, BorderLayout.SOUTH);

        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to validate input
            }
        });

        passInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to validate input
            }
        });
    }

    public void newAccountScreen() {
        JFrame frame = new JFrame();

        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Container loginContent = frame.getContentPane();

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
        createButton.add(continueButton);

        accountInfo.add(userFields, BorderLayout.NORTH);
        accountInfo.add(passFields, BorderLayout.CENTER);

        loginContent.add(nameFields, BorderLayout.NORTH);
        loginContent.add(accountInfo, BorderLayout.CENTER);
        loginContent.add(createButton, BorderLayout.SOUTH);

        nameInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to validate input
            }
        });

        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to validate input
            }
        });

        passInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to validate input
            }
        });
    }

    public void accountPage() {
        JFrame frame = new JFrame();

        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Container content = frame.getContentPane();

        content.setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();

        account = new JButton("Account");
        posts = new JButton("Posts");
        addPost = new JButton("New Post");
        importPost = new JButton("Import Post");
        logOut = new JButton("Log Out");

        panelTop.add(account);
        panelTop.add(posts);
        panelTop.add(addPost);
        panelTop.add(importPost);
        panelTop.add(logOut);

        content.add(panelTop, BorderLayout.NORTH);

        JPanel accountPanel = new JPanel();
        JLabel accountName = new JLabel("Account: "); // get account name from server
        // add posts from server
        // add comments from server
        accountPanel.add(accountName);

        JPanel postPanel = new JPanel();
        JLabel postName = new JLabel("Post: "); // get account name from server
        // add posts from server
        // add comments from server
        postPanel.add(postName);

        JPanel accountActions = new JPanel();
        editAccount = new JButton("Edit Account");
        deleteAccount = new JButton("Delete Account");
        accountActions.add(editAccount);
        accountActions.add(deleteAccount);

        accountActions.setVisible(true);
        content.add(accountActions, BorderLayout.SOUTH);

        accountPanel.setVisible(true);
        content.add(accountPanel, BorderLayout.CENTER);
        postPanel.setVisible(false);

        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to retrieve information
                accountPanel.setVisible(true);
                accountActions.setVisible(true);
                postPanel.setVisible(false);
                content.add(accountPanel, BorderLayout.CENTER);

                Clicklistener click= new Clicklistener();
                editAccount.addActionListener(click);
                deleteAccount.addActionListener(click);

            }
        });

        posts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to retrieve information
                postPanel.setVisible(true);
                accountActions.setVisible(false);
                accountPanel.setVisible(false);
                content.add(postPanel, BorderLayout.CENTER);
                // display all posts and comments

            }
        });

        addPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "",
                        "New Post", JOptionPane.PLAIN_MESSAGE);
            }
        });

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // communicate with server to log out
            }
        });

        importPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // import post
            }
        });

    }


    private class Clicklistener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == logIn) {
                logInScreen();
            } else if (e.getSource() == newAccount) {
                newAccountScreen();
            } else if (e.getSource() == exit) {
                return; // need to get exit button to work
            } else if (e.getSource() == continueButton) { // check that everything is valid
                accountPage();
            } else if (e.getSource() == editAccount) {
                JOptionPane.showMessageDialog(null, "",
                                "Edit Account", JOptionPane.PLAIN_MESSAGE);
            } else if (e.getSource() == deleteAccount) {
                JOptionPane.showMessageDialog(null, "Are you sure you would like to proceed?",
                        "Delete Account", JOptionPane.ERROR_MESSAGE);
                // communicate w server to delete account
            }
        }
    }

}




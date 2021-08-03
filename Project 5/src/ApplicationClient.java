import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.IOException;
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
    JButton continueButton = new JButton("Continue"); //crystal did not use this
    JButton posts;
    JButton account;
    JButton addPost;

    JButton editAccount;
    JButton deleteAccount;
    JButton logOut;
    JButton importPost;
    JButton viewPosts;
    JButton makeComment;
    JButton editComment;
    JButton viewComments;
    JButton exportPost;

    JLabel username = new JLabel("Username: ");
    JTextField userInput = new JTextField(10);
    JLabel password = new JLabel("Password: ");
    JPasswordField passInput = new JPasswordField(10);
    JLabel name = new JLabel("Name: ");
    JTextField nameInput = new JTextField(10);


    public static Socket socket;
    public static BufferedReader br;
    public static PrintWriter pw;
    static boolean q1 = false;
    static boolean q2 = false;
    static boolean message = false;
    static boolean response = false;
    static String line;
    public static void main(String[] args) throws IOException {
        ApplicationClient ac = new ApplicationClient();
        SwingUtilities.invokeLater(ac);
        socket = new Socket("localhost", 4244);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream());
        while ((line = br.readLine()) != null) {
            if (line.length() != 0 && line.charAt(line.length() - 1) == ' ') {
                if (line.equals("Q1 ")) {
                    q1 = true;
                } else if (line.equals("Q2 ")) {
                    q2 = true;
                } else { //any other time a text response is needed
                    response = true;
                }
            } else { //no response needed
                message = true;
            }
        }

        br.close();
        pw.close();
    }

    public void run() {
        while (true) {
            if (q1) {
                q1 = false;
                startScreen();
            } else if (q2) {
                q2 = false;
                optionScreen();
            } else if (response) {
                response = false;
                JFrame frame = new JFrame();
                JLabel label = new JLabel(line);
                JTextField text = new JTextField(8);
                JButton submit = new JButton("Submit");
                frame.add(label);
                frame.setVisible(true);

                submit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println(text.getText());
                        pw.flush();
                        frame.setVisible(false);
                    }
                });
            } else if (message) {
                message = false;
                JFrame frame = new JFrame();
                JLabel label = new JLabel(line);
                JButton ok = new JButton("OK");
                frame.add(label);
                frame.setVisible(true);

                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pw.println("\n");
                        frame.setVisible(false);
                    }
                });
            }

            //Clicklistener click= new Clicklistener();
            //logIn.addActionListener(click);
            //newAccount.addActionListener(click);
            //exit.addActionListener(click);
            //continueButton.addActionListener(click);
        }
    }


    public void startScreen() {
        System.out.println("Hi");
        JFrame startFrame = new JFrame();
        JPanel startPanelTop = new JPanel();
        JPanel startPanelBottom = new JPanel();
        Container startContent = startFrame.getContentPane();

        startContent.setLayout(new BorderLayout());
        startFrame.setSize(300, 150);
        startFrame.setLocationRelativeTo(null);
        startFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        logIn = new JButton("Log In");
        newAccount = new JButton("New Account");
        exit = new JButton("Exit");

        startPanelTop.add(logIn);
        startPanelTop.add(newAccount);
        startPanelBottom.add(exit);

        startContent.add(startPanelTop, BorderLayout.NORTH);
        startContent.add(startPanelBottom, BorderLayout.SOUTH);

        startFrame.setVisible(true);

        // if any button is clicked, hide frame
        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("1\n");
                pw.flush();
            }
        });

        newAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("2\n");
                pw.flush();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("3\n");
                pw.flush();
                startFrame.dispose();
            }
        });
    }

    public void optionScreen() {
        JFrame optionFrame = new JFrame();
        Container optionContent = optionFrame.getContentPane();
        optionContent.setLayout(new FlowLayout());
        optionFrame.setSize(300, 150);
        optionFrame.setLocationRelativeTo(null);
        optionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel optionR = new JPanel();
        JPanel optionL = new JPanel();
        JPanel optionB = new JPanel();

        editAccount = new JButton("Edit account.");
        viewPosts = new JButton("View all of a user's posts.");
        deleteAccount = new JButton("Delete account.");
        makeComment = new JButton("Make a comment.");
        editComment = new JButton("Edit/Delete a comment.");
        viewComments = new JButton("View all of a user's comments.");
        importPost = new JButton("Import post.");
        exportPost = new JButton("Export post.");
        logOut = new JButton("Log out.");

        optionL.add(editAccount);
        optionL.add(viewPosts);
        optionL.add(deleteAccount);
        optionL.add(makeComment);
        optionR.add(editComment);
        optionR.add(viewComments);
        optionR.add(importPost);
        optionR.add(exportPost);
        optionB.add(logOut);

        optionContent.add(optionL);
        optionContent.add(optionR);
        optionContent.add(optionB);

        optionFrame.setVisible(true);

        editAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("1\n");
                pw.flush();
            }
        });

        viewPosts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("2\n");
                pw.flush();
            }
        });

        deleteAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("3\n");
                pw.flush();
            }
        });

        makeComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("4\n");
                pw.flush();
            }
        });

        editComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("5\n");
                pw.flush();
            }
        });

        viewComments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("6\n");
                pw.flush();
            }
        });

        importPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("7\n");
                pw.flush();
            }
        });

        exportPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("8\n");
                pw.flush();
            }
        });

        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pw.println("9\n");
                pw.flush();
            }
        });
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




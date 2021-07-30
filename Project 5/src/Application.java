import java.io.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;


/**
 * Project 4 - Application
 * <p>
 * The Application class is the class that
 * does all the interactions with the user.
 *
 * @author Team #002, Section Y01
 * @version July 21, 2021
 */

public class Application {

    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private static ArrayList<Post> posts = new ArrayList<Post>();
    private static ArrayList<Comment> comments = new ArrayList<Comment>();
    private static String usernameAccountLoggedIn;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ReadData r = new ReadData("accounts.txt", "posts.txt", "comments.txt");
        try {
            r.readAccounts();
            r.readPosts();
            r.readComments();
        } catch (IOException e) {
            System.out.println("There are no existing accounts.");
        }
        accounts = r.accounts;
        posts = r.posts;
        comments = r.comments;
        while (true) {
            String firstAns = initialQuestion(scan);
            if (firstAns.equals("3")) {
                usernameAccountLoggedIn = null;
                break;
            }
            while (true) {
                if (firstAns.equals("1")) {
                    usernameAccountLoggedIn = login(scan);
                } else if (firstAns.equals("2")) {
                    Account a = createAccount(scan);
                    accounts.add(a);
                    usernameAccountLoggedIn = a.getUsername();
                }
                String ans = nextQuestion(scan);
                if (ans.equals("1")) {
                    editAccount(scan);
                } else if (ans.equals("2")) {
                    viewPosts(scan);
                } else if (ans.equals("3")) {
                    for (int i = 0; i < accounts.size(); i++) {
                        if (usernameAccountLoggedIn.equals(accounts.get(i).getUsername())) {
                            accounts.remove(i);
                        }
                    }
                    break;
                } else if (ans.equals("4")) {
                    makeComment(scan);
                } else if (ans.equals("5")) {
                    editComment(scan);
                } else if (ans.equals("6")) {
                    viewComments(scan);
                } else if (ans.equals("7")) {
                    importPost(scan);
                } else if (ans.equals("8")) {
                    exportPost(scan);
                } else {
                    usernameAccountLoggedIn = null;
                    break;
                }
                firstAns = "";
            }
        }
        r.accounts = accounts;
        r.posts = posts;
        r.comments = comments;
        try {
            r.writeAccountInformation();
            r.writePostInformation();
            r.writeCommentInformation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String initialQuestion(Scanner scan) {
        System.out.println("Would you like to:\n1. Log in\n2. Create an Account\n3. Exit the program\n");
        String ans = scan.nextLine();
        while (!(ans.equals("1")) && !(ans.equals("2")) && !(ans.equals("3"))) {
            System.out.println("You must answer with either 1, 2, or 3.");
            System.out.println("Would you like to:\n1. Log in\n2. Create an Account\n3. Exit the program\n");
            ans = scan.nextLine();
        }
        return ans;
    }


    public static String nextQuestion(Scanner scan) {
        System.out.println("Would you like to:" +
                "\n1. Edit your account." +
                "\n2. View all of a user's posts." +
                "\n3. Delete account." +
                "\n4. Make a comment." +
                "\n5. Edit/Delete a comment." +
                "\n6. View all of a user's comments." +
                "\n7. Import a post." +
                "\n8. Export a post." +
                "\n9. Log out.");
        String ans = scan.nextLine();
        while (!(ans.equals("1")) && !(ans.equals("2")) && !(ans.equals("3")) && !(ans.equals("4")) &&
                !(ans.equals("5")) && !(ans.equals("6")) && !(ans.equals("7")) && !(ans.equals("8"))
                && !ans.equals("9")) {
            System.out.println("You must answer with either 1, 2, 3, 4, 5, 6, 7, 8, or 9.");
            System.out.println("Would you like to:" +
                    "\n1. Edit your account." +
                    "\n2. View all of a user's posts." +
                    "\n3. Delete account." +
                    "\n4. Make a comment." +
                    "\n5. Edit/Delete a comment." +
                    "\n6. View all of a user's comments." +
                    "\n7. Import a post." +
                    "\n8. Export a post." +
                    "\n9. Log out.");
            ans = scan.nextLine();
        }
        return ans;
    }

    public static Account createAccount(Scanner scan)  {
        System.out.print("What is your name? ");
        String name = scan.nextLine();
        System.out.print("What would you like your username to be? ");
        String username = scan.nextLine();
        while (usernameValidity(username) != null) {
            System.out.print("This username is taken.\nTry again: ");
            username = scan.nextLine();
        }
        System.out.print("What would you like your password to be? ");
        String password = scan.nextLine();
        System.out.print("Re-enter your password: ");
        String reenteredPassword = scan.nextLine();
        while (!password.equals(reenteredPassword)) {
            System.out.print("That is incorrect. Try again: ");
            reenteredPassword = scan.nextLine();
        }
        System.out.println("Your account has been created.");
        return new Account(name, username, password, true);
    }


    public static String login(Scanner scan) {
        System.out.print("Username: ");
        String username = scan.nextLine();
        while(usernameValidity(username) == null) {
            System.out.println("Username does not exist. Try again. ");
            System.out.print("Username: ");
            username = scan.nextLine();
            if (username.equalsIgnoreCase("c")) {
                return username;
            }
        }
        Account a = usernameValidity(username);
        System.out.print("Password: ");
        String password = scan.nextLine();
        while (!password.equals(a.getPassword())) {
            System.out.print("Password is invalid. Try again: ");
            password = scan.nextLine();
        }
        a.logIn();
        return username;
    }

    public static void editAccount(Scanner scan) {
        Account a = usernameValidity(usernameAccountLoggedIn);
        System.out.println("Would you like to: " +
                "\n1. Make a post." +
                "\n2. Edit a post." +
                "\n3. Delete a post.");
        String ans = scan.nextLine();
        while (!(ans.equals("1")) && !(ans.equals("2")) && !(ans.equals("3"))) {
            System.out.println("You must reply with 1,2, or 3");
            System.out.println("Would you like to: " +
                    "\n1. Make a post." +
                    "\n2. Edit a post." +
                    "\n3. Delete a post.");
            ans = scan.nextLine();
        }
        if (ans.equalsIgnoreCase("1")) {
            System.out.print("What would you like your title to be? ");
            String title = scan.nextLine();
            while (getPostIndex(title, a) != -1) {
                System.out.println("This title already exists.");
                System.out.print("What would you like your title to be? ");
                title = scan.nextLine();
            }
            System.out.print("Enter your post: ");
            String postContent = scan.nextLine();
            Post post = new Post(title, a.getUsername(), postContent);
            a.addPost(post);
            System.out.println("Your post has been made");
        } else if (ans.equalsIgnoreCase("2")) {
            System.out.print("What is the title of the post that you would like to edit: ");
            String title = scan.nextLine();
            while (getPostIndex(title, a) == -1) {
                System.out.print("This title does not exist. Try again: ");
                title = scan.nextLine();
            }
            int postIndex = getPostIndex(title, a);
            System.out.print("What would you like to change your post to? ");
            String changedContext = scan.nextLine();
            a.editPost(a.getPosts().get(postIndex), changedContext);
            System.out.println("Your change has been made. ");
        } else if (ans.equalsIgnoreCase("3")) {
            System.out.print("What is the title of the post you would like to delete? ");
            String title = scan.nextLine();
            while (getPostIndex(title, a) == -1) {
                System.out.print("This title does not exist. Try again: ");
                title = scan.nextLine();
            }
            int postIndex = getPostIndex(title, a);
            a.getPosts().remove(postIndex);
        }
    }

    public static void viewPosts(Scanner scan) {
        System.out.println("Enter the username of the account you would like to view: ");
        String username = scan.nextLine();
        while (usernameValidity(username) == null) {
            System.out.print("This username does not exist.\nTry again: ");
            username = scan.nextLine();
        }
        Account account = usernameValidity(username);
        if (account.getPosts().size() == 0) {
            System.out.println("This user has no posts.");
        } else {
            account.displayPosts();
        }
    }

    public static void viewComments(Scanner scan) {
        System.out.println("Enter the username of the account you would like to view: ");
        String username = scan.nextLine();
        while (usernameValidity(username) == null) {
            System.out.print("This username does not exist.\nTry again: ");
            username = scan.nextLine();
        }
        Account account = usernameValidity(username);
        if (account.getComments().size() == 0) {
            System.out.println("This user has made no comments.");
        } else {
            account.displayComments();
        }
    }

    public static void makeComment(Scanner scan) {
        System.out.println("Enter the author of the post you would like to comment on: ");
        String username = scan.nextLine();
        while (usernameValidity(username) == null) {
            System.out.print("This username does not exist.\nTry again: ");
            username = scan.nextLine();
        }
        Account account = usernameValidity(username);
        System.out.println("Enter the title of the post you would like to comment on: ");
        String title = scan.nextLine();
        while (getPostIndex(title, account) == -1) {
            System.out.print("This title does not exist. Try again: ");
            title = scan.nextLine();
        }
        int postIndex = getPostIndex(title, account);
        System.out.println("What would you like to comment on this post? ");
        String ans = scan.nextLine();
        Comment comment = new Comment(usernameAccountLoggedIn, ans);
        account.makeComment(comment, postIndex);
        System.out.println("Comment was made");
    }

    public static void editComment(Scanner scan) {
        System.out.println("Enter the author of the post you would like to edit/delete your comment on: ");
        String username = scan.nextLine();
        while (usernameValidity(username) == null) {
            System.out.print("This username does not exist.\nTry again: ");
            username = scan.nextLine();
        }
        Account account = usernameValidity(username);
        System.out.println("Enter the title of the post you would like to edit/delete your comment on: ");
        String title = scan.nextLine();
        while (getPostIndex(title, account) == -1) {
            System.out.print("This title does not exist. Try again: ");
            title = scan.nextLine();
        }
        int postIndex = getPostIndex(title, account);
        System.out.println("Enter the comment you would like to edit/delete: ");
        String text = scan.nextLine();
        while (findComment(text, account.getPosts().get(postIndex).getComments()) == -1
            && findComment(text, account.getComments()) == -1) {
            System.out.print("This comment does not exist.\nTry again: ");
            text = scan.nextLine();
        }
        int commentIndexAccount = findComment(text, account.getComments());
        int commentIndexPost = findComment(text, account.getPosts().get(postIndex).getComments());
        System.out.println("Would you like to: " +
                "\n1. Edit the comment." +
                "\n2. Delete the comment.");
        String ans = scan.nextLine();
        while (!(ans.equals("1")) && !(ans.equals("2"))) {
            System.out.println("You must reply with 1 or 2");
            System.out.println("Would you like to: " +
                    "\n1. Edit the comment." +
                    "\n2. Delete the comment.");
            ans = scan.nextLine();
        }
        if (ans.equals("1")) {
            System.out.println("What would you like to change your comment to: ");
            String changedText = scan.nextLine();
            account.editComment(commentIndexAccount, commentIndexPost, postIndex, changedText);
        } else {
            account.deleteComment(commentIndexAccount, commentIndexPost, postIndex);
        }
        System.out.println("Your changes were made.");
    }

    public static void importPost(Scanner scan) {
        System.out.println("Enter the title of the post you would like to import: ");
        String ans = scan.nextLine();
        File f = new File(ans + ".csv");
        try(BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] information = line.split(" ");
                String title = information[0].replaceAll("_", " ");
                String author = information[1].replaceAll("_", " ");
                String text = information[2].replaceAll("_", " ");
                Post post = new Post(title, author, text);
                Account account = usernameValidity(usernameAccountLoggedIn);
                System.out.println(post);
                account.addPost(post);
                System.out.println("The post was added to your account.");
            }
        } catch(FileNotFoundException e) {
            System.out.println("This file does not exist.");
        } catch(IOException e) {
            System.out.println("The information in the file is invalid.");
        }
    }

    public static void exportPost(Scanner scan) {
        System.out.println("Enter the title of the post you would like to export: ");
        String ans = scan.nextLine();
        while (findPost(ans) == null) {
            System.out.println("This title does not exist.");
            System.out.println("Try again");
            ans = scan.nextLine();
        }
        Post post = findPost(ans);
        File f = new File(post.getTitle() + ".csv");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, false))) {
            pw.print(post.getTitle() + " " + post.getAuthorName() + " " + post.getText() + " " + post.getTimeStamp());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }



    public static int getPostIndex(String title, Account account) {
        for (int i = 0; i < account.getPosts().size(); i++) {
            if (title.equalsIgnoreCase(account.getPosts().get(i).getTitle())) {
                return i;
            }
        }
        return -1;
    }

    public static Post findPost(String title) {
        for (Account a : accounts) {
            for (Post p : a.getPosts()) {
                if (title.equalsIgnoreCase(p.getTitle())) {
                    return p;
                }
            }
        }
        return null;
    }

    public static int findComment(String context, ArrayList<Comment> comments) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getText().equalsIgnoreCase(context)) {
                return i;
            }
        }
        return -1;
    }


    public static Account usernameValidity(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                return accounts.get(i);
            }
        }
        return null;
    }


}

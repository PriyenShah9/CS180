import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Application {

    private static ArrayList<Account> accounts = new ArrayList<Account>();

    public static void main(String[] args) throws AccountException{
        Scanner scan = new Scanner(System.in);
        String firstAnswer = createAccountQuestion(scan);
        if (firstAnswer.equals("1")) {
            createAccount(scan);
        } else if (firstAnswer.equals("2")) {

        } else if (firstAnswer.equals("3")) {

        } else {
            return;
        }

    }

    public static String createAccountQuestion(Scanner scan) {
        System.out.print("Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? ");
        String ans = scan.nextLine();
        while (!ans.equals("1") && !ans.equals("2") && !ans.equals("3") && !ans.equals("4") && !ans.equals("5")) {
            System.out.println("You must answer with either 1, 2, 3, 4, or 5.");
            System.out.print("Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? ");
            ans = scan.nextLine();
        }
        return ans;
    }

    public static Account createAccount(Scanner scan) throws AccountException {
        System.out.print("What is your name? ");
        String name = scan.nextLine();
        System.out.print("What would you like your username to be? ");
        String username = scan.nextLine();
        while (usernameValidity(username) != null) {
            System.out.print("This username is taken.\n Try again: ");
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
        return new Account(username, password, name, true);
    }

    public static String login(Scanner scan) {
        System.out.println("To edit an account you must login.");
        System.out.print("Username: ");
        String username = scan.nextLine();
        while(usernameValidity(username) != null) {
            System.out.println("Username does not exist. Create account(c) or try again: ");
            username = scan.nextLine();
            if (username.equalsIgnoreCase("c")) {
                return username;
            }
        }
        Account a = usernameValidity(username);
        System.out.println("Password: ");
        String password = scan.nextLine();
        while (password.equals(a.getPassword())) {
            System.out.print("Password is invalid. Try again: ");
            password = scan.nextLine();
        }
        a.logIn();
        return username;
    }

    public static void viewAccount(Scanner scan, String username) {
        System.out.print("Enter the username of the account you would like to view: ");
        String usernameToBeViewed = scan.nextLine();
        while (usernameValidity(usernameToBeViewed) != null) {
            System.out.print("Couldn't find username. Try again: ");
            usernameToBeViewed = scan.nextLine();
        }
        Account a = usernameValidity(usernameToBeViewed);
        System.out.println("Here are " + usernameToBeViewed + "'s posts.");
        for (int i = 0; i < a.getPosts().size(); i++) {
            a.getPosts().get(i).displayPost();
            System.out.println();
        }
        System.out.print("Would you like to make a comment(c) on a post or go back(b)? ");
        String ans = scan.nextLine();
        while (!(ans.equalsIgnoreCase("c") || ans.equalsIgnoreCase("b"))) {
            System.out.println("You must answer with \"c\" or \"b\".");
            System.out.print("Would you like to make a comment(c) on a post or go back(b)? ");
            ans = scan.nextLine();
        }
        if (ans.equalsIgnoreCase("b")) {
            return;
        } else {
            System.out.print("Enter the title of the post you would like to make a comment to.");
            String title = scan.nextLine();
            while (getPostIndex(title, a) == -1) {
                System.out.print("Post with title " + title + "could not be found. Try again: ");
                title = scan.nextLine();
            }
            System.out.println("What would you like to comment on this post.");
            String comment = scan.nextLine();
            
        }
    }

    public static void editAccount(Scanner scan, String username) {
        Account a = usernameValidity(username);
        System.out.print("Would you like to create(c), edit(e), or delete(d) a post? ");
        String ans = scan.nextLine();
        while (!(ans.equalsIgnoreCase("c") || ans.equalsIgnoreCase("e")
                || ans.equalsIgnoreCase("d"))) {
            System.out.println("You must answer with c, e, or d.");
            System.out.print("Would you like to create(c), edit(e), or delete(d) a post? ");
            ans = scan.nextLine();
        }
        if (ans.equalsIgnoreCase("c")) {
            System.out.print("What would you like your title to be? ");
            String title = scan.nextLine();
            System.out.print("Enter your post: ");
            String postContent = scan.nextLine();
            LocalDateTime now = LocalDateTime.now();

        } else if (ans.equalsIgnoreCase("e")) {
            System.out.print("What is the title of the post that you would like to edit: ");
            String title = scan.nextLine();
            while (getPostIndex(title, a) == -1) {
                System.out.print("This title does not exist. Try again: ");
                title = scan.nextLine();
            }
            int postIndex = getPostIndex(title, a);

        } else if (ans.equalsIgnoreCase("d")) {
            System.out.print("What is the title of the post you would like to delete? ");
            String title = scan.nextLine();
            while (getPostIndex(title, a) == -1) {
                System.out.print("This title does not exist. Try again: ");
                title = scan.nextLine();
            }
            int postIndex = getPostIndex(title, a);
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

    public static Account usernameValidity(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                return accounts.get(i);
            }
        }
        return null;
    }


}

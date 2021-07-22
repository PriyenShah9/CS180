import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Application {

    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private static ArrayList<Post> posts = new ArrayList<Post>();
    private static ArrayList<Comment> comments = new ArrayList<Comment>();

    public static void main(String[] args) throws AccountException {
        File f = new File("storagefile.txt");
        if (f.exists()) {
            try {
                ReadData data = new ReadData("storagefile.txt");
            } catch (FileNotFoundException e) {
                System.out.println("Error loading previous data.");
            }
            accounts = ReadData.accounts;
            posts = ReadData.posts;
            comments = ReadData.comments;
        }

        Scanner scan = new Scanner(System.in);
        String username = "";
        while (true) {
            String firstAnswer = createAccountQuestion(scan);
            if (firstAnswer.equals("5")) {
                try {
                    ReadData.accounts = accounts;
                    ReadData.posts = posts;
                    ReadData.comments = comments;
                    ReadData.writeChangesToFile("storagefile.txt");
                } catch (FileNotFoundException e) {
                    System.out.println("Error saving changes");
                }
                return;
            }
            if (firstAnswer.equals("1")) {
                Account a = createAccount(scan);
                accounts.add(a);
                username = a.getUsername();
            } else {
                boolean logIn = true;
                if (username.equals("")) {
                    username = login(scan);
                }
                if (firstAnswer.equals("2")) {
                    editAccount(scan, username);
                } else if (firstAnswer.equals("3")) {
                    System.out.println(username);
                    Account a = usernameValidity(username);
                    try {
                        a.isLoggedIn();
                    } catch (AccessException e) {
                        System.out.print("You must be logged in to delete an account.");
                        logIn = false;
                    }
                    if (logIn) {
                        a.deleteAccount();
                        System.out.println("Account deleted");
                    }
                } else if (firstAnswer.equals("4")) {
                    viewAccount(scan, username);
                } else if (firstAnswer.equals("5")) {
                    try {
                        ReadData.writeChangesToFile("storagefile.txt");
                    } catch (FileNotFoundException e) {
                        System.out.println("Error saving changes");
                    }
                    return;
                }
            }
        }
    }


    public static String createAccountQuestion(Scanner scan) {
        System.out.print("Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? ");
        String ans = scan.nextLine();
        while (!(ans.equals("1")) && !(ans.equals("2")) && !(ans.equals("3")) && !(ans.equals("4")) && !(ans.equals("5"))) {
            System.out.println("You must answer with either 1, 2, 3, 4, or 5.");
            System.out.print("Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? ");
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
        System.out.println("To edit or view an account you must login.");
        System.out.print("Username: ");
        String username = scan.nextLine();
        while(usernameValidity(username) == null) {
            System.out.println("Username does not exist. Create account(c) or try again: ");
            username = scan.nextLine();
            if (username.equalsIgnoreCase("c")) {
                return username;
            }
        }
        Account a = usernameValidity(username);
        System.out.println("Password: ");
        String password = scan.nextLine();
        while (!password.equals(a.getPassword())) {
            System.out.print("Password is invalid. Try again: ");
            password = scan.nextLine();
        }
        a.logIn();
        return username;
    }

    public static void viewAccount(Scanner scan, String username) {
        Account commenting = usernameValidity(username);
        System.out.println(commenting.getComments().size());
        try {
            commenting.isLoggedIn();
        } catch (AccessException e) {
            System.out.print("You are not logged in. ");
            return;
        } catch (NullPointerException e) {
            System.out.print("You are not logged in. ");
            return;
        }
        boolean loop = true;
        while (loop) {
            System.out.print("Enter the username of the account you would like to view: ");
            String usernameToBeViewed = scan.nextLine();
            while (usernameValidity(usernameToBeViewed) == null) {
                System.out.print("Couldn't find username. Try again: ");
                usernameToBeViewed = scan.nextLine();
            }
            Account a = usernameValidity(usernameToBeViewed);
            if (a.getPosts() == null) {
                System.out.println("No posts");
                return;
            }
            System.out.println("Here are " + usernameToBeViewed + "'s posts.");
            for (int i = 0; i < a.getPosts().size(); i++) {
                a.getPosts().get(i).displayPost();
                System.out.println();
            }
            System.out.print("Would you like to make a comment(c), delete a comment(d), edit a comment(e), view all comments(v), view all posts(p) or go back(b)? ");
            String ans = scan.nextLine();
            while (!(ans.equalsIgnoreCase("c") || ans.equalsIgnoreCase("b")
                    || ans.equalsIgnoreCase("d") || ans.equalsIgnoreCase("e")
                    || ans.equalsIgnoreCase("v") || ans.equalsIgnoreCase("p"))) {
                System.out.println("You must answer with c, d, e, v, p, or b.");
                System.out.print("Would you like to make a comment(c), delete a comment(d), edit a comment(e), view all comments(v), view all posts(p) or go back(b)? ");
                ans = scan.nextLine();
            }
            if (ans.equalsIgnoreCase("b")) {
                return;
            } else if (ans.equalsIgnoreCase("c")){
                System.out.print("Enter the title of the post you would like to make a comment to. ");
                String title = scan.nextLine();
                while (getPostIndex(title, a) == -1) {
                    System.out.print("Post with title " + title + " could not be found. Try again: ");
                    title = scan.nextLine();
                }
                System.out.println("What would you like to comment on this post.");
                String comment = scan.nextLine();
                int postIndex = getPostIndex(title, a);
                Comment c = new Comment(username, comment, a.getPosts().get(postIndex), commenting);
                commenting.makeComment(c);
                a.getPosts().get(postIndex).addComment(c);
                System.out.print("Comment was made. ");
            } else if (ans.equalsIgnoreCase("e")) {
                System.out.print("Enter the title of the post you would like to edit a comment on.");
                String title = scan.nextLine();
                while (getPostIndex(title, a) == -1) {
                    System.out.print("Post with title " + title + "could not be found. Try again: ");
                    title = scan.nextLine();
                }
                int postIndex = getPostIndex(title, a);
                System.out.println("Here are the comments made by you.");
                ArrayList<Comment> comments = displayComments(commenting, a.getPosts().get(postIndex));
                System.out.println("Enter the context of the comment you would like to edit.");
                String context = scan.nextLine();
                while (findComment(context, comments) == -1) {
                    System.out.println("This comment does not exist. Try again: ");
                    context = scan.nextLine();
                }
                int commentPostIndex = findComment(context, a.getPosts().get(postIndex).getComments());
                int commentAccountIndex = findComment(context, commenting.getComments());
                System.out.println("What would you like to change your comment to?");
                String changedContext = scan.nextLine();
                a.getPosts().get(postIndex).getComments().get(commentPostIndex).editComment(changedContext);
                commenting.getComments().get(commentAccountIndex).editComment(changedContext);
                System.out.println("Your change was made ");
            } else if (ans.equalsIgnoreCase("d")) {
                System.out.print("Enter the title of the post you would like to delete a comment on.");
                String title = scan.nextLine();
                while (getPostIndex(title, a) == -1) {
                    System.out.print("Post with title " + title + "could not be found. Try again: ");
                    title = scan.nextLine();
                }
                int postIndex = getPostIndex(title, a);
                System.out.println("Here are the comments made by you.");
                ArrayList<Comment> comments = displayComments(commenting, a.getPosts().get(postIndex));
                System.out.println("Enter the context of the comment you would like to delete.");
                String context = scan.nextLine();
                while (findComment(context, comments) == -1) {
                    System.out.println("This comment does not exist. Try again: ");
                    context = scan.nextLine();
                }
                int commentPostIndex = findComment(context, a.getPosts().get(postIndex).getComments());
                int commentAccountIndex = findComment(context, commenting.getComments());
                a.getPosts().get(postIndex).getComments().get(commentPostIndex).deleteComment();
                commenting.getComments().get(commentAccountIndex).deleteComment();
            } else if (ans.equalsIgnoreCase("v")) {
                a.displayComments();
            } else if (ans.equalsIgnoreCase("p")) {
                a.displayPosts();
            }
            System.out.print("Would you like to view more accounts(c) or go back(b)? ");
            String answer = scan.nextLine();
            while (!(answer.equalsIgnoreCase("c")) && !(answer.equalsIgnoreCase("b"))) {
                System.out.println("You must answer with \"c\" or \"b\".");
                System.out.print("Would you like to view more accounts(c) or go back(b)? ");
                answer = scan.nextLine();
            }
            if (answer.equalsIgnoreCase("b")) {
                return;
            } else {
                loop = true;
            }
        }
    }

    public static void editAccount(Scanner scan, String username) {
        Account a = usernameValidity(username);
        try {
            a.isLoggedIn();
        } catch (AccessException e) {
            System.out.print("You are not logged in.");
            return;
        }
        boolean loop = true;
        while (loop) {
            System.out.print("Would you like to create(c), edit(e), delete(d), import(i), or export(ex) a post? ");
            String ans = scan.nextLine();
            while (!(ans.equalsIgnoreCase("c") || ans.equalsIgnoreCase("e")
                    || ans.equalsIgnoreCase("d") || ans.equalsIgnoreCase("i")
                    || ans.equalsIgnoreCase("ex"))) {
                System.out.println("You must answer with c, e, d, i, or ex");
                System.out.print("Would you like to create(c), edit(e), delete(d), import(i), or export(ex) a post? ");
                ans = scan.nextLine();
            }
            if (ans.equalsIgnoreCase("c")) {
                System.out.print("What would you like your title to be? ");
                String title = scan.nextLine();
                System.out.print("Enter your post: ");
                String postContent = scan.nextLine();
                LocalDateTime now = LocalDateTime.now();
                Post addPost = new Post(title, username, postContent, a);
            } else if (ans.equalsIgnoreCase("e")) {
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
                System.out.print("Your change has been made. ");
            } else if (ans.equalsIgnoreCase("d")) {
                System.out.print("What is the title of the post you would like to delete? ");
                String title = scan.nextLine();
                while (getPostIndex(title, a) == -1) {
                    System.out.print("This title does not exist. Try again: ");
                    title = scan.nextLine();
                }
                int postIndex = getPostIndex(title, a);
                a.getPosts().get(postIndex).deletePost();
            } else if (ans.equalsIgnoreCase("i")) {
                System.out.print("What is the title of the post you would like to import? ");
                String title = scan.nextLine();
                while (getPostIndex(title, a) == -1) {
                    System.out.print("This title does not exist. Try again: ");
                    title = scan.nextLine();
                }
                try {
                    a.uploadPost(title + ".csv");
                } catch (FileNotFoundException e) {
                    System.out.println("We were unable to import this post.");
                }
            } else if (ans.equalsIgnoreCase("ex")) {
                System.out.print("What is the title of the post you would like to export? ");
                String title = scan.nextLine();
                while (getPostIndex(title, a) == -1) {
                    System.out.print("This title does not exist. Try again: ");
                    title = scan.nextLine();
                }
                int postIndex = getPostIndex(title, a);
                Post p = a.getPosts().get(postIndex);
                try {
                    p.exportPost(p);
                } catch (IOException e) {
                    System.out.print("We were not able to export this post");
                }
            }
            System.out.print("Would you like to edit your account(c) or go back(b)? ");
            String answer = scan.nextLine();
            while (!(answer.equalsIgnoreCase("c")) && !(answer.equalsIgnoreCase("b"))) {
                System.out.println("You must answer with \"c\" or \"b\".");
                System.out.print("Would you like to edit your account(c) or go back(b)? ");
                answer = scan.nextLine();
            }
            if (answer.equalsIgnoreCase("b")) {
                return;
            } else {
                loop = true;
            }
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

    public static int findComment(String context, ArrayList<Comment> comments) {
        for (int i = 0; i < comments.size(); i++) {
            if (comments.get(i).getText().equalsIgnoreCase(context)) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<Comment> displayComments(Account a, Post p) {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        for (int i = 0; i < a.getComments().size(); i++) {
            for (int j = 0; j < p.getComments().size(); j++) {
                if (a.getComments().get(i).equals(p.getComments().get(j))) {
                    comments.add(p.getComments().get(j));
                }
            }
        }
        return comments;
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


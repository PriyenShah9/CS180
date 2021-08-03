import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ApplicationServer {

    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private static ArrayList<Post> posts = new ArrayList<Post>();
    private static ArrayList<Comment> comments = new ArrayList<Comment>();
    private static String usernameAccountLoggedIn;

    //gatekeepers
    private static Object accountsGatekeeper = new Object();
    private static Object postsGatekeeper = new Object();
    private static Object commentsGatekeeper = new Object();

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(4244);
            Socket socket = ss.accept();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
                ReadData r = new ReadData("accounts.txt", "posts.txt", "comments.txt");
                try {
                    r.readAccounts();
                    r.readPosts();
                    r.readComments();
                } catch (IOException e) {
                    throw e;
                }
                accounts = r.accounts;
                posts = r.posts;
                comments = r.comments;
                postTransfer();
                commentTransfer();
                while (true) {
                    String firstAns = initialQuestion(pw, br);
                    if (firstAns.equals("3")) {
                        pw.println("Program Exit.");
                        pw.flush();
                        usernameAccountLoggedIn = null;
                        break;
                    }
                    while (true) {
                        if (firstAns.equals("1")) {
                            usernameAccountLoggedIn = login(pw, br);
                        } else if (firstAns.equals("2")) {
                            Account a = createAccount(pw, br);
                            accounts.add(a);
                            usernameAccountLoggedIn = a.getUsername();
                        }
                        String ans = nextQuestion(pw, br);
                        if (ans.equals("1")) {
                            editAccount(pw, br);
                        } else if (ans.equals("2")) {
                            viewPosts(pw, br);
                        } else if (ans.equals("3")) {
                            synchronized (accountsGatekeeper) {
                                for (int i = 0; i < accounts.size(); i++) {
                                    if (usernameAccountLoggedIn.equals(accounts.get(i).getUsername())) {
                                        accounts.remove(i);
                                    }
                                }
                            }
                            pw.println("Your account was deleted");
                            usernameAccountLoggedIn = null;
                            break;
                        } else if (ans.equals("4")) {
                            makeComment(pw, br);
                        } else if (ans.equals("5")) {
                            editComment(pw, br);
                        } else if (ans.equals("6")) {
                            viewComments(pw, br);
                        } else if (ans.equals("7")) {
                            importPost(pw, br);
                        } else if (ans.equals("8")) {
                            exportPost(pw, br);
                        } else {
                            Account a = usernameValidity(usernameAccountLoggedIn);
                            a.logOut();
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
            } catch (IOException e) {
                throw e;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String initialQuestion(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Would you like to:" +
                "\n1. Log in" +
                "\n2. Create an Account" +
                "\n3. Exit the program ");
        printWriter.flush();
        String ans = bufferedReader.readLine();
        while (!(ans.equals("1")) && !(ans.equals("2")) && !(ans.equals("3"))) {
            printWriter.println("You must answer with either 1, 2, or 3." +
                    "\nWould you like to:" +
                    "\n1. Log in" +
                    "\n2. Create an Account" +
                    "\n3. Exit the program ");
            printWriter.flush();
            ans = bufferedReader.readLine();
        }
        return ans;
    }


    public static String nextQuestion(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Would you like to:" +
                "\n1. Edit your account." +
                "\n2. View all of a user's posts." +
                "\n3. Delete account." +
                "\n4. Make a comment." +
                "\n5. Edit/Delete a comment." +
                "\n6. View all of a user's comments." +
                "\n7. Import a post." +
                "\n8. Export a post." +
                "\n9. Log out. ");
        printWriter.flush();
        String ans = bufferedReader.readLine();
        while (!(ans.equals("1")) && !(ans.equals("2")) && !(ans.equals("3")) && !(ans.equals("4")) &&
                !(ans.equals("5")) && !(ans.equals("6")) && !(ans.equals("7")) && !(ans.equals("8"))
                && !ans.equals("9")) {
            printWriter.println("You must answer with either 1, 2, 3, 4, 5, 6, 7, 8, or 9." +
                    " Would you like to:" +
                    "\n1. Edit your account." +
                    "\n2. View all of a user's posts." +
                    "\n3. Delete account." +
                    "\n4. Make a comment." +
                    "\n5. Edit/Delete a comment." +
                    "\n6. View all of a user's comments." +
                    "\n7. Import a post." +
                    "\n8. Export a post." +
                    "\n9. Log out. ");
            printWriter.flush();
            ans = bufferedReader.readLine();
        }
        return ans;
    }

    public static Account createAccount(PrintWriter printWriter, BufferedReader bufferedReader)  throws IOException {
        printWriter.println("What is your name? ");
        printWriter.flush();
        String name = bufferedReader.readLine();
        printWriter.println("What would you like your username to be? ");
        printWriter.flush();
        String username = bufferedReader.readLine();
        while (usernameValidity(username) != null) {
            printWriter.print("This username is taken." +
                    "\nTry again: ");
            printWriter.flush();
            username = bufferedReader.readLine();
        }
        printWriter.println("What would you like your password to be? ");
        printWriter.flush();
        String password = bufferedReader.readLine();
        printWriter.println("Re-enter your password: ");
        printWriter.flush();
        String reenteredPassword = bufferedReader.readLine();
        while (!password.equals(reenteredPassword)) {
            printWriter.print("That is incorrect." +
                    "\nTry again: ");
            printWriter.flush();
            reenteredPassword = bufferedReader.readLine();
        }
        return new Account(name, username, password, true);
    }


    public static String login(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Username: ");
        printWriter.flush();
        String username = bufferedReader.readLine();
        while(usernameValidity(username) == null) {
            printWriter.println("Username does not exist." +
                    "\nTry again: ");
            printWriter.flush();
            username = bufferedReader.readLine();
        }
        Account a = usernameValidity(username);
        printWriter.println("Password: ");
        printWriter.flush();
        String password = bufferedReader.readLine();
        while (!password.equals(a.getPassword())) {
            printWriter.println("Password is invalid." +
                    "\nTry again: ");
            printWriter.flush();
            password = bufferedReader.readLine();
        }
        a.logIn();
        return username;
    }

    public static void editAccount(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        Account a = usernameValidity(usernameAccountLoggedIn);
        printWriter.println("Would you like to:" +
                "\n1. Make a post." +
                "\n2. Edit a post." +
                "\n3. Delete a post. ");
        printWriter.flush();
        String ans = bufferedReader.readLine();
        while (!(ans.equals("1")) && !(ans.equals("2")) && !(ans.equals("3"))) {
            printWriter.print("You must reply with 1,2, or 3.");
            printWriter.println(" Would you like to:" +
                    "\n1. Make a post." +
                    "\n2. Edit a post." +
                    "\n3. Delete a post. ");
            printWriter.flush();
            ans = bufferedReader.readLine();
        }
        if (ans.equalsIgnoreCase("1")) {
            printWriter.println("What would you like your title to be? ");
            printWriter.flush();
            String title = bufferedReader.readLine();
            while (getPostIndex(title, a) != -1) {
                printWriter.println("This title already exists." +
                        "\nWhat would you like your title to be? ");
                printWriter.flush();
                title = bufferedReader.readLine();
            }
            printWriter.println("Enter your post: ");
            printWriter.flush();
            String postContent = bufferedReader.readLine();
            Post post = new Post(title, a.getUsername(), postContent);
            synchronized (a) {
                a.addPost(post);
            }
            printWriter.println("Your post has been made.");
            printWriter.flush();
        } else if (ans.equalsIgnoreCase("2")) {
            printWriter.println("What is the title of the post that you would like to edit: ");
            printWriter.flush();
            String title = bufferedReader.readLine();
            while (getPostIndex(title, a) == -1) {
                printWriter.println("This title does not exist." +
                        "\nTry again: ");
                printWriter.flush();
                title = bufferedReader.readLine();
            }
            int postIndex = getPostIndex(title, a);
            printWriter.println("What would you like to change your post to? ");
            printWriter.flush();
            String changedContext = bufferedReader.readLine();
            synchronized (a) { //cannot edit while parsing through to display
                a.editPost(a.getPosts().get(postIndex), changedContext);
            }
            printWriter.println("Your edit was made.");
            printWriter.flush();
        } else if (ans.equalsIgnoreCase("3")) {
            printWriter.println("What is the title of the post you would like to delete? ");
            printWriter.flush();
            String title = bufferedReader.readLine();
            while (getPostIndex(title, a) == -1) {
                printWriter.println("This title does not exist." +
                        "\nTry again: ");
                printWriter.flush();
                title = bufferedReader.readLine();
            }
            int postIndex = getPostIndex(title, a);
            synchronized (a) {
                a.getPosts().remove(postIndex);
            }
            printWriter.println("Your post was deleted.");
            printWriter.flush();
        }
    }

    public static void viewPosts(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Enter the username of the account you would like to view: ");
        printWriter.flush();
        String username = bufferedReader.readLine();
        while (usernameValidity(username) == null) {
            printWriter.print("This username does not exist." +
                    "\nTry again: ");
            printWriter.flush();
            username = bufferedReader.readLine();
        }
        Account account = usernameValidity(username);
        if (account.getPosts().size() == 0) {
            printWriter.println("This user has no posts.");
            printWriter.flush();
        } else {
            synchronized (account) {
                printWriter.println(account.displayPosts());
            }
            printWriter.flush();
        }
    }

    public static void viewComments(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Enter the username of the account you would like to view: ");
        printWriter.flush();
        String username = bufferedReader.readLine();
        while (usernameValidity(username) == null) {
            printWriter.print("This username does not exist." +
                    "\nTry again: ");
            printWriter.flush();
            username = bufferedReader.readLine();
        }
        Account account = usernameValidity(username);
        if (account.getComments().size() == 0) {
            printWriter.println("This user has made no comments.");
            printWriter.flush();
        } else {
            synchronized (account) {
                printWriter.println(account.displayComments());
            }
            printWriter.flush();
        }
    }

    public static void makeComment(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Enter the author of the post you would like to comment on: ");
        printWriter.flush();
        String username = bufferedReader.readLine();
        while (usernameValidity(username) == null) {
            printWriter.print("This username does not exist." +
                    "\nTry again: ");
            printWriter.flush();
            username = bufferedReader.readLine();
        }
        Account account = usernameValidity(username);
        printWriter.println("Enter the title of the post you would like to comment on: ");
        printWriter.flush();
        String title = bufferedReader.readLine();
        while (getPostIndex(title, account) == -1) {
            printWriter.print("This title does not exist." +
                    "\nTry again: ");
            title = bufferedReader.readLine();
        }
        int postIndex = getPostIndex(title, account);
        printWriter.println("What would you like to comment on this post? ");
        printWriter.flush();
        String ans = bufferedReader.readLine();
        Comment comment = new Comment(usernameAccountLoggedIn, ans, title);
        Account a = usernameValidity(usernameAccountLoggedIn);
        synchronized (a) { //same object as account in viewComments
            a.addComment(comment);
        }
        account.makeComment(comment, postIndex);
    }

    public static void editComment(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Enter the author of the post you would like to edit/delete your comment on: ");
        printWriter.flush();
        String username = bufferedReader.readLine();
        while (usernameValidity(username) == null) {
            printWriter.print("This username does not exist." +
                    "\nTry again: ");
            username = bufferedReader.readLine();
        }
        Account account = usernameValidity(username);
        printWriter.println("Enter the title of the post you would like to edit/delete your comment on: ");
        printWriter.flush();
        String title = bufferedReader.readLine();
        while (getPostIndex(title, account) == -1) {
            printWriter.print("This title does not exist." +
                    "\nTry again: ");
            printWriter.flush();
            title = bufferedReader.readLine();
        }
        int postIndex = getPostIndex(title, account);
        printWriter.println("Enter the comment you would like to edit/delete: ");
        printWriter.flush();
        String text = bufferedReader.readLine();
        Account a = usernameValidity(usernameAccountLoggedIn);
        while (findComment(text, account.getPosts().get(postIndex).getComments()) == -1
                || findComment(text, a.getComments()) == -1) {
            printWriter.print("This comment does not exist." +
                    "\nTry again: ");
            printWriter.flush();
            text = bufferedReader.readLine();
        }
        int commentIndexAccount = findComment(text, a.getComments());
        int commentIndexPost = findComment(text, account.getPosts().get(postIndex).getComments());
        printWriter.println("Would you like to:" +
                "\n1. Edit the comment." +
                "\n2. Delete the comment. ");
        printWriter.flush();
        String ans = bufferedReader.readLine();
        while (!(ans.equals("1")) && !(ans.equals("2"))) {
            printWriter.println("You must reply with 1 or 2.");
            printWriter.println("Would you like to:" +
                    "\n1. Edit the comment." +
                    "\n2. Delete the comment. ");
            printWriter.flush();
            ans = bufferedReader.readLine();
        }
        if (ans.equals("1")) {
            printWriter.println("What would you like to change your comment to: ");
            printWriter.flush();
            String changedText = bufferedReader.readLine();
            account.editCommentPost(commentIndexPost, postIndex, changedText);
            synchronized (a) { //cannot change text while viewComment is parsing through all comments
                a.editComment(commentIndexAccount, changedText);
            }
        } else {
            account.deleteCommentPost( commentIndexPost, postIndex);
            synchronized (a) {
                a.deleteComment(commentIndexAccount);
            }
        }
        printWriter.println("Your changes were made.");
    }

    public static void importPost(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException{
        printWriter.println("Enter the title of the post you would like to import: ");
        printWriter.flush();
        String ans = bufferedReader.readLine();
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
                synchronized (account) {
                    account.addPost(post);
                }
                posts.add(post);
                System.out.println("The post was added to your account.");
            }
        } catch(FileNotFoundException e) {
            printWriter.println("This file does not exist.");
            printWriter.flush();
        } catch(IOException e) {
            printWriter.println("The information in the file is invalid.");
            printWriter.flush();
        }
    }

    public static void exportPost(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
        printWriter.println("Enter the title of the post you would like to export: ");
        printWriter.flush();
        String ans = bufferedReader.readLine();
        while (findPost(ans) == -1) {
            printWriter.println("This title does not exist.");
            printWriter.println("Try again: ");
            printWriter.flush();
            ans = bufferedReader.readLine();
        }
        int postIndex = findPost(ans);
        Post post;
        synchronized (postsGatekeeper) {
            post = posts.get(postIndex);
        }
        File f = new File(post.getTitle() + ".csv");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(f, false))) {
            pw.print(post.getTitle().replaceAll(" ", "_")
                    + " " + post.getAuthorName().replaceAll(" ", "_")
                    + " " + post.getText().replaceAll(" ", "_") +
                    " " + post.getTimeStamp().replaceAll(" ", "_"));
        } catch (FileNotFoundException e) {
            printWriter.println("An error occurred.");
            printWriter.flush();
        }
    }

    public static void postTransfer() {
        for (Post p : posts) {
            for (int i = 0; i < accounts.size(); i++) {
                if (p.getAuthorName().equals(accounts.get(i).getUsername())) {
                    Account a = accounts.remove(i);
                    a.addPost(p);
                    accounts.add(i, a);
                }
            }
        }
    }

    public static void commentTransfer() {
        for (Comment c : comments) {
            for (int i = 0; i < accounts.size(); i++) {
                if (c.getAuthorName().equals(accounts.get(i).getUsername())) {
                    Account a = accounts.remove(i);
                    a.addComment(c);
                    accounts.add(i, a);
                }
            }
            for (int i = 0; i < accounts.size(); i++) {
                for (int j = 0; j < accounts.get(i).getPosts().size(); j++) {
                    if (accounts.get(i).getPosts().get(j).getTitle().equals(c.getPostTitle())) {
                        Account a = accounts.remove(i);
                        a.makeComment(c, j);
                        accounts.add(i, a);
                    }
                }
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

    public static int findPost(String title) {
        for (int i = 0; i < posts.size(); i++) {
            if (title.equalsIgnoreCase(posts.get(i).getTitle())) {
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


    public static Account usernameValidity(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (username.equals(accounts.get(i).getUsername())) {
                return accounts.get(i);
            }
        }
        return null;
    }
}


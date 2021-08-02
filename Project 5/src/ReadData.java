import java.io.*;
import java.util.ArrayList;

public class ReadData {

    public static ArrayList<Account> accounts = new ArrayList<>();
    public static ArrayList<Post> posts = new ArrayList<>();
    public static ArrayList<Comment> comments = new ArrayList<>();
    private static String accountsFileName;
    private static String postsFileName;
    private static String commentsFileName;

    public ReadData(String accountsFileName, String postsFileName, String commentsFileName) {
        this.accountsFileName = accountsFileName;
        this.postsFileName = postsFileName;
        this.commentsFileName = commentsFileName;
    }

    public static void writeAccountInformation() throws IOException{
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(accountsFileName, false))) {

            for (Account a : accounts) {
                String accountName = a.getName().replaceAll(" ", "_");
                String accountUsername = a.getUsername().replaceAll(" ", "_");
                String accountPassword = a.getPassword().replaceAll(" ", "_");
                boolean accountLoggedIn = a.isLoggedIn();
                pw.println(accountName + " " + accountUsername + " " + accountPassword + " " + accountLoggedIn);
            }
        }
    }

    public static void writePostInformation() throws IOException{
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(postsFileName, false))) {

            for (Account a : accounts) {
                for (Post p : a.getPosts()) {
                    String postTitle = p.getTitle().replaceAll(" ", "_");
                    String postAuthor = p.getAuthorName().replaceAll(" ", "_");
                    String postText = p.getText().replaceAll(" ", "_");
                    String postTimestamp = p.getTimeStamp().replaceAll(" ", "_");
                    pw.println(postTitle + " " + postAuthor + " " + postText + " " + postTimestamp);
                }
            }
        }
    }

    public static void writeCommentInformation() throws IOException{
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(commentsFileName, false))) {
            for (Account a : accounts) {
                for (Comment c : a.getComments()) {
                    String commentAuthor = c.getAuthorName().replaceAll(" ", "_");
                    String commentText = c.getText().replaceAll(" ", "_");
                    String commentTimestamp = c.getTimestamp().replaceAll(" ", "_");
                    String postTitle = c.getPostTitle().replaceAll(" ", "_");
                    pw.println(commentAuthor + " " + commentText + " " + postTitle + " " + commentTimestamp);
                }
            }
        }
    }

    public static void readAccounts() throws IOException{
        File f = new File(accountsFileName);
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] accountInformation = line.split(" ");
                String accountName = accountInformation[0].replaceAll("_", " ");
                String accountUsername = accountInformation[1].replaceAll("_", " ");
                String accountPassword = accountInformation[2].replaceAll("_", " ");
                boolean loggedIn = Boolean.parseBoolean(accountInformation[3]);
                Account a = new Account(accountName, accountUsername, accountPassword, loggedIn);
                accounts.add(a);
            }
        }
    }

    public static void readPosts() throws IOException{
        File f = new File(postsFileName);
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] postInformation = line.split(" ");
                String postTitle = postInformation[0].replaceAll("_", " ");
                String postAuthor = postInformation[1].replaceAll("_", " ");
                String postText = postInformation[2].replaceAll("_", " ");
                String postTimestamp = postInformation[3].replaceAll("_", " ");
                Post p = new Post(postTitle, postAuthor, postText, postTimestamp);
                posts.add(p);
            }
        }
    }

    public static void readComments() throws IOException{
        File f = new File(commentsFileName);
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = bfr.readLine()) != null) {
                String[] commentInformation = line.split(" ");
                String commentAuthor = commentInformation[0].replaceAll("_", " ");
                String commentText = commentInformation[1].replaceAll("_", " ");
                String commentPost = commentInformation[2].replaceAll("_", " ");
                String commentTimestamp = commentInformation[2].replaceAll("_", " ");
                Comment c = new Comment(commentAuthor, commentText, commentPost, commentTimestamp);
                comments.add(c);
            }
        }
    }

}

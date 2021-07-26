import java.io.*;
import java.util.ArrayList;

// test GitHub test

public class ReadData {

    public static ArrayList<Account> accounts = new ArrayList<Account>();
    public static ArrayList<Post> posts = new ArrayList<Post>();
    public static ArrayList<Comment> comments = new ArrayList<Comment>();
    private static String fileName;

    public ReadData(String fileName) {
        this.fileName = fileName;
    }

    public static void read() throws FileNotFoundException {
        File input = new File(fileName);
        FileReader fr = new FileReader(input);
        try (BufferedReader bfr = new BufferedReader(fr)) {
            while (true) {
                String line = bfr.readLine();
                String postLine = bfr.readLine();
                if (line == null)
                    break;
                if (accounts == null)
                    accounts = new ArrayList<>();
                if (posts == null)
                    posts = new ArrayList<>();
                if (comments == null)
                    comments = new ArrayList<>();
                createInformation(line, postLine);

            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createInformation(String line, String postLine) {
        String[] splitLine = line.split(" ");
        String name = splitLine[0].replaceAll("_", " ");
        String username = splitLine[1];
        String password = splitLine[2];
        Account newAccount = new Account(name, username, password);
        accounts.add(newAccount);

        if (postLine != null && postLine.length() != 0) {
            String[] postInformation = postLine.split("\\$");

            for (int w = 1; w < postInformation.length; w++) { //start 1 b/c first element is ""
                String[] splitPosts = postInformation[w].split(" ");
                String postTitle = splitPosts[0].replaceAll("_", " ");
                String text = splitPosts[1].replaceAll("_", " ");
                String timeStamp = splitPosts[2];
                Comment newComment = null;
                Post newPost;
                ArrayList<Comment> postComments = new ArrayList<Comment>();

                newPost = new Post(postTitle, newAccount.getUsername(), text, newAccount, timeStamp, postComments);
                newAccount.addPost(newPost);

                if (splitPosts.length > 3) {
                    for (int i = 3; i < splitPosts.length; i++) {
                        String[] commentData = splitPosts[i].split(";");
                        Account dummy = new Account(null, commentData[1], null);
                        newComment = new Comment(commentData[1], commentData[0].replaceAll("_", " "), newPost, dummy, commentData[2]);
                        comments.add(newComment);
                    }
                }

                posts.add(newPost);
            }

            //add comments to account array of commentsMade
            for (int t = 0; t < accounts.size(); t++) {
                for (int u = 0; u < comments.size(); u++) {
                    if (comments.get(u) != null && comments.get(u).getAuthorName().equals(accounts.get(t).getUsername())) {
                        comments.get(u).setAccount(accounts.get(t));
                        accounts.get(t).makeComment(comments.get(u));
                    }
                }
            }
            return;
        }
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static ArrayList<Comment> getComments() {
        return comments;
    }

    public static ArrayList<Post> getPosts() {
        return posts;
    }

    public static void writeChangesToFile(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new FileOutputStream(fileName, false));

        for (int i = 0; i < accounts.size(); i++) {
            String accountName = accounts.get(i).getName().replaceAll(" ", "_");
            String accountUsername = accounts.get(i).getUsername().replaceAll(" ", "_");
            String accountPassword = accounts.get(i).getPassword();
            pw.println(accountName + " " + accountUsername + " " + accountPassword);
            ArrayList<Post> accountPosts = accounts.get(i).getPosts();
            for (int j = 0; j < accountPosts.size(); j++) {
                String postTitle = accountPosts.get(j).getTitle().replaceAll(" ", "_");
                String postText = accountPosts.get(j).getText().replaceAll(" ", "_");
                String postTimeStamp = accountPosts.get(j).getTimeStamp();
                pw.print("$" + postTitle + " " + postText + " " + postTimeStamp + " ");
                ArrayList<Comment> postComments = accountPosts.get(j).getComments();
                for (int w = 0; w < postComments.size(); w++) {
                    String comment = postComments.get(w).getText().replaceAll(" ", "_");
                    String author = postComments.get(w).getPost().getAuthorUsername().replaceAll(" ", "_");
                    String commentTimeStamp = postComments.get(w).getTimestamp();
                    pw.print(comment + ";" + author + ";" + commentTimeStamp + " ");
                }
            }
            pw.println();
        }
        pw.close();
    }
}

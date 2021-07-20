import java.io.*;
import java.util.ArrayList;

//USER: new User(String name, String username, String password, Boolean isLoggedIn, ArrayList<Posts>())
//POST: new Post(String postTitle, Account account, String authorName, String caption, String mm:dd:hh:mm:ss, ArrayList<Comment>());
//COMMENT: new Comment(String comment, Post post, String author);

public class ReadData {

    public static ArrayList<Account> accounts = new ArrayList<Account>();
    public static ArrayList<Post> posts = new ArrayList<Post>();
    public static ArrayList<Comment> comments = new ArrayList<Comment>();
    private String fileName;

    public ReadData(String fileName) throws FileNotFoundException, AccountException, PostException, CommentException, AccessException {
        this.fileName = fileName;
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

    private static void createInformation(String line, String postLine) throws PostException, AccountException, CommentException, AccessException{
        String[] splitLine = line.split(" ");
        String name = splitLine[0];
        String username = splitLine[1];
        String password = splitLine[2];
        String isLoggedIn = splitLine[3];
        boolean loggedIn = false;
        String[] postInformation = postLine.split("$");
        if (isLoggedIn.equalsIgnoreCase("True")) {
            loggedIn = true;
        }

        for (int w = 0; w < postInformation.length; w++) {
            String[] splitPosts = postInformation[w].split(" ");
            String postTitle = splitPosts[0];
            String caption = splitPosts[1];
            String timeStamp = splitPosts[2];
            Comment newComment;
            Post newPost;
            Account newAccount;
            ArrayList<Post> userPosts = new ArrayList<Post>();
            ArrayList<Comment> postComments = new ArrayList<Comment>();

            newAccount = new Account(name, username, password, isLoggedIn, userPosts);
            newPost = new Post(postTitle, newAccount, name, caption, timeStamp, postComments);

            if (splitPosts.length > 3) {
                for (int i = 3; i < splitPosts.length; i++) {
                    String[] commentData = splitPosts[i].split(";");
                    newComment = new Comment(commentData[0], newPost, commentData[1]);
                    comments.add(newComment);
                }
            }

            if (newAccount.equals(newPost.getAccount())) {
                newAccount.addPost(newPost);
            }
            for (int t = 0; t < comments.size(); t++) {
                if (newPost.equals(comments.get(t).getPost())) {
                    newPost.addComment(comments.get(t));
                }
            }
            accounts.add(newAccount);
            posts.add(newPost);

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

    public void writeChangesToFile() throws FileNotFoundException {
        File output = new File(fileName);
        FileOutputStream fos = new FileOutputStream(output);
        PrintWriter pw = new PrintWriter(fos);

        for (int i = 0; i < accounts.size(); i++) {
            String accountName = accounts.get(i).getName().replaceAll(" ", "_");
            String accountUsername = accounts.get(i).getUsername().replaceAll(" ", "_");
            String accountPassword = accounts.get(i).getPassword();
            String accountLoggedIn = String.valueOf(accounts.get(i).getLoggedIn());
            pw.println(accountName + " " + accountUsername + " " + accountPassword + " " + accountLoggedIn);
            Post[] accountPosts = accounts.get(i).getPosts();
            for (int j = 0; j < accountPosts.length; j++) {
                String postTitle = accountPosts[j].getTitle();
                String postCaption = accountPosts[j].getCaption();
                String postTimeStamp = accountPosts[j].getTimeStamp();
                pw.print("$" + postTitle + " " + postCaption + " " + postTimeStamp + " ");
                Comment[] postComments = accountPosts[j].getComments();
                for (int w = 0; w < postComments.length; w++) {
                    String comment = postComments[w].getComment();
                    String author = postComments[w].getAuthor();
                    pw.print(comment + ";" + author + " ");

                }

            }
            pw.println();

        }

        pw.close();
    }
}
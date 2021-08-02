import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
/**
 * Project 4 - Account
 * <p>
 * The Account class stores information from the account
 * such as posts and comments made on other posts. Access
 * is granted by a password and every account has a unique
 * username.
 *
 * @author Team #002, Section Y01
 * @version July 21, 2021
 *
 */
public class Account {
    private String username;
    private String password;
    private String name;
    private ArrayList<Post> posts = new ArrayList<Post>();
    private ArrayList<Comment> commentsMade = new ArrayList<Comment>();
    private boolean loggedIn = false; //by default not logged in

    /**
     * Construct an Account - brand new
     *
     * @param name: name
     * @param username: unique username
     * @param password: password to allow access
     * @param loggedIn: whether logged in or not
     */
    public Account(String name, String username, String password, boolean loggedIn) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.loggedIn = loggedIn;
    }

    /**
     * Construct an Account - loading in from previous runs of the program
     *
     * @param name: name
     * @param username: unique username
     * @param password: password to allow access
     */
    public Account(String name, String username, String password) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.posts = posts;
    }

    /**
     * Check if logged in
     *
     * @throws AccessException: when not logged in
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * log user in
     **/
    public void logIn() {
        loggedIn = true;
    }

    /**
     * Log out
     */
    public void logOut() {
        loggedIn = false;
    }

    /**
     * get username
     *
     * @return: username
     */
    public String getUsername() {
        return username;
    }

    /**
     * change username
     *
     * @param username: new username
     */
    public void changeUsername(String username) {
        this.username = username;
    }

    /**
     * get password
     *
     * @return: password
     */
    public String getPassword() {
        return password;
    }

    /**
     * change password
     *
     * @param password: new passowrd
     */
    public void changePassword(String password) {
        this.password = password;
    }

    /**
     * get name
     *
     * @return: name
     */
    public String getName() {
        return name;
    }

    /**
     * change name
     *
     * @param name: new name
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * add a post
     *
     * @param post: post to be added
     */
    public void addPost(Post post) {
        posts.add(post);
    }

    /**
     * display posts
     */
    public String displayPosts() {
        String append = "";
        for (Post i : posts) {
            append += i.displayPost() + "\n";
            append += "\n";
        }
        return append;
    }

    /**
     * returns all posts
     *
     * @return: posts arraylist
     */
    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**
     * upload post from csv - csv must be formatted
     * as title, authorname, text, timestamp
     *
     * @param filename: csv file
     */
    public void uploadPost(String filename) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.useDelimiter(",");
            String[] p1 = {};
            int i = 0;
            while (sc.hasNext()) {
                p1[i++] = sc.next();
            }
            String title = p1[0];
            String authorName = p1[1];
            String text = p1[2];

            Post post = new Post(title, authorName, text);
            this.addPost(post);
            sc.close();

        } catch (FileNotFoundException e) {
            FileNotFoundException fe = new FileNotFoundException("File does not exist");
            throw fe;
        }
    }

    /**
     * edit the text of a post
     *
     * @param post: post to be edited
     * @param text: new text
     */
    public void editPost(Post post, String text) {
        post.editText(text);
    }

    /**
     * get number of posts
     *
     * @return: number of posts
     */
    public int getNumPosts() {
        return posts.size();
    }

    /**
     * delete a post
     *
     * @param post: post to be deleted
     */
    public void deletePost(Post post) {
        posts.remove(post);
        post.deletePost();
    }

    /**
     * get all comments made
     *
     * @return: comments
     */
    public ArrayList<Comment> getComments() {
        return commentsMade;
    }

    /**
     * display all comments made
     */
    public String displayComments() {
        String append = "";
        for (Comment i : commentsMade) {
            append += i.displayComment() + "\n";
            append += "\n";
        }
        return append;
    }

    public void addComment(Comment comment) {
        commentsMade.add(comment);
    }

    /**
     * make a comment
     *
     * @param comment: the comment
     */
    public void makeComment(Comment comment, int postIndex) {
        Post p = posts.remove(postIndex);
        p.addComment(comment);
        posts.add(postIndex, p);
    }

    /**
     * edit the text of a comment
     *
     *
     * @param text: text of a comment
     */
    public void editCommentPost(int commentIndexPost, int postIndex, String text) {
        Post p = posts.get(postIndex);
        p.editComment(commentIndexPost, text);
    }

    public void editComment(int commentIndexAccount, String text) {
        Comment c = commentsMade.remove(commentIndexAccount);
        c.editComment(text);
        commentsMade.add(commentIndexAccount, c);
    }

    /**
     * delete a comment
     *
     *
     */
    public void deleteCommentPost( int commentIndexPost, int postIndex) {
        Post p = posts.get(postIndex);
        p.deleteComment(commentIndexPost);
        posts.add(postIndex, p);
    }

    public void deleteComment(int commentIndexAccount) {
        commentsMade.remove(commentIndexAccount);
    }
    /**
     * delete account by setting fields to null
     */
    public void deleteAccount() {
        this.username = null;
        this.name = null;
        this.password = null;
        this.posts = null;
        this.commentsMade = null;
        this.loggedIn = false;
    }
}

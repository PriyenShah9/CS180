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
    private ArrayList<Post> posts;
    private ArrayList<Comment> commentsMade;
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
     * @param posts: all posts made
     * @param comments: all comments made
     */
    public Account(String name, String username, String password, ArrayList<Post> posts, ArrayList<Comment> comments) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.posts = posts;
        this.commentsMade = comments;
    }

    /**
     * Check if logged in
     *
     * @throws AccessException: when not logged in
     */
    private void isLoggedIn() throws AccessException {
        if (!loggedIn) {
            throw new AccessException("You are not logged in to this account!");
        }
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
    public String getPassword(String password) {
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
    public void displayPosts() {
        for (Post i : posts) {
            i.displayPost();
        }
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
            String timestamp = p1[3];

            Post post = new Post(title, authorName, text, this, timestamp);
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
    public void displayComments() {
        for (Comment i : commentsMade) {
            i.displayComment();
        }
    }

    /**
     * make a comment
     *
     * @param comment: the comment
     */
    public void makeComment(Comment comment) {
        commentsMade.add(comment);
    }

    /**
     * edit the text of a comment
     *
     * @param comment: comment to change
     * @param text: text of a comment
     */
    public void editComment(Comment comment, String text) {
        comment.editComment(text);
    }

    /**
     * delete a comment
     *
     * @param comment: comment to delete
     */
    public void deleteComment(Comment comment) {
        comment.getPost().deleteComment(comment);
        commentsMade.remove(comment);
        comment.deleteComment();
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

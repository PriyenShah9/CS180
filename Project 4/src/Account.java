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
     *
     * @throws AccessException: when not logged in - must be logged in to log out
     */
    public void logOut() throws AccessException {
        if (!loggedIn) {
            throw new AccessException("You are already logged out!");
        }
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
     * @throws AccessException: when not logged in
     */
    public void changeUsername(String username) throws AccessException {
        this.isLoggedIn();

        this.username = username;
    }

    /**
     * change password
     *
     * @param password: new passowrd
     * @throws AccessException: when not logged in
     */
    public void changePassword(String password) throws AccessException{
        this.isLoggedIn();

        this.password = password;
    }

    /**
     * change name
     *
     * @param name: new name
     * @throws AccessException: when not logged in
     */
    public void changeName(String name) throws AccessException{
        this.isLoggedIn();

        this.name = name;
    }

    /**
     * add a post
     *
     * @param post: post to be added
     * @throws AccessException: when not logged in
     * @throws PostException: when a post with this name has already been made
     */
    public void addPost(Post post) throws PostException, AccessException {
        this.isLoggedIn();

        if (posts != null && posts.size() > 0) {
            for (Post i : posts) {
                if (i.getTitle().equals(post.getTitle())) {
                    throw new PostException("You already made a post with this title!");
                }
            }
        }
        posts.add(post);
    }

    /**
     * display posts
     *
     * @throws PostException: when post does not exist
     */
    public void displayPosts() throws PostException {
        for (Post i : posts) {
            i.displayPost();
        }
    }

    /**
     * upload post from csv - csv must be formatted
     * as title, authorname, text, timestamp
     *
     * @param filename: csv file
     * @throws AccessException: when not logged in
     * @throws PostException: when a post with this title already exists
     */
    public void uploadPost(String filename) throws AccessException, PostException {
        this.isLoggedIn();

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
            AccessException a = new AccessException(String.format("File does not exist"));
            throw a;
        } catch (PostException e) {
            throw e;
        }
    }

    /**
     * edit the text of a post
     *
     * @param post: post to be edited
     * @param text: new text
     * @throws AccessException: when not logged in, or when not the creator of the post
     */
    public void editPost(Post post, String text) throws AccessException {
        this.isLoggedIn();
        if (!post.getAccount().equals(this)) {
            throw new AccessException("You are not the creator of this post!");
        }

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
     * @throws AccessException: when not logged in, or when not the author who made the post
     */
    public void deletePost(Post post) throws PostException, AccessException {
        this.isLoggedIn();
        if (!post.getAccount().equals(this)) {
            throw new AccessException("You are not the creator of this post!");
        }

        if (!posts.contains(post)) {
            throw new PostException("This post does not exist!");
        }
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
     * display all commments
     *
     * @throws CommentException: when comment doesn't exist
     * @throws PostException: when post the comment is made on doesn't exist
     */
    public void displayComments() throws CommentException, PostException {
        for (Comment i : commentsMade) {
            i.displayComment();
        }
    }

    /**
     * make a comment
     *
     * @param comment: the comment
     * @throws AccessException: when not logged in
     */
    public void makeComment(Comment comment) throws AccessException {
        this.isLoggedIn();
        commentsMade.add(comment);
    }

    /**
     * edit the text of a comment
     *
     * @param comment: comment to change
     * @param text: text of a comment
     * @throws AccessException: when not logged in or not the author fo the comment
     * @throws CommentException: when comment does not exit
     */
    public void editComment(Comment comment, String text) throws AccessException, CommentException {
        this.isLoggedIn();
        if (!comment.getAccount().equals(this)) {
            throw new AccessException("You are not the creator of this post!");
        }

        if (!commentsMade.contains(comment)) {
            throw new CommentException("Comment does not exist!");
        }

        comment.editComment(text);
    }

    /**
     * delete a comment
     *
     * @param comment: comment to delete
     * @throws AccessException: when not logged in or not the author of the comment
     * @throws AccountException: when comment does not exist
     */
    public void deleteComment(Comment comment) throws AccessException, AccountException {
        this.isLoggedIn();
        if (!comment.getAccount().equals(this)) {
            throw new AccessException("You are not the creator of this comment!");
        }

        if (!commentsMade.contains(comment)) {
            throw new AccountException("Comment does not exist!");
        }
        comment.getPost().deleteComment(comment);
        commentsMade.remove(comment);
        comment.deleteComment();
    }

    /**
     * delete account by setting fields to null
     *
     * @throws AccessException: when not logged in
     */
    public void deleteAccount() throws AccessException {
        this.isLoggedIn();

        this.username = null;
        this.name = null;
        this.password = null;
        this.posts = null;
        this.commentsMade = null;
        this.loggedIn = false;
    }
}

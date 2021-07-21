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
 * @author Crystal Jiang, Section Y01
 * @version July 19, 2021
 *
 */
public class Account {
    private String username;
    private String password;
    private String name;
    private ArrayList<Post> posts;
    private ArrayList<Comment> commentsMade;
    private boolean loggedIn = false; //by default not logged in

    //create an account
    public Account(String name, String username, String password, ArrayList<Account> accounts, boolean loggedIn) throws AccountException {

        this.username = username;
        this.password = password;
        this.name = name;
        this.loggedIn = loggedIn;
    }

    public Account(String filename) throws AccountException { //to be loaded if it existed previously
        try (Scanner scan = new Scanner(new File(filename))) {
            //details later
        } catch (FileNotFoundException e) {
            AccountException ae = new AccountException(String.format("Account with %s does not exist!", username));
            throw ae;
        }
    }

    // checks if user is logged into the account and can have access
    private void isLoggedIn() throws AccessException {
        if (!loggedIn) {
            throw new AccessException("You are not logged in to this account!");
        }
    }

    // logs user in when password is correct
    public void logIn(String password) throws AccessException {
        if (!password.equals(this.password)) {
            throw new AccessException("Incorrect Password!");
        }
        loggedIn = true;
    }

    // user can log out and data will be saved
    public void logOut() {
        loggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public void changeUsername(String username, ArrayList<Account> accounts) throws AccessException, AccountException {
        this.isLoggedIn();

        for (Account i : accounts) { //temporary solution?
            if (i.username.equals(username)) {
                throw new AccountException("Username taken!");
            }
        }

        this.username = username;
    }

    public void changePassword(String password) throws AccessException{
        this.isLoggedIn();

        this.password = password;
    }

    public void changeName(String name) throws AccessException{
        this.isLoggedIn();

        this.name = name;
    }

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

    public void displayPosts() throws PostException {
        for (Post i : posts) {
            i.displayPost();
        }
    }

    public void uploadPost(String filename) throws AccessException {
        this.isLoggedIn();

        //file parsing

        Post post = new Post()//PUT PARSED INFO HERE

        posts.add(post);
    }

    public void editPost(Post post) {
        //TODO
    }

    public void deletePost(Post post) throws PostException, AccessException {
        this.isLoggedIn();

        if (!posts.contains(post)) {
            throw new PostException("This post does not exist!");
        }
        posts.remove(post);
        post.deletePost();
    }

    public ArrayList<Comment> getComments() {
        return commentsMade;
    }

    public void displayComments() throws CommentException, PostException {
        for (Comment i : commentsMade) {
            i.displayComment();
        }
    }

    public void makeComment(Comment comment) {
        commentsMade.add(comment);
    }

    public void editComment(Comment comment, String text) throws AccessException, CommentException {
        this.isLoggedIn();

        if (!commentsMade.contains(comment)) {
            throw new CommentException("Comment does not exist!");
        }

        comment.editComment(text);
    }

    public void deleteComment(Comment comment) throws AccessException, AccountException {
        this.isLoggedIn();

        if (!commentsMade.contains(comment)) {
            throw new AccountException("Comment does not exist!");
        }
        commentsMade.remove(comment);
        comment.deleteComment();
    }

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

import java.util.ArrayList;
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
    private static ArrayList<Account> accounts;
    private String username;
    private String password;
    private String name;
    private ArrayList<Post> posts;
    private ArrayList<Comment> commentsMade;
    private int numPosts;
    private boolean loggedIn = false; //by default not logged in

    public Account(String username, String password, String name) throws AccountException {
        for (Account i : accounts) {
            if (i.username.equals(username)) {
                throw new AccountException("Username taken!");
            }
        }
        this.username = username;
        this.password = password;
        this.name = name;
        this.loggedIn = true;
        this.numPosts = 0;
    }

    public Account() {} //to be loaded if it existed previously

    // checks if user is logged into the account and can have access
    private void isLoggedIn() throws AccessException {
        if (!loggedIn) {
            throw new AccessException("You are not logged in to this account!");
        }
    }

    // logs user in when password is correct
    private void logIn() {
        loggedIn = true;
    }

    // user can log out and data will be saved
    public void logOut() {
        loggedIn = false;
        //write changes to file
    }

    public String getUsername() {
        return username;
    }

    public void addPost(Post post) throws PostException, AccessException {
        this.isLoggedIn();

        if (posts != null && posts.size() > 0) {
            for (Post i : posts) {
                if (i.getTitle().equals(post.getTitle())) {
                    throw new PostException("A post with this title already exists!");
                }
            }
        }
        posts.add(post);
        numPosts++;
    }

    public void deletePost(Post post) throws PostException, AccessException {
        this.isLoggedIn();

        if (!posts.contains(post)) {
            throw new PostException("This post does not exist!");
        }
        posts.remove(post);
        numPosts--;
        post.deletePost();
    }

    public ArrayList<Comment> getComments() {
        return commentsMade;
    }

    public void displayComments() {

    }

    public void makeComment(Comment comment) {
        commentsMade.add(comment);
    }

    public void editComment() throws AccessException {
        this.isLoggedIn();
        //finish
    }

    public void editAccount() throws AccessException {
        this.isLoggedIn();
        //finish
    }

    public void deleteAccount() throws AccessException {
        this.isLoggedIn();

        this.username = null;
        this.name = null;
        this.password = null;
        this.posts = null;
        this.commentsMade = null;
        this.numPosts = 0;
        this.loggedIn = false;
    }
}

import java.util.ArrayList;
import java.io.*;
import java.time.*;
/**
 * Project 4 - Post
 * <p>
 * The Post class stores information about posts
 *
 * @author Team #002, Section Y01
 * @version July 21, 2021
 *
 */
public class Post {
    private String title;
    private String authorName;
    private final Account account; //account that made it
    private String text;
    private String timestamp; //e.g. 2010-12-03T11:30
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    /**
     * Constructs a new post - for use with a brand new post
     *
     * @param title: title
     * @param authorName: author name
     * @param text: text contents
     * @param account: account that made the post
     */
    public Post(String title, String authorName, String text, Account account) {
        this.title = title;
        this.authorName = authorName;
        this.text = text;
        this.account = account;

        String time = LocalDateTime.now().toString();
        time = time.substring(5, 19);
        this.timestamp = time;
        account.addPost(this);
    }

    /**
     * Constructs a new post - for use with retrieval
     * from previous data
     *
     * @param title: title
     * @param authorName: author name
     * @param text: text contents
     * @param account: account that made the post
     * @param timestamp: current time (local machine)
     */
    public Post(String title, String authorName, String text, Account account, String timestamp, ArrayList<Comment> comments) {
        this.title = title;
        this.authorName = authorName;
        this.text = text;
        this.account = account;
        this.timestamp = timestamp;
        this.comments = comments;
    }

    /**
     * get title
     *
     * @return: title
     */
    public String getTitle() {
        return title;
    }

    /**
     * get author name
     *
     * @return: author name
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * get username
     *
     * @return: username
     */
    public String getAuthorUsername() {
        return account.getUsername();
    }

    /**
     * get account
     *
     * @return: account that made the post
     */
    public Account getAccount() {
        return account;
    }

    /**
     * get timestamp
     *
     * @return: timestamp
     */
    public String getTimeStamp() {
        return timestamp.toString();
    }

    /**
     * get text
     *
     * @return: number of comments
     */
    public String getText() {
        return text;
    }

    /**
     * get number of comments
     *
     * @return: number of comments
     */
    public int getNumComments() {
        return comments.size();
    }

    /**
     * get comments
     *
     * @return: comments
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * add a comment
     *
     * @param comment: comment to be added
     */
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    /**
     * delete a comment
     *
     * @param comment: comment to be deleted
     */
    public void deleteComment(Comment comment) {
        comments.remove(comment);
    }

    /**
     * delete post by setting all fields to null
     */
    public void deletePost() {
        title = null;
        authorName = null;
        text = null;
        comments = null;
        timestamp = null;
    }

    /**
     * display the post
     **/
    public void displayPost() {
        System.out.println(this.toString());
    }

    /**
     * format post as string
     *
     * @return: string of post
     *  title
     *  username    timestamp
     *  text
     */
    public String toString() {
        return title + "\n" + account.getUsername() + "\t" + timestamp + "\n" + text;
    }

    /**
     * edit text
     *
     * @param text: new text
     */
    public void editText(String text) {
        this.text = text;
    }

    /**
     * export post to a csv
     *
     * @param post: post to be exported
     * @throws IOException: when FileWritier fails
     */
    public void exportPost(Post post) throws IOException {
        try (FileWriter fw = new FileWriter(new File(post.title +".csv"))) {
            fw.append(post.title + ",");
            fw.append(post.authorName + ",");
            fw.append(post.text + ",");
            fw.append(post.timestamp.toString());
            
        } catch (IOException e) {
            throw e;
        }
    }
}

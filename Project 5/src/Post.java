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
    private String text;
    private String timestamp; //e.g. 2010-12-03T11:30
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    /**
     * Constructs a new post - for use with a brand new post
     *
     * @param title: title
     * @param authorName: author name
     * @param text: text contents
     */
    public Post(String title, String authorName, String text) {
        this.title = title;
        this.authorName = authorName;
        this.text = text;

        String time = LocalDateTime.now().toString();
        time = time.substring(5, 19);
        this.timestamp = time;
    }

    /**
     * Constructs a new post - for use with retrieval
     * from previous data
     *
     * @param title: title
     * @param authorName: author name
     * @param text: text contents
     * @param timestamp: current time (local machine)
     */
    public Post(String title, String authorName, String text, String timestamp) {
        this.title = title;
        this.authorName = authorName;
        this.text = text;
        this.timestamp = timestamp;
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
     * get timestamp
     *
     * @return: timestamp
     */
    public String getTimeStamp() {
        return timestamp;
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

    public void editComment(int commentIndexPost, String text) {
        Comment comment = comments.get(commentIndexPost);
        comment.editComment(text);
        comments.set(commentIndexPost, comment);
    }

    /**
     * delete a comment
     *
     * @param comment: comment to be deleted
     */
    public void deleteComment(int index) {
        comments.remove(index);
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
        return "Title: " + title + "\nAuthor: " + authorName + "\n" + text + "\n" + timestamp;
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

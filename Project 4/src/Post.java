import java.util.ArrayList;
import java.time.*;

public class Post {
    private String title;
    private String authorName;
    private final Account account; //account that made it
    private String text;
    private LocalDateTime timestamp; //e.g. 2010-12-03T11:30
    private ArrayList<Comment> comments;

    public Post(String title, String authorName, String text, Account account) throws PostException, AccessException {
        this.title = title;
        this.authorName = authorName;
        this.text = text;
        this.account = account;
        this.timestamp = LocalDateTime.now();
        account.addPost(this);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorUsername() {
        return account.getUsername();
    }

    public String getTimeStamp() {
        return timestamp.toString();
    }

    public int getNumComments() {
        return comments.size();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void deletePost() {
        title = null;
        authorName = null;
        text = null;
        comments = null;
        timestamp = null;
    }

    public void displayPost() throws PostException{
        if (this.title == null) {
            throw new PostException("This post was deleted!");
        }
        System.out.println(this.toString());
    }

    public String toString() {
        return title + "\n" + account.getUsername() + "\t" + timestamp + "\n" + text;
    }

}

import java.util.ArrayList;

public class Post {
    private String title;
    private String authorName;
    private String authorUsername;
    private String text;
    private int date; //mmddyy
    private int time; //hhmm
    private ArrayList<Comment> comments;
    private int numComments;

    public Post(String title, String authorName, String text) {
        this.title = title;
        this.authorName = authorName;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public int getNumComments() {
        return numComments;
    }

    public void deletePost() {
        title = null;
        authorName = null;

    }

    public void displayPost() {

    }

    public String toString() {

    }

}

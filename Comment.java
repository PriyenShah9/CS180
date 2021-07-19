public class Comment {
    private String authorName;
    private String text;
    private Post post; //post the comment was posted on

    public Comment(String authorName, String text) {
        this.authorName = authorName;
        this.text = text;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
    }

    public Post getPost() {
        return post;
    }

    public void displayComment() {

    }

    public String toString() {

    }
}

public class Comment {
    private String authorName;
    private String text;
    private final Post post; //post the comment was posted on
    private final Account account; //account that made the comment

    public Comment(String authorName, String text, Post post, Account account) {
        this.authorName = authorName;
        this.text = text;
        this.post = post;
        this.account = account;
        post.addComment(this);
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

    public void editComment(String text) { //basically setText()
        this.text = text;
    }

    public void deleteComment() {
        text = null;
    }

    public void displayComment() throws CommentException, PostException {
        if (this.text == null) {
            throw new CommentException("This comment was deleted!");
        }
        post.displayPost();
        System.out.println(this.toString());
    }

    public String toString() {
        return account.getUsername() + ": " + text;
    }
}

import java.time.LocalDateTime;
/**
 * Project 4 - Comment
 * <p>
 * The Comment class stores information
 * about comments and provides some methods
 * to access or change that infromation.
 *
 * @author Team #002, Section Y01
 * @version July 21, 2021
 *
 */
public class Comment {
    private String authorName;
    private String text;
    private final Post post; //post the comment was posted on
    private final Account account; //account that made the comment
    private String timestamp;

    /**
     * Construct a comment - brand new
     *
     * @param authorName: authorname
     * @param text: text
     * @param post: post the comment is made on
     * @aparam account: account that made the comment
     * @throws AccessException: when not logged in
     */
    public Comment(String authorName, String text, Post post, Account account) throws AccessException {
        this.authorName = authorName;
        this.text = text;
        this.post = post;
        this.account = account;
        post.addComment(this);

        String time = LocalDateTime.now().toString();
        time.replaceAll("-", ":");
        time.replaceAll("T", ":");
        time = time.substring(5);
        this.timestamp = time;
        account.makeComment(this);
    }

    /**
     * Construct a comment - from previous run of program
     *
     * @param authorName: authorname
     * @param text: text
     * @param post: post the comment is made on
     * @aparam account: account that made the comment
     * @param timestamp: time the comment was made
     */
    public Comment(String authorName, String text, Post post, Account account, String timestamp) {
        this.authorName = authorName;
        this.text = text;
        this.post = post;
        this.account = account;
        this.timestamp = timestamp;
        post.addComment(this);
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
     * get text
     *
     * @return: text
     */
    public String getText() {
        return text;
    }

    /**
     * get post
     *
     * @return: post
     */
    public Post getPost() {
        return post;
    }

    /**
     * get account
     *
     * @return: account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * edit comment
     *
     * @param text: new text
     */
    public void editComment(String text) { //basically setText()
        this.text = text;
    }

    /**
     * delete commment by setting fields to null
     **/
    public void deleteComment() {
        text = null;
        authorName = null;
        timestamp = null;
    }

    /**
     * display comment
     *
     * @throws CommentException: when comment is a delted comment
     * @throws PostException: when post is deleted
     */
    public void displayComment() throws CommentException, PostException {
        if (this.text == null) {
            throw new CommentException("This comment was deleted!");
        }
        post.displayPost();
        System.out.println(this.toString());
    }

    /**
     * format comment as string
     *
     * @return: string version
     * username - timestamp: text
     */
    public String toString() {
        return account.getUsername() + " - " + timestamp + ": " + text;
    }
}

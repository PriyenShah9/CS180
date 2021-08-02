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
    private String timestamp;
    private String postTitle;

    /**
     * Construct a comment - brand new
     *
     * @param authorName: authorname
     * @param text:       text
     */
    public Comment(String authorName, String text, String postTitle) {
        this.authorName = authorName;
        this.text = text;
        this.postTitle = postTitle;
        String time = LocalDateTime.now().toString();
        time = time.substring(5, 19);
        this.timestamp = time;
    }

    /**
     * Construct a comment - from previous run of program
     *
     * @param authorName: authorname
     * @param text:       text
     * @param timestamp:  time the comment was made
     */
    public Comment(String authorName, String text, String postTitle, String timestamp) {
        this.authorName = authorName;
        this.text = text;
        this.postTitle = postTitle;
        this.timestamp = timestamp;
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
     * get time
     *
     * @return: timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    public String getPostTitle() {
        return postTitle;
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
     */
    public String displayComment() {
        return this.toString();
    }

    /**
     * format comment as string
     *
     * @return: string version of comment
     * username - timestamp: text
     */
    public String toString() {
        return authorName + " - " + timestamp + ": " + text;
    }

    /**
     * check if two comments are equal by checking all fields
     *
     * @param: comment
     * @return: boolean T for equal, F for not equal
     */
    public boolean equals(Comment comment) {
        if (this == comment) {
            return true;
        }
        if (!this.authorName.equals(comment.authorName)) {
            return false;
        }
        if (!this.timestamp.equals(comment.timestamp)) {
            return false;
        }
        if (!this.text.equals(comment.text)) {
            return false;
        }
        return true;
    }
}


import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.util.Scanner;

public class Post {
    private String title;
    private String authorName;
    private final Account account; //account that made it
    private String text;
    private LocalDateTime timestamp; //e.g. 2010-12-03T11:30
    private ArrayList<Comment> comments;

    public Post(String title, String authorName, String text, Account account, String timeString) throws PostException, AccessException {
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

    public void uploadPost(String filename) throws AccessException, Exception{
        try {

            Scanner sc = new Scanner(new File(filename));  
            sc.useDelimiter(","); 
            String[] p1 = {};   
            int i = 0;
            while (sc.hasNext()) { 
                p1[i] = sc.next();
                i++;
            }
            String title = p1[0];
            String authorName = p1[1];
            String text = p1[2];
            String timestamp = LocalDateTime.now().toString();

            Post post = new Post(title, authorName, text, account, timestamp);
            account.addPost(post);
            sc.close(); 

        } catch (FileNotFoundException e) {
            AccessException a = new AccessException(String.format("File does not exist"));
            throw a;
        }
     
    }
    public void exportPost(Post post){

        try (FileWriter fw = new FileWriter(new File(post.title +".csv"))) {
            fw.append(post.title + ",");
            fw.append(post.authorName + ",");
            fw.append(post.text + ",");
            fw.append(post.timestamp.toString());
            
        } catch (IOException e) {
        
            e.printStackTrace();
        }
    }

}

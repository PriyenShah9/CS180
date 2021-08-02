import java.io.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.*;


/**
 * Project 4 - Application
 * <p>
 * The Application class is the class that
 * does all the interactions with the user.
 *
 * @author Team #002, Section Y01
 * @version July 21, 2021
 */

public class ApplicationClient {

    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private static ArrayList<Post> posts = new ArrayList<Post>();
    private static ArrayList<Comment> comments = new ArrayList<Comment>();
    private static String usernameAccountLoggedIn;

    public static void main(String[] args) throws IOException{
        Scanner scan = new Scanner(System.in);
        Socket socket = new Socket("localhost", 4244);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.length() != 0 && line.charAt(line.length() - 1)== ' ') {
                    String ans = scan.nextLine();
                    pw.println(ans);
                    pw.flush();
                }
            }

        br.close();
        pw.close();
    }
}


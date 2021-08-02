import java.io.*;
import java.io.IOException;
import java.util.Scanner;
import java.net.*;


/**
 * Project 4 - ApplicationClient
 * <p>
 * The ApplicationClient class is the class that
 * does all the interactions with the user through the GUI.
 *
 * @author Team #002, Section Y01
 * @version August 2, 2021
 */

public class ApplicationClient {

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


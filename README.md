# CS180 - Project 4 - Team #002
***
A social media application that allows users to create accounts,
make posts, and add comments to posts (Option 1)
***
** Table of Contents
1. [Class Application](#class-application)
2. [Class Account](#class-account)
3. [Class Post](#class-post)
4. [Class Comment](#class-comment)
5. [Exceptions](#exceptions)
6. [Test Case](#test-cases)
***
** Class Application
This class runs the main method of the program that interacts with the user. 
Users will be prompted to create an account, log in, or exit the application.
Once logged in using a password, users will be able to make posts or comments and view others'
posts and comments.

Editing and deleting is restricted to the users that created the account/post/comment. 
Additionally, only users that are logged in will be able to see any posts or comments.

Most importantly, this class is in charge of loading in data from previous runs of the program
and storing any changes made after a user logs out. 
***
** Class Account
This class stores all information about accounts. This includes name, username, password,
posts made, comments made, and the logged in status. Methods in this class allow for access
to the account information, as well as to posts and comments made by the account.
***
** Class Post
This class stores information about each individual post. These include the username of the author, 
the title of the post, the timestamp, the text contents, and all comments made on the post. Users are
also able to upload posts from a csv file
***
** Class Comment
This class stores information about each individual comment. These include the username of the author,
the timestamp, and the text contents.
***
** Exceptions
When they occur, exceptions come with specialized error messages that are more specific than
descriptions given here.
1. AccountException - occurs when there are issues creating or retrieving an account
2. AccessException - occurs when a user tries to do an action that they are not authorized to do
3. PostException - occurs when there are issues creating or retrieving a post.
4. CommentException - occurs when there are isseus creating or retrieving a comment. 
***
** Test Cases
These test scripts were used to show some basic features of the program. We did more testing beyond
what these scripts show, hoever it is extremely tedious to create the scripts so they were just done
through running the program.

@Test(timeout = 1000)
public void testExpectedOne() {
    // Separate each input with a newline (\n). 
    String input = "1\nJohn Doe\njdoe\npassword\npassword\n2\nc\nFirst Post\nHello World\nt\nb\n5\n"; 

    // Pair the input with the expected result
    String expected = "Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? \n
          What is your name? \n
          What would you like your username to be? \n
          What would you like your password to be? \n
          Re-enter your password: \n
          Your account has been created.\n
          Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? \n
          Would you like to create(c), edit(e), delete(d), import(i), or export(ex) a post? \n
          What would you like your title to be? \n
          Enter your post: \n
          Would you like to edit your account(c) or go back(b)? \n
          You must answer with "c" or "b".\n
          Would you like to edit your account(c) or go back(b)? \n
          Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)?" 
    
    //Storage file should now contain (timestamp may be different):
    //john_doe jdoe pass
    //$First_Post Hello_World 07-22T09:10:08 

    // Runs the program with the input values
    // Replace TestProgram with the name of the class with the main method
    receiveInput(input);
    TestProgram.main(new String[0]);

    // Retrieves the output from the program
    String stuOut = getOutput();

    // Trims the output and verifies it is correct. 
    stuOut = stuOut.replace("\r\n", "\n");
    assertEquals("Error message if output is incorrect, customize as needed",
                    expected.trim(), stuOut.trim());

}

** After running the above code, run this next test to see that the program loads input correctly
@Test(timeout = 1000)
public void testExpectedOne() {
    // Set the input        
    // Separate each input with a newline (\n). 
    String input = "4\njdoe\npass\njdoe\nc\nFirst Post\nHello John\nb"; 

    // Pair the input with the expected result
    String expected = "Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? \n
        To edit or view an account you must login.\n
        Username: \n
        Password: \n
        Enter the username of the account you would like to view: \n
        Here are jdoe's posts.\n
        First Post\n
        jdoe	07-22T09:10:08\n
        Hello World\n
        \n
        Would you like to make a comment(c), delete a comment(d), edit a comment(e), view all comments(v), view all posts(p) or go back(b)? \n
        Enter the title of the post you would like to make a comment to. \n
        What would you like to comment on this post.\n
        \n
        Comment was made. Would you like to view more accounts(c) or go back(b)? \n
        Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? \n" 
        
    //Storage file should now contain (timestamp may be different):
    //john_doe jdoe pass
    //$First_Post Hello_World 07-22T09:10:08 Hello_John;jdoe;07-22T09:20:52

    // Runs the program with the input values
    // Replace TestProgram with the name of the class with the main method
    receiveInput(input);
    TestProgram.main(new String[0]);

    // Retrieves the output from the program
    String stuOut = getOutput();

    // Trims the output and verifies it is correct. 
    stuOut = stuOut.replace("\r\n", "\n");
    assertEquals("Error message if output is incorrect, customize as needed",
                    expected.trim(), stuOut.trim());

}

** Make a new account
@Test(timeout = 1000)
public void testExpectedOne() {
    // Set the input        
    // Separate each input with a newline (\n). 
    String input = "1\nJane Doe\ndoej\npass\npass\n4\njdoe\nFirst Post\nb\n5\n"; 

    // Pair the input with the expected result
    String expected = "Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? \n
        What is your name? \n
        What would you like your username to be? \n
        What would you like your password to be? \n
        Re-enter your password: \n
        Your account has been created.\n
        Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? \n
        Enter the username of the account you would like to view: \n
        Here are jdoe's posts.
        First Post
        jdoe	07-22T09:10:08
        Hello World
        \n
        Would you like to make a comment(c), delete a comment(d), edit a comment(e), view all comments(v), view all posts(p) or go back(b)? \n
        Enter the title of the post you would like to edit a comment on. \n
        You have not made any comments on this post\n
        Would you like to view more accounts(c) or go back(b)? \n
        Would you like to create(1), edit(2), delete(3), view(4) an account or exit the program(5)? \n" 
        
    //Storage file should now contain (timestamp may be different):
    //John_Doe jdoe pass
    //$First_Post Hello_World 07-22T09:10:08 Hello_John;jdoe;07-22T09:20:52
    //doej pass Jane_Doe

    // Runs the program with the input values
    // Replace TestProgram with the name of the class with the main method
    receiveInput(input);
    TestProgram.main(new String[0]);

    // Retrieves the output from the program
    String stuOut = getOutput();

    // Trims the output and verifies it is correct. 
    stuOut = stuOut.replace("\r\n", "\n");
    assertEquals("Error message if output is incorrect, customize as needed",
                    expected.trim(), stuOut.trim());

}

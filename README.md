# CS180 - Project 5 - Team #002
***
A social media application that allows users to create accounts,
make posts, and add comments to posts (Option 1)
***
** Table of Contents
1. [Instructions] (#instructions)
2. [Class ApplicationClient](#class-applicationclient)
3. [Class ApplicationServer] (#class-applicationserver)
4. [Classes Account, Post, Comment, Exceptions](#classes-account-post-comment-exceptions)
5. [Concurrency](#concurrency)
***
** Instructions
Compile as usual, making sure all files are in the same directory.
  If using IntelliJ and if there is a .csv file or a storagefile.txt with desired "previous run" information, 
  these should be placed in the same directory as the module containing the program files.
  No testing of extra file placement was carried out for other IDEs as all team members use IntelliJ.
Run ApplicationServer first, then start ApplicationClient. It should automatically connect. 
***
** Class ApplicationClient
***
** Class ApplicationServer
***
** Classes Account, Post, Comment, Exceptions
** Class Application
This class runs the main method of the program that interacts with the user. 
Users will be prompted to create an account, log in, or exit the application.
Once logged in using a password, users will be able to make posts or comments and view others'
posts and comments.

Editing and deleting is restricted to the users that created the account/post/comment. 
Additionally, only users that are logged in will be able to see any posts or comments.

Most importantly, this class is in charge of loading in data from previous runs of the program
and storing any changes made after a user logs out. 

** Class Account
This class stores all information about accounts. This includes name, username, password,
posts made, comments made, and the logged in status. Methods in this class allow for access
to the account information, as well as to posts and comments made by the account.

** Class Post
This class stores information about each individual post. These include the username of the author, 
the title of the post, the timestamp, the text contents, and all comments made on the post. Users are
also able to upload posts from a csv file

** Class Comment
This class stores information about each individual comment. These include the username of the author,
the timestamp, and the text contents.

** Exceptions
When they occur, exceptions come with specialized error messages that are more specific than
descriptions given here.
1. AccountException - occurs when there are issues creating or retrieving an account
2. AccessException - occurs when a user tries to do an action that they are not authorized to do
3. PostException - occurs when there are issues creating or retrieving a post.
4. CommentException - occurs when there are isseus creating or retrieving a comment. 
***
** Concurrency
***
** Submission Responsibilities
Submission to Vocareum - 
Submission of Report to Brightspace - 
Submission of Presentation to Brightspace - Crystal

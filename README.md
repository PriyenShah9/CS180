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
also able to upload posts from a csv file.
***
** Class Comment
This class stores information about each individual comment. These include the username of the author,
the timestamp, and the text contents.
*** Exceptions
When they occur, exceptions come with specialized error messages that are more specific than
descriptions given here.
1. AccountException - occurs when there are issues creating or retrieving an account
2. AccessException - occurs when a user tries to do an action that they are not authorized to do
3. PostException - occurs when there are issues creating or retrieving a post.
4. CommentException - occurs when there are isseus creating or retrieving a comment. 



# CS180 - Project 5 - Team #002
***
A social media application that allows users to create accounts,
make posts, and add comments to posts (Option 1). Project 5 extensions
adds a GUI, network, and the use of concurrency.
***
** Table of Contents
1. [Instructions] (#instructions)
2. [Class Application] (#class-application)
3. [Class ApplicationClient](#class-applicationclient)
4. [Class ApplicationServer] (#class-applicationserver)
5. [Classes Account, Post, Comment, Exceptions](#classes-account-post-comment-exceptions)
6. [Concurrency](#concurrency)
***
** Instructions

Compile as usual, making sure all files are in the same directory.
  If using IntelliJ and if there is a .csv file or a storagefiles (accounts.txt, posts.txt, comments.txt) 
  with desired "previous run" information, these should be placed in the same directory as the module 
  containing the program files.
  No testing of extra file placement was carried out for other IDEs as all team members use IntelliJ.
  Run ApplicationServer first, then start ApplicationClient. It should automatically connect. 
  
  The only other thing is that selecting "edit account" will allow you to do actions regarding posts.
***
** Class Application

This class is an updated version of the Application program from Project 4. This is a full and working 
implementation of Project 4, which we used to do Project 5. As before, it is the "main". 
***
** Class ApplicationClient

This class is one half of the Application class that runs on the user end. It's main purpose is to 
display the GUI and interact with the user. It displays posts and comments, and accepts user input 
via buttons or the keyboard. Information is sent between this class and the ApplicationServer class.
***
** Class ApplicationServer
This class is the other half of the Application class and runs on the server end. It's main purpose
is to hold the data of the application and do computations. For example, if ApplicationClient requests
to view an account's posts, the ApplicationServer will find the account and send the posts to the 
ApplicationClient to display. 

This class also implements concurrency, which is discussed in a later section.
***
** Classes Account, Post, Comment, Exceptions

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

Concurrency is implemented on the ApplicationServer class and allows many users to be connected
at the same time. Threads and the Runnable interface are used to carry out this task and synchronized 
blocks are used to eliminate race conditions. There are three static ArrayLists for Accounts, Posts, and Comments
in order to store information that is accessible to all threads. These ArrayLists are using Collections.synchronizedList
to help eliminate race conditions when adding or removing.

Gatekeepers involved are accountsGatekeeper, postsGatekeeper, commentsGatekeeper, and some of the accounts being
loaded. These are used to lock different methods together. For example, two threads cannot have one access the method 
viewing posts and the other access the method making a post on the account being viewed at the same time.

Lastly, there is a real-time update implementation. This is used to automatically refresh the posts or comments when
someone is viewing all posts or all comments from a user. This is not done through threading, but rather just
re-displaying during the time when the "ok" button is not pressed.
***
** Submission Responsibilities

Submission of Code to Vocareum - Arko Mukhopadhyay

Submission of Report to Brightspace - Ilina Adhikari

Submission of Presentation to Brightspace - Crystal Jiang

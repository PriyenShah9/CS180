# Test Cases
***
1.  [Test 1] (#test-1)
2.  [Test 2] (#test-2)
3.  [Test 3] (#test-3)
4.  [Test 4] (#test-4)
***
**Test 1: Account Creation
Steps:
 1. User launches application.
 2. User selects the “Create Account” button
 3. User enters username via the keyboard.
 4. User enters password via the keyboard.
 5. User selects the "Create" button. 

Expected result: Application verifies that the user’s username is unique and automatically logs them in. 
Test Status: Passed. 
***
**Test 2: Making, Commenting On, and Viewing a Post
Steps:
 1. User is logged in.
 2. User selects the “Make a Post” button.
 3. User selects the title textbox and enters the title via the keyboard.
 4. User selects the content textbox and enters the content of the post via the keyboard.
 5. User selects the “Post” button.
 6. User selects the “Make a Comment” button.
 7. User selects the content textbox and enters the content of the comment via the keyboard.
 8. User selects the “Comment” button.
 
Expected result: Application makes a post on the user’s account, attaches the comment to that post, and display both.
Test Status: Passed. 
***
**Test 3: Data Preservation
Steps:
 1. Test 1 and 2 have been previously carried out.
 2. User x’s out the application.
 3. User re-opens the application.
 4. User attempts to create a new account using the username used in Test 1.
 5. User creates a new account with a different username and is logged in.
 6. User selects the “Search for an Account” textbox and enters the username from Test 1 via keyboard.
 7. User selects the “Search” button.

Expected result: Application displays that the Test 1 username is in use, then allows the user to make the account with the new username, and then displays the post and comment from Test 2.
Test Status: Passed. 
***
**Test 4: Two Users (Concurrency Test)
Steps:
 1. User 1 logs in on one machine with the username and password from Tests 1 and 2.
 2. User 2 logs in on another machine with the new username and password from Test 3.
 3. User 2 makes a new post of their own.
 4. User 2 searches for and views User 1’s posts.
 5. User 1 makes a new post.
 6. User 1 searches for and views User 2’s posts.

Expected result: User 2’s view of User 1’s posts automatically updates with the new post. User 1 can see the new post made by User 2.
Test Status: Passed. 



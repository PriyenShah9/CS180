# Test Cases
***
1.  [Test 1] (#test-1)
2.  [Test 2] (#test-2)
3.  [Test 3] (#test-3)
4.  [Test 4] (#test-4)
***
**Test 1: Account Creation
Steps:
1.  User launches application.
2.  User types “1” and clicks “Enter”.
3.  User enters username via the keyboard.
4.  User clicks “Enter".
5.  User enters password via the keyboard and clicks “Enter".
6.  User re-enters password and clicks “Enter".

Expected result: Application verifies that the user’s username is unique and automatically logs them in. The option screen should now display.
Test Status: Passed. 
***
**Test 2: Making, Commenting On, and Viewing a Post
Steps:
 1.  User is logged in.
 2.  User writes “1” and hits “Enter”.
 3.  User writes “1”  and hits “Enter” again to make a new post.
 4.  User selects the textbox, writes the title via the keyboard, and clicks “Enter".
 5.  User selects the textbox, writes the content of the post via the keyboard, and clicks “Enter".
 6.  User writes “4” and hits “Enter”.
 7.  User selects the textbox, writes their own username, clicks “Enter”, writes the title of the post, and clicks “Enter”.
 8.  User selects the textbox and enters the content of the comment via the keyboard.
 9.  User selects the “Enter” button.

Expected result: Application makes a post on the user’s account and attaches the comment to that post.
Test Status: Passed. 
***
**Test 3: Data Preservation
Steps:
 1. Test 1 and 2 have been previously carried out.
 2. User logs out.
 4. User attempts to create a new account using the username used in Test 1.
 5. User creates a new account with a different username and is logged in.
 6. User writes “2”, hits “Enter”, and writes the username from Test 1 via keyboard.
 7. User selects the “Enter” button.

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
Test Status: Failed.
***
**Other Tests

- Not explicitly spelled out like the previous 4 tests.
- Each member of the team played around with the program as if we were users using it
   - Editing posts and comments
   - Deleting posts and comments
   - Importing and exporting posts with .csv
- Each member also tried to “break” it
   - Putting in incorrect input over and over again
   - Doing illogical sequences of actions
   - The same user logged in on two sessions



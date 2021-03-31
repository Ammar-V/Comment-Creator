# Comment Creator

This is a JavaFX program created for my high school staff. It is aimed at reducing the time to write report card comments at the end of the semester. The idea is that a teacher may set up their own database of comments, and when actually writing them, all they have to do is select a specific comment from their database, or let the program randomely decide. At the end of the process, the program outputs a paragraph comment that includes the name of the student and uses personal pronouns.

To run the program, have Java 11 or higher installed and download the folder **CommentCreator**. JavaFX SDK 11 or higher also needs to be downloaded into the application directory.

The current username is "user" and password is "pass"

There is an example database already in the directory called "commentData.txt". Delete this file if you want to start fresh.

The program has two main sections: the database handling system and an interface to actually create the comments.

**Database**
"View Comments" -> Select the subject you want to edit the data for, delete a subject, or create a new subject, and then click next. At this window, you can add, delete, or edit units/chapters. After selecting a unit, you can add comments. New comments have to be ranked on the scale provided. You can also delete or edit existing comments. You can undo if you delete a comment on accident. All changes are saved on exiting the program.

**Creating a comment**
"Create New Comment" -> Select the subject for which you wish to create a comment. Type the student name, select their gender, and add your email(Optional! If added, the program outputs a statement letting the reader know how they can reach out to you.) -> The next window is the main section where you can create the comment. Select the unit and type a rating for the comment based on the student's performance. If the "Select Specific Comment" is checked when you press "Add Comment", a window will pop up with all of the comments in the unit and rating specified by you. If unchecked, the program will randomely output a comment from the selected unit and rating. No repeated comments will be added. You can also delete a comment and change the positioning of a comment by navigating to the menu up top ("Edit"). Click next when done. The paragraph comment will be presented to you in a rich text editor. You can also see alert symbols that warn you if gramatical changes need to be made. You can copy the comment and move onto creating the next comment.

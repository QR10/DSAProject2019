-Decription
This java aplication allows a book (text file) to be read line by line and each word stored as a key in a hashmap 
which also stores an integer value for each key which represents the number of times the word was read in by the application.
The aplication compares the words being read in against a file which contains words to be ignore (note that the ignorewords.txt file was modified),
these words are ignored and therefore not stored in the hashmap.
After reading the file the application can then organise the words by the frequency which they appear and output the most frequent words to a png image file.

-When running the program, the following menu will be outputed.

********************************************************

============== Select the Book =============
1 - To enter a file name
2 - To specify max number of words(default = 10, max = 10, min = 1)

9 - To exit and output words to PNG
=============================================

Enter option number:

********************************************************

-Instructions for use
The user should pick option 1 to read a new book (file).
  -the user is then asked to input the name of the file to be read in,
  -the name should be entered without the file extension.
	** Example: to read a file called "DeBelloGallico.txt" enter: "DeBelloGallico" **	

The the user can then specify the number of words to be outputed to the png file by picking option number 2.
  -The default number of words is 10, but the user can input any number from 1 (minimum) to 10 (maximum).

At last pick option 9 to exit the program and output the desired number of words to a png file.




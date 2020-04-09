Google Doc Link: [https://docs.google.com/document/d/1s1ySVRLn8WtPpgv-QgCpm3piCgmBqqAGpY9VMDg5Gco/edit?usp=sharing]

1. Make sure you got a pretty recent version of java (i hava 13.0.2)
2. download javafx sdk (im using fx 14): https://gluonhq.com/download/javafx-14-sdk-windows/
3. extract and place the folder somewhere consistent, mine's in programfiles/java
4. git pull cos i've rearranged some stuff
5. go to File>Project Structure>Project, make sure the project sdk is at the latest version java you have
6. change the language level to match
7. change the compiler output from %PATH%\out to %PATH\mods
8. go to the Libraries tab click the plus>java>find the path to the lib folder of the javafx sdk. Mine is 'Program Files\Java\javafx-sdk-14\lib'
9. click ok and then ok again
10. Run the main class under view if you haven't yet, it'll throw an error but that is fine
11. go to Run>edit configurations>Application>Main
12. under VM Options add 
	
	--module-path "%PATH_TO_JAVAFX_LIB%;mods\production"
	
	%PATH_TO_JAVAFX_LIB% is the same as from step 8
13. click ok
14. now run the main class.
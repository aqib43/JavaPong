# CSCI2020U_FINAL
The Final Project for 2020U.

Kevin Lounsbury 100654226
Nicholas Juniper 100659791
Tiseagan Ketharasingam 100748047
Aqib Alam 100754170

Project Information:
This project is a game of pong that we implemented utilizing JavaFX's Canvas system along with a custom Animation Timer to act as a game loop. It has rudimentary physics, a polymorphic GameObject system and is networked in order for you to play against another person. The networking is local as-is though, but could easily be modified to work as not local if we gave the chance to input ip.

Our scope was to create a game of pong that ran well, while networked, and was something you could just restart over and over again. We achieved that scope and went on to improve the aesthetic of the game, choosing a nice black and white theme to complement the original game.

You can see our main menu here:
https://media.discordapp.net/attachments/802685229529694218/832044566524854322/unknown.png
You can see the game in action here:
https://gyazo.com/420f990f39a6d5267010e06f8d998a29

How to Run (step-by-step):
Step 1: Make sure you are using java11 or later
Step 2: Have Gradle properly installed and configured
Step 3: Open into ./assignment2/Server/
Step 4: Run command 'gradle run'
Step 5: Wait for program to start running. Will say what port it's listening to.
Step 6: Open into ./assignment2/Client/
Step 7: Run command 'gradle run'
Step 8: Run command 'gradle run' again to open up a second client.
Step 9: Press play button on both clients
Step 10: Press Connect at the top of both clients
Step 11: The paddle you control is highlighted blue and controls are W and S to move
up and down respectively.
Step 12: Enjoy the game!
Step 13: When you win or lose, you can return to menu and return to step 9 to start all over again!

Referenced Resources:
Referenced Java documentation: 
https://docs.oracle.com/en/java/javase/11/
Referenced JavaFX documentation: 
https://docs.oracle.com/javase/8/javafx/api/toc.htm

All code submitted is original.





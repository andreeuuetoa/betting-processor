# betting-processor
This is my solution for the PlayTech internship assignment.
The task was to create an application that processes betting data.

## Short description
The program takes a group of data as an input, where there is specified the matches played in the casin, the players who took part of them and their operations in the matches.
The matches and the operations are ordered by time, with older events first.
The input data can be found in folder (starting from the project root folder): "src/main/resources".
In that folder, there are two files, match_data.txt and player_data.txt, both of which can be modified by hand, but the line structure within must remain.

The match_data.txt file consists of multiple lines, each visualising a match that was played in the casino.
For each line, the elements are separated with a comma and their meanings are:
* First value is the match ID – A random UUID.
* Second value is the return rate for A side.
* Third value is the return rate for B side.
* Fourth value is the result of the match.

The player_data.txt is similar to that of match_data.txt, but this file consists of data about each player who has participated in at least one match.
Each line consists of those values separated by a comma:
* First value is player ID – A random UUID.
* Second value is player operation – One of 3 operations: DEPOSIT, BET, WITHDRAW.
* Third value (if exists) is a match ID – A random UUID.
* Fourth value is the coin number player use for that operation.
* Fifth value (if exists) is the side of the match the player places the bet on.

As the output, the program returns a brief summary that consists of:
* The players who did not make an invalid operation with their final balance and their betting win rate (games won / number of placed bets)
* All the players who made at least one invalid operation with the operation
* Casino's final balance after all the matches were played

The output is shown in results.txt file located in the folder "src/main/java/main".

## Developers

Prerequisites:
* Git
* JDK 17

To run the application, first clone the repository:
````shell
git clone https://github.com/andreeuuetoa/betting-processor
cd betting-processor
````
Running the application will output the result in the aforementioned folder. You can run the program with the command:
````shell
./gradlew run
````

To run the unit tests:
````shell
./gradlew test
````

## Retrospective

The assignment was quite fun and excite to work on.
It also gave me a good project to practice test-driven development on,
which contributed heavily to making the assignment fun to work on,
since I did not have to spend that much time hunting out all the bugs that may be hiding in inconvenient places.
I would like to thank the recruitment partner
who reached out to me when I applied for the internship program and gave me the opportunity to show them my skills.

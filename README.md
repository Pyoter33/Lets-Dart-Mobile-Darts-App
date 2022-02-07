# Lets-Dart-Mobile-Darts-App

LetsDart is a mobile app created for darts fans. It provides a great deal of help with calculating scores and gathering statistics during games 
or creating and managing tournaments and leagues for more than 2 players. It is fully written in Kotlin for devices with Android OS.

## Main Menu
The user have 5 choices in the main menu. He can start a quick game, manage saved players, create new league or tournament and see players' statistics.

<img src=https://i.imgur.com/r7R58Pd.png width=200/>

## Game
This part of the app is obvoiusly the most crucial. At the bottom of the screen there are several buttons which mirror all the basic scores that can be found on the dartboard. 
After each throw the user clicks a proper button and the score updates. If a player scores a double or a triple, the user has to first toggle a triple or a double button and than 
click the button with a desired value. If the user makes a mistake and wants to undo his throw, he can do it anytime with an undo button. There are no restrictions on this, so it's 
possible to even go back to the beginning of the game if needed. On the upper half on the screen there is a section which consists of basic statistics representing current 
state of the game for each player - main score, round score, each partial score in a round, number of won legs and sets, number of scored triples and doubles. Players' scores are 
represented with states. This objects store all needed informations and are than arranged in a stack to greatly simplify previously mentioned undoing of throws. To help
newer players get into the game there's a special lightbulb button which after clicking shows a possible game finisher if there is one. 

<img src=https://i.imgur.com/EUUiUaG.png width=200/>    <img src=https://i.imgur.com/PG2E3ob.png width=200/>

## Game summary
After each game a user is presented with a short summary. If he wills to see more detailed statistics of the game he can click a button beside the player's name. This view shows
some basic stats like won legs or number of thrown darts but also a detailed summary of all scored points by counting totals or percentage wise. 

<img src=https://i.imgur.com/8hCLpT1.png width=200/>    <img src=https://i.imgur.com/NNtIbPI.png width=200/>

## Player management
In this view a user can add new players and rename or delete currently existing. All data is saved in a local relational database provided by Android Room.

<img src=https://i.imgur.com/Qj0mzOG.png width=200/>    <img src=https://i.imgur.com/T0DjSBh.png width=200/>

## Tournaments
Tournaments are a bracket like type of series. If a player wins a game against his opponent, he advances to the next round. Each tournament is customizable and can have 
different rules. If an uneven number of players wants to participate in the tournament one of them (randomly chosen) receives a "buy out" which grants a free pass to the next round
without playing a game. Each round is generated dynamically which provides an unique experience and a readable interface.

<img src=https://i.imgur.com/coDDV0T.png width=200/> <img src=https://i.imgur.com/MGkavYQ.png width=200/> <img src=https://i.imgur.com/6QuNDdn.png width=200/> 

## Leagues
Leagues are based on standings. Like a tournament, each league is customizable. Each player plays a game (or two) against every other opponent in the league. The results are than saved and reflected in the standings. 
The user can filter the games' list to see only those that are still to be played.

<img src=https://i.imgur.com/3NRWVwY.png width=200/>    <img src=https://i.imgur.com/NEFA2SE.png width=200/>    <img src=https://i.imgur.com/3LYAw6K.png width=200/>    <img src=https://i.imgur.com/ArG2pM2.png width=200/>

## Statistics
After each game players' total stats are updated. They can be seen in the statistics view. They look very similar to the "after game stats" but with a few extra fields.

<img src=https://i.imgur.com/cX9y5Ud.png width=200/>




_Special thanks to all my friends who instilled love for darts in me and than helped me test the app._


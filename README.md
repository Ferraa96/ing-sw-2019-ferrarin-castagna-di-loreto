# Prova Finale Ingegneria del Software 2020

## Gruppo AM43


- ###   10567361    Alberto Ferrarin ([@Ferraa96](https://github.com/Ferraa96))<br>alberto.ferrarin@mail.polimi.it
- ###   10567369    Filiberto Castagna ([@FilibertoCastagna](https://github.com/FilibertoCastagna))<br>filiberto.castagna@mail.polimi.it
- ###   10575894    Luigi Di Loreto ([@luigidiloreto](https://github.com/luigidiloreto))<br>luigi.diloreto@mail.polimi.it

| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Advanced Gods | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

##The game

![](https://www.projectnerd.it/wp-content/uploads/2018/05/copertina.jpg)

Santorini is a turn-based strategy game, set on a 5x5 board. Each player has 2 workers on the board and the main movements are two and are, in order, move and build (from height 1 to height 4). The aim of the game is to bring a worker to a block at height 3. Each player also has a god card with a special power, which can be activated manually or automatically and allows you to change the order of actions in the turn, to add some, to block others from opponents or to add win conditions.

##JAR file

The jar file can be found [here](https://github.com/Ferraa96/ing-sw-2019-ferrarin-castagna-di-loreto/tree/master/DELIVERABLES/JAR)

##Server

The server can be run with

    java -jar Santorini.jar s port delay
If the `port` attribute is not specified, the server will run on the default port 10000

If the `delay` attribute (in seconds) is not specified, the delay will be the default (2 mins)

After at least 2 players are connected, the server waits for one other player for `delay` seconds before starting the game.

##Client (GUI version)
Double click on the jar file or

    java -jar Santorini.jar g

##Client (CLI version)

    java -jar Santorini.jar c
    
For a better game experience we suggest to run the client from a terminal that supports UTF-8 and ANSI escape
    
##Documentation

The complete documentation can be found [here](https://github.com/Ferraa96/ing-sw-2019-ferrarin-castagna-di-loreto/tree/master/DELIVERABLES/JavaDoc/index.html)

##UML

UML can be found [here](https://github.com/Ferraa96/ing-sw-2019-ferrarin-castagna-di-loreto/tree/master/DELIVERABLES/UML)

##Authors

* **[Alberto Ferrarin](https://github.com/Ferraa96)**
* **[Filiberto Castagna](https://github.com/FilibertoCastagna)**
* **[Luigi di Loreto](https://github.com/luigidiloreto)**
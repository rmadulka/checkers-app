---
geometry: margin=1in
---
# PROJECT Design Documentation

> _The following template provides the headings for your Design
> Documentation.  As you edit each section make sure you remove these
> commentary 'blockquotes'; the lines that start with a > character
> and appear in the generated PDF in italics._

## Team Information
* Team name: Checkm8
* Team members
  * Spencer Josi
  * Casper Loveless
  * Ryan Madulka
  * Alan Read

## Executive Summary

Our web-based checkers application will provide an interactive user experience as players can play against other online 
users. It will incorporate sign-in functionality as each user can specify who they want to play against, directing both 
users to a game page presenting a GUI with the game board. The checkers game will also have drag-and-drop piece 
capabilities as a user can submit their moves when they drop their piece in a valid position. Not only will users be 
able to play against other online users, but they can compete against an AI or can record games to replay move-by-move.

### Purpose
We want to develop a web application for all users to play checkers against other live opponents, abiding by the American
checkers rules and providing a fully functional server-client connection.


### Glossary and Acronyms
> _Provide a table of terms and acronyms._

| Term | Definition |
|------|------------|
| VO | Value Object |


## Requirements

The most essential components of Webcheckers is to have sign-in functionality, initiating games with other players, and
the ability to play a game of checkers according to the American Rules on a GUI with drag-and-drop pieces. In order to
adhere to the checkers rulebook, developing a system to thoroughly validate moves is mandatory as the code must review
moves made by the player and asses its validity once the user has submitted their turn. 

### Definition of MVP
The Minimum Value Product expresses the most essential requirements that must be developed
and included within the product in order for it to be satisfactory. 

### MVP Features
A user must be able to sign in and out of their account, and if they remain logged in they
can initiate a game with a selected online user. Both users will be transferred to a game board
screen consisting of all of the checker pieces, along with buttons giving them the option to resign the game
the game, backup their most recent move, or submit their valid turn. When done playing, users can sign out of their account.

### Roadmap of Enhancements

We have already completed sign-in functionality and the basic structure of the board, so our current sprint plan
is to complete valid piece movement, develop game structure as users must be able to submit their turn or resign,
and also to create sign-out functionality. We are expecting to finish the MVP within this sprint, as sprint 3 is
devoted to completing our enhancements where we want to implement an AI and a replay mode to record and playback previous games.

## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](DomainAnalysisTeam.png)

The Checkers WebApp is played by two Players on a single checkers GameBoard.
The GameBoard contains 64 Squares, with each having a Location and a Color(Dark/Light).
24 Pieces are initially placed on the GameBoard, and each Player takes control of 12.
One Piece can occupy one Square. Each Piece has a Color(Red/White) and can either 
be a Single Piece or a King Piece. A Player can either be a human Player or an AI Player, 
and a Player has the ability to replay a Game.

## Architecture and Design

This section describes the application architecture. 

In the application, our design focuses on utilizing MVC. The user is able to interact with everything in the view which 
includes the ftl, CSS and JavaScript. The controller consists of the two application tier classes PlayerLobby and 
GameLobby which are invoked by most of the UI tier handler classes. The model contains most of the move validation and stores the 
information related to players and the positions on the board. We designed the board using a 2d array so we could iterate
through to find specific pieces and to validate the moves of a piece. Furthermore, we implemented iterator to populate the 
board in the ftl so that the user could see and interact with a changing board. When implementing move validation, we decided
to use an abstract class that different move classes extended to make it easier to check for valid moves. We split this into
checking for a jump, a simple move, a backwards king move and a backwards king jump. By splitting these rules into
separate classes, we were able to follow high cohesion and polymorphism principles. This also made it easier to determine
if a move was legal since we could just call the abstract method to determine if the given move was valid based on our
rules. However, by doing this, we had to create a util tier class that contained static boolean methods that utilized 
the abstract class. This was not the best design choice but was the "cleanest" way we could think about going about 
validation. 

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a
browser.  The client-side of the UI is composed of HTML pages with
some minimal CSS for styling the page.  There is also some JavaScript
that has been provided to the team by the architect.

The server-side tiers include the UI Tier that is composed of UI Controllers and Views.
Controllers are built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are built using plain-old Java objects (POJOs).

Details of the components within these tiers are supplied below.


### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](WebAppInterface.png)
When the user first connects to the web application, they are sent to the home page. By clicking on the sign-in button,
on the top left of the screen, the user is then sent to the sign-in page in which they can then enter a username. 
Assuming the user inputs a valid username and they click on the sign-in button, they will be sent back to the home page. 
If the player enters an invalid username, the sign-in page will reload. Once they are back on the home page and the user 
chooses to play an online user, they will both be sent to the game screen. On the game page, each player takes turns making 
moves and the page updates after each move is made. Once the game is over, the player will be sent back to the homepage. 

### UI Tier
> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._


### Application Tier
Our application tier consists of two classes that are both intended to manage board logic along with providing 
services to the UI tier. PlayerLobby directly manages all online users and GameLobby manages users that are both
in a current game together, allowing UI classes to directly access user information as the application classes are implemented
within the UI tier. Both application classes include information such as the board, players, moves, and game statuses, 
allowing the UI tier to extract that data and have it visible to both users.
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._


### Model Tier
The root of our model tier is our board class which initiates our board as a 2-d array that is compiled of a variety of
types of objects. It is comprised of Spaces (which creates the layout of the board of dark and white tiles), Pieces (which
the users move diagonally on dark tiles utilizing different valid movement techniques in order to remove the opponent's 
gipieces through jumps), 
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._

### Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements. After completion of the Code metrics exercise, you
> will also discuss the resutling metric measurements.  Indicate the
> hot spots the metrics identified in your code base, and your
> suggested design improvements to address those hot spots._

## Testing
> _This section will provide information about the testing performed
> and the results of the testing._

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._

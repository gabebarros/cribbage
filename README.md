# Cribbage
To play the game, run the view class!

## Design
The source code for the project is split up into 3 main parts, the Model, View and Controller (And a seperate folder for unit tests).

### Model
The model contains several classes (including enums and interface types) that interact with each other to compose the backend of the program. ArrayLists were chosen to represent the hand/cardstacks because they can easily be sorted, and items can be removed from any given index. Here is an overview of some of the classes and design decisions that were made:
- Card: Card is composed of the Rank and Suit enums. It is implemented as a flyweight class, with a private contructor and public method to get cards from the store. Also implements the Comparable interface, helping to reduce duplicated code.
- Deck: Deck is composed of 52 cards. It also implements the iterable interface.
- Player: Player is composed of a name, a score, and their hand (an ArrayList of Cards). Player also implements the CpuStrategy interface, which defines the move a CPU player should make if the game mode is set to Easy or Hard (STRATEGY design pattern).
- Scorer: This class handles all the scoring logic for the game, including the show and play phases.
- Game: This class "brings everything together" in the model, and it is the main point of interaction for the model in the MVC design. Game utilizes the observer design pattern, and includes methods to notify observers when a change is made in the backend.

Enums: Rank, Suit, GameMode    

Interfaces: CpuStrategy, GameObserver

### View (AI Used)
The view contains the class View.java. The GUI is designed using Swing. This class also contains the main method for the program. View contains a constructor that creates panels for all of the GUI components, and assembles them into a cohesive game board. View also implements GameObserver, implementing the abstract methods to update the UI when a change is made in the backend. It also contains various methods to display information, such as the dealer indicator and the scoring summary that is displayed after the show phase

### Controller (AI Used)
The controller contains the class Controller.java. It essentially serves to be notified when a user interacts with the view, then make changes in the Model to reflect the user interaction. This is why its constructor takes Game and View objects as arguments, as it needs access to both. It also takes a GameMode enum, so it can choose between a random move or an optimized move for a CPU player if the mode is CPU_EASY or CPU_HARD, or wait for user input if the mode is PVP (STRATEGY design pattern). It also contains methods to play the crib phase, play the show phase, and start the game.

## Rules and Instructions
**How to Play:**
- Select a gamemode (i.e. PVP or CPU)
- Select a difficulty (if player vs cpu)
- Select name(s) for human player(s)
- Play cards to crib
- Take turns playing cards
- Avoid going over 31 in the play stack
- Play rounds until one player reaches a total score of 121

**Ways to Score:**
- Closest to 31 (+1 for closest, +2 for exact)
- Pairs equalling 15 (+2 per combo)
- One for his knob (+1 for starter card being a jack with same suit)
- Two for his heels (+2 for jack as starter card) - for dealer only
- Flushes (+5 for hand+starter, +5 for crib only, +4 for hand only)
- Runs (+1 per card in run)
- 3/4 of a kind(+6/12 respectively)
- Go! (+1 for last card being played without playstack exceeding 31)
- Pairs (+2 for each player playing the same card)
- Pair Royal (+6 for three consecutive same kind cards played)
- Double Pair Royal (+12 for four same rank cards played in a row)

**To Note:**
- If both players exceed 121 at the same time, the higher score wins
- Dealer switches every round

**_HAVE FUN!_**

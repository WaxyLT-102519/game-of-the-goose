# Class Notes
These notes detail all the problems I see in the original project code. I will
list each class and describe what's going on that would be worth fixing, as
well as if fixing it would reveal any lessons worth teaching to LinkedIn.
## Die
- This should just be a utility that exposes a `.roll()` function, no private
state or public setters (what does that even mean to have a setter?)

## Position
- Supposed to encapsulate a position on the board, but that could be done
just as well with a plain int (or even a byte, since there's only 63 spaces)
- The `int position` is `protected`, but every file is in the same package, so
it doesn't even matter
- There is a setter, but the value is accessed directly

## Player
- violates composition over inheritance with `Player extends Position`
- having players keep track of their own turns with a counter requires a
closer look, it might actually be a smart thing to do
  - since a players turn can be determined by an internal clock, to skip two
  turns just set `turn = 2`
  - violated with a public setter
- `.equals()` with no `.hashcode()`

## BoardSpace
- `BoardSpace extends Player` doesn't make any sense
  - I didn't even know that an abstract class could extend a concrete class
- `.onLanding()` has a really strange method signature.
  - `BoardSpace[] spaces` should be static always, since there's only ever one
  game board
  - `Player player1` is the one that gets acted on most of the time
  - `Player player2` is only important for the `WellPrisonSpace`
  - the method signature would imply that these arguments would be a totally
  valid call:
    - `space.onLanding(new BoardSpace[0], new WellPrisonSpace(), new BridgeSpace(), 6);`
- all spaces take an `int position`, but their position is immutable. Each space is
always in the same spot. concrete spaces assume this.
- all spaces take in a `turn` parameter in their constructor, set to one so that it can't
get up and walk away itself

## Concrete Spaces
### Plain Space
This doesn't exist. It should.

### BridgeSpace
- This space will always move the player to space 12, but assumes that its at space 6

### DeathSpace
- Always moves the player to the starting position

### GooseSpace
- `player1.setPosition(roll + player1.position)` implies that the player's initial
movement is done outside the `.onLanding()` method
- the switch case implies that a polymorphic solution is in order... but we're 
already using polymorphism?
- The space is responsible for checking that the player doesn't overstep the finish line

### MazeSpace
- This actually does the same thing as the `BridgeSpace` and `DeathSpace`. These spaces
all move the player to a specific spot. They could all be condensed into their own class,
like a `RelocatingSpace` that will take a target as state

### TavernSpace
- Skipping two turns is tricky
- `player1.setTurn(4)` implies that this space, as well as other mechanics are affecting
the player's turn. should not be so

### WellPrisonSpace
- there are lots of reasons why this should be split
  - The name implies this is for a `WellSpace` and a `PrisonSpace`
  - there are different logs if you're on the well or the prison
  - the positions of these spaces are hardcoded, and logic splits between them
- I wonder if it's even possible to avoid the hardcoded space numbers, since the
goose game is so rigid in its rules

## The_Goose_Game
This is the main driver, but it has a ton of logic in it.
- Here are some things the main driver is doing that it should not be
  - creating the game board
    - in a for loop i might add
    - in a void method too, could just return a `BoardSpace[]` array
  - checking for the special starting cases
    - checks the actual dice values, but checking the sum of the rolls would be much easier
  - terminal-based gameplay, even though this could be automated
  - checking if any player wins
    - checks if a player is on space 63
    - checks if they move past 63 and puts them back
  - violate liskov substitution with the `WellPrisonSpace` by checking for the player's
  position before calling the function
- not a problem with the main driver, but when calling `.onLanding()` on a non-`WellPrisonSpace`,
player1 is passed in twice
- Everything is in a really big do-while loop
- `.playTheBoard()` is only useful as a wrapper function because `.onLanding()` is not a
good method

# Core Issues
This section details the biggest things that are causing this program to suffer, and
what should be addressed in order to fix it
## Bad Inheritance Structure
It's deep and nonsensical. Somehow, a `WellPrisonSpace` is a `BoardSpace`, which is a `Player`
(who can't move), which is a `Position` (that has a name and landing callback). So each concrete
space is 4 layers deep in an inheritance structure.

The `onLanding()` method only works because we never found out how to break it, but because 
of the inheritance structure, the types of the allowed arguments can be completely nonsensical.

A player's position can be simply defined as an int representing an index of a space in an
immutable list of spaces. No need for the parent class.

What happens to a player should be determined by a callback on that space, so there is reason
to keep a parent `BoardSpace` with its implementors. However, some of the concrete classes could
be consolidated. For example, bridge, death, and maze all move a player to a set position on 
the board, so we could just turn that into a `RelocatingSpace`.

## Violates Liskov Substitution Principle
The `WellPrisonSpace` shows a clear violation of LSP, and a not-so-clear violation is in the
absence of a `StandardSpace` -- one that just does nothing. If we still need to use a switch
statement after creating a polymorphic structure, then why did we make it?

`.onLanding(BoardSpace[], Player, Player, int)` lies about what it really needs, since not
every space cares about player2 on landing. We also shouldn't be passing in the array of spaces,
since the game board should be a part of some global state. 

## Lack of Clear Boundaries
While the names of classes do clearly label the different parts of the game, their code leads
to a lot of confusion. Everyone is allowed to move players and change their turns. The main
driver needs to handle a lot of logic that could be further extracted into its own classes,
such as initializing the players and game board, checking if any player has won yet, or even
checking if a player rolled a special number on the first roll.

Seeing a messy implementation doesn't really lead me to the clean one so obviously. I still
have questions regarding who really has the right to do what. For example, who should be
responsible for knowing which players are on which spaces? Should a player know which space
it's on, or should a space know which player is on it? Or should neither know, and player
placement is handled by a separate object? It makes sense that spaces would be responsible
for the actions that happen to a player when they land there. but then how would be 
fix the `WellPrisonSpace`, which needs to know if a player is on the space and if another
is arriving?

Everyone ends up setting the player's position, and everyone can set the player's turn.
The turn order can be very complex. There should be one object responsible for tracking
player's positions and moving them, and everyone has to go through that object. Then there
should be another object solely focused on the turn order, and everyone needs to go through
there in order to affect who goes next.

# Going Forward
While not necessary for this specific project, I want to challenge myself to create a fully
flexible GooseGame. To me, this means:
- Any number of players
- Any number of spaces
- Any space can be a special space, not just the defaults
- Completely automatic gameplay after the start (no terminal prompt, just a game log)
- REST-lite: starting a game requires a JSON configuration, and the output of the game
is an object which could be sent through a REST api.
  - This will not be a spring boot app though, just a terminal app. But the inputs and
  outputs will behave like it

Here are some things that I would need to change in order to accommodate these new requirements:
- player turn cannot be determined by an internal countdown clock
- skipping turns needs to work differently
- naming players needs to be efficient
- having any number of spaces means we need to make sure that we account for the special
starting rules, since there are automatic wins in the original game.
  - Maybe make it so that goose spaces are still spread out evenly across the board, then
  just specify the spread
  - Allow users to customize the board before the game starts, and be able to see it.
  - allow users to set up the instant win conditions
- spaces do not know where they are on the game board. So when a space relocates a player,
that is adjustable through input
  - the landing callback needs to accommodate this
- There will be a brief setup portion where users will be able to configure the game using
terminal prompts. the end result of this initializer will be a record with the configurations.
  - or a couple records, it might not be convenient to store the whole game board here
- Create a GameBoard facade that will track the internal game state between turns. A game will
return a historical model of what happened on every turn and how they started and ended.
  - player positions before the dice roll
  - whose turn it was
  - what the dice roll was
  - the space they landed on
  - what happened to them on that space
  - player positions after the landing callback
- Now different parts of the game can all just add their own input to the global game state
without needing to all be capable of every action.
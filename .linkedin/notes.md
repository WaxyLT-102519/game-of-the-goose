## Die
- This should just be a utility that exposes a `.roll()` function, no private
state or public setters (what does that even mean?)

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

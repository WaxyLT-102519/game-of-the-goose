package archive;

import java.util.*;
public class The_Goose_Game 
{
	public static void main(String[] args)
	{
		//instantiate objects
		Scanner scan = new Scanner(System.in);
		ArrayList<Player> players = new ArrayList<Player>();
		Die die = new Die();
		
		//instantiating constants
		final BoardSpace[] spaces = new BoardSpace[64];
		
		for (int i = 0; i < spaces.length; i++)
		{
			makeTheBoard(spaces, i);
		}
		
		//declaring variables
		String response;
		String name;
		int amtOfPlayers;
		boolean on63;
		int die1;
		int die2;
		
		//instantiating variables
		System.out.println("How many players are there?");
		amtOfPlayers = scan.nextInt();
		
		//determining turn order
		for (int i = 0; i < amtOfPlayers; i++)
		{
			System.out.println("What is the name of player " + (i + 1) + "?");
			name = scan.next();
			players.add(new Player(name, 1, 0));
		}
		
		int i = 0;
		on63 = false;
		
		
		/*
		 *  crap (Starting roll)
		 */
		for (int j = 0; j < amtOfPlayers; j++)
		{
			players.get(j).setTurn(0);
			System.out.println("Make your first roll, " + players.get(j).getName() + "!");
			die1 = die.roll();
			die2 = die.roll();
			if ((die1 == 3 && die2 == 6) || (die1 == 6 && die2 == 3))
			{
				players.get(j).setPosition(26);
			}
			else if ((die1 == 4 && die2 == 5) || (die1 == 5 && die2 == 4))
			{
				players.get(j).setPosition(53);
			}
			else
			{
				players.get(j).setPosition((die1 + die2));
				playTheBoard(spaces,players.get(j),players.get(j),(die1 + die2));
			}
		}
		
		
		/*
		 * Main gameplay loop
		 * 
		 * while no one is on 63 
		 * {
		 * 	while player turn is true
		 *  {
		 *   rolls dice and moves
		 *   does the stuff on each space
		 *   turn set to false
		 *  }
		 * }
		 */
		
		do
		{
			int roll = die.roll() + die.roll();
			
			players.get(i).changeTurn();
			
			//making sure nobody is on space 63
			for (int j = 0; j < amtOfPlayers; j++)
			{
				if (players.get(j).position == 63)
				{
					on63 = true;
					break;
				}
			}
			
			//player starts their turn
			System.out.println(players.get(i));
			while (players.get(i).getTurn() == 0 && on63 == false)
			{
				
				System.out.println("Do you want to roll?");
				response = scan.next();
				if (response.equalsIgnoreCase("no"))
				{
					System.out.println("Too bad! You have to.");
				}

				players.get(i).setPosition(roll + players.get(i).position);
				
				System.out.println("You rolled a " + roll);
				if (players.get(i).position > 63)
				{
					players.get(i).setPosition(63 - (players.get(i).position - 63));
					System.out.println("You fool. You went past space 63. " + 
					"You're now on space " + players.get(i).position + ".");
				}
				
				
				/*
				 * Special board space stuff happens
				 */
				if (players.get(i).position == 31 ||
					players.get(i).position == 52)
				{//if they land on a well or prison then the method needs to find 
				 //who to compare to 
					for (int j = 0; j < amtOfPlayers; j++)
					{
						if (players.get(i).position == players.get(j).position)
						{
							playTheBoard(spaces, players.get(i), players.get(j), roll);
						}
					}
				}
				else
				{
					playTheBoard(spaces, players.get(i), players.get(i), roll);
				}
				players.get(i).changeTurn();
			}
			
			//changing the player
			if (i + 1 == amtOfPlayers && players.get(i).position != 63 && on63 == false)
			{
				i = 0;
			}
			else if (i < amtOfPlayers && players.get(i).position != 63 &&on63 == false)
			{
				i++;
			}
		} while (!on63); //game continues is no one has won
		
		
		/*
		 * Winning
		 * (the player with index i is the winner)
		 */
		
		System.out.println("Congradulations " + players.get(i).getName() + ", you win!" +
		"\nYour prize is an A on this project!");
		
	}
	
	/*
	 * Internal methods
	 */
	
	//populates the board with spaces
	public static void makeTheBoard(BoardSpace[] spaces, int spaceNum)
	{
		switch (spaceNum)
		{
		case 5: case 9: case 14: case 18: case 23: case 27: case 32: case 36: case 41:
		case 45: case 50: case 54: case 59:
			spaces[spaceNum] = new GooseSpace(spaceNum);
			break;
		
		case 6:
			spaces[spaceNum] = new BridgeSpace(spaceNum);
			break;
			
		case 19:
			spaces[spaceNum] = new TavernSpace(spaceNum);
			break;
			
		case 31: 
			spaces[spaceNum] = new WellPrisonSpace("Well", spaceNum);
			break;
			
		case 52:
			spaces[spaceNum] = new WellPrisonSpace("Prison", spaceNum);
			break;
			
		case 42:
			spaces[spaceNum] = new MazeSpace(spaceNum);
			break;
			
		case 58:
			spaces[spaceNum] = new DeathSpace(spaceNum);
		}
	}
	
	public static void playTheBoard(BoardSpace[] spaces, Player player1, Player player2, int roll)
	{
		switch (player1.position)
		{
		case 5: case 6: case 9: case 14: case 18: case 19: case 23: case 27: case 31:
		case 32:case 36: case 41: case 42: case 45: case 50: case 52: case 54: case 58: 
		case 59:
			spaces[player1.position].onLanding(spaces, player1, player2, roll);
			break;
		default:
			System.out.println("You landed on space " + player1.position);
		}
	}
}

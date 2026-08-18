package com.luke.personal.archive;

public class WellPrisonSpace extends BoardSpace
{
	public WellPrisonSpace(String name, int position) 
	{
		super(name, 1, position);
	}

	@Override
	public void onLanding(BoardSpace[] spaces, Player player1, Player player2, int roll)
	{
		/*
		 * If you land here and no one is on it, you get 
		 * stuck.
		 * If you land and someone is on, they leave and
		 * you take their place.
		 * 
		 * player1 is the person who is arriving on the space,
		 * player2 is the one who is potentially already on this space
		 */
		
		if (!(player1.equals(player2)))
		{//if someone is there
			player2.setTurn(1);
			player1.setTurn(Integer.MAX_VALUE);
			
			System.out.println("It's your lucky day, " + player2.getName() + ". " + player1.getName() + " came to get you out! Looks like their stuck now though.");
		}
		else 
		{//if they are not there
			player1.setTurn(Integer.MAX_VALUE);
			
			if (player1.position == 31)
			{
				System.out.println("Thirsty? maybe if you want a drink from the well, you should just use the bucket." +
								   "\nYou're stuck here until someone comes to get you out.");
			}
			else if (player1.position == 52)
			{
				System.out.println("You slimy criminal. What did you to end up in there?" +
								   "\nYou're stuck here until someone comes to get you out.");
			}
		}
	}
}

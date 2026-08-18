package com.luke.personal.archive;

public class TavernSpace extends BoardSpace
{
	public TavernSpace(int position) 
	{
		super("Tavern", 1, position);
	}

	@Override
	public void onLanding(BoardSpace[] spaces, Player player1, Player player2, int roll)
	{
		//lose 2 turns
		if (player1.getTurn() == 0)
		{
			player1.setTurn(4);
			System.out.println("I guess it wouldn't hurt to take a break" +
							   " at the tavern. Just remember your fake ID." +
							   "\nYou lose two turns.");
		}
		else
		{
			player1.changeTurn();
			System.out.println("You just keep having fun in that tavern!");
		}
	}
}

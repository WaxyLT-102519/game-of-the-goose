package com.luke.personal.archive;

public class DeathSpace extends BoardSpace
{
	public DeathSpace(int position) 
	{
		super("Death", 1, position);
	}
	
	@Override
	public void onLanding(BoardSpace[] spaces, Player player1, Player player2, int roll)
	{
		//takes you to space 1
		player1.setPosition(0);
		System.out.println("You died (and you didn't have a gold SCAR so no revive)." +
		" Head back to the beginning.");
	}
}

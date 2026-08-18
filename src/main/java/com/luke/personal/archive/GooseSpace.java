package com.luke.personal.archive;

public class GooseSpace extends BoardSpace
{
	public GooseSpace(int position) 
	{
		super("Goose", 1, position);
	}
	
	@Override
	public void onLanding(BoardSpace[] spaces, Player player1, Player player2, int roll)
	{
		//takes what you roll and you roll again
		player1.setPosition(roll + player1.position);
		System.out.println("You landed on a Goose! Double your roll and keep going!");
		
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
		
		if (player1.position > 63)
		{
			player1.setPosition(63 - (player1.position - 63));
			System.out.println("You fool. You went past space 63. " + 
			"You're now on space " + player1.position + ".");
		}
	}
}

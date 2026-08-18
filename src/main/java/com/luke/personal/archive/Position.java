package com.luke.personal.archive;

/*
 * This class represents the space that the player occupies
 * on the board.
 */
public class Position
{
	protected int position;
	
	public Position(int position)
	{
		this.position = position;
	}
	
	public void setPosition(int move)
	{
		this.position = move;
	}
}

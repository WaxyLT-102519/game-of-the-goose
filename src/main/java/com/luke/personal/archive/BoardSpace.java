package archive;

/*
 * This class represents the different effects of the 
 * spaces on the board
 */
public abstract class BoardSpace extends Player
{
	public abstract void onLanding(BoardSpace[] spaces, Player player1, Player player2, int roll);
	
	//player1 will be the one who has stuff happen to them. player2 is only there for
	//the Well and Prison spaces.
	public BoardSpace(String name, int turn, int position)
	{
		super(name, turn, position);
	}
}

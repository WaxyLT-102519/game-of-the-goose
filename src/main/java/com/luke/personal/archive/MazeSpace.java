package archive;

public class MazeSpace extends BoardSpace
{
	public MazeSpace(int position) 
	{
		super("Maze", 1, position);
	}

	@Override
	public void onLanding(BoardSpace[] spaces, Player player1, Player player2, int roll)
	{
		//move from space 42 to space 30
		player1.setPosition(30);
		System.out.println("Did you get lost in the maze? Head back to space 30.");
	}
}

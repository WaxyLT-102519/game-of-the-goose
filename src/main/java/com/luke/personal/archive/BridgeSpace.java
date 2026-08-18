package archive;

public class BridgeSpace extends BoardSpace
{
	
	public BridgeSpace(int position) 
	{
		super("Bridge", 1, position);
	}
	
	@Override
	public void onLanding(BoardSpace[] spaces, Player player1, Player player2, int roll)
	{
		//moves to space 12
		player1.setPosition(12);
		System.out.println("You landed on the Bridge! move to space 12!");
	}
	
	
}

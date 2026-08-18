package archive;

public class Player extends Position
{
	private String name;
	private int turn; //0 means its your turn, greater than 0 means it isn't
	
	public Player(String name, int turn, int position)
	{
		super(position);
		this.name = name;
		this.turn = turn;
	}
	
	/*
	 * Getters
	 */
	
	public String getName()
	{
		return this.name;
	}
	
	public int getTurn()
	{
		return this.turn;
	}
	
	public String toString()
	{
		if (this.turn == 0)
		{
			return "Go " + this.name + ", it's your turn!" +
				   "\nYou're currently on space " + this.position;
		}
		else
		{
			return "Looks like " + this.name + " is stuck " +
				   "on space " + this.position;
		}
	}
	
	/*
	 * Setters
	 */
	
	public void changeTurn()
	{
		if (this.turn > 0)
		{
			//if it's not their turn, then the timer counts down until it is.
			this.turn--;
		}
		else if (this.turn == 0)
		{
			//if it is their turn, then it is set to a number that will make it not their turn
			this.turn = 1;
		}
	}
	
	public void setTurn(int turn)
	{
		this.turn = turn;
	}
	
	/*
	 * Comparisons
	 */
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Player)
		{
			Player player = (Player)o;
			if (this.position == player.position &&
				this.name.equals(player.getName()) &&
				this.turn == player.getTurn())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}

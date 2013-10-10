package com.forrestpruitt.texas;
import java.util.ArrayList;

public class Player {
	private int numOfChips; //The Number of chips the player has
	private String name; //The Player's Name
	private Hand hand; //The Player's Hand
	private int id; //Unique ID for player
	private int numOfWins;

	private boolean isAllIn = false;
	
	public int totalBetThisRound;
	
	public Player(int startingChips, String playerName, int id)
	{
		this.numOfChips = startingChips;
		this.name=playerName;
		this.id = id;
		totalBetThisRound = 0;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getNumOfChips()
	{
		return this.numOfChips;
	}
	public int getID()
	{
		return this.id;
	}
	public void setHand(Hand hand)
	{
		this.hand = hand;
	}
	public Hand getHand()
	{
		return this.hand;
	}
	
	public void winChips(int chipsWon)
	{
		this.numOfChips += chipsWon;
	}
	
	/**
	 * Add some chips to the current pot.
	 * @param chipsToBet the number of chips to bet.
	 * @return true if bet succeeded, false if bet fails
	 */
	public boolean betChips(int chipsToBet)
	{
		if(numOfChips >= chipsToBet)
		{
			totalBetThisRound += chipsToBet;

			Game.chipsInPot += chipsToBet;
			this.numOfChips -= chipsToBet;
			return true;
		}
		else
			return false;
	}

	public void refundChips(int chipsToRefund)
	{
		Game.chipsInPot -= chipsToRefund;
		this.numOfChips += chipsToRefund;

		totalBetThisRound -= chipsToRefund;
	}
	
	public void winGame()
	{
		numOfWins++;
	}
	public int getWins()
	{
		return numOfWins;
	}

	public int getTotalBetThisRound()
	{
		return totalBetThisRound;
	}

	public void clearTotalBetThisRound()
	{
		totalBetThisRound = 0;
	}

	public boolean isAllIn()
	{
		return isAllIn;
	}

	public void setIsAllIn(boolean in)
	{
		isAllIn = in;
	}
}

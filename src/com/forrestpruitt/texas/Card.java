package com.forrestpruitt.texas;

public class Card
{
	private final Suit suit;
	private final Rank rank;
	private final int id;
	
	public enum Suit
	{
		HEART, DIAMOND, CLUB, SPADE;
	}
	public enum Rank
	{
		TWO, THREE, FOUR, FIVE, SIX,SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
	}
	
	public Card(Rank rank, Suit suit, int id) {
        this.rank = rank;
        this.suit = suit;
        this.id = id;
    }
	
	public Rank getRank()
	{
		return this.rank;
	}
	
	public Suit getSuit()
	{
		return this.suit;
	}
	
	public int getID()
	{
		return this.id;
	}
	
	public String toString()
	{
		return this.rank+" of "+this.suit+"S, with ID="+this.id;
	}
}

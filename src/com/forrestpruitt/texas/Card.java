package com.forrestpruitt.texas;
import java.util.Collections;

public class Card implements Comparable
{
	private final Suit suit;
	private final Rank rank;
	private final int id;
	private String imageURL ="images/";
	
	//Every card has a suit of one of these.
	public enum Suit
	{
		HEART, DIAMOND, CLUB, SPADE;
	}
	
	//Every card has a rank of one of these.
	public enum Rank
	{
		TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;
	}
	
	/**
	 * Construct a Card object with a rank, suit, and unique ID.
	 * @param rank The rank of the card (eg, 2-10, J, Q, K, A)
	 * @param suit The suit of the card (eg, Heart, Diamond, Club, Spade)
	 * @param id A Unique ID for each card ranging from 0-51
	 */
	public Card(Rank rank, Suit suit, int id) {
        this.rank = rank;
        this.suit = suit;
        this.id = id;
    }
	/**
	 * Get the Rank of this card.
	 * @return The rank of the card.
	 */
	public Rank getRank()
	{
		return this.rank;
	}
	/**
	 * Get the Suit for this card.
	 * @return the suit of the card
	 */
	public Suit getSuit()
	{
		return this.suit;
	}
	/**
	 * Get the unique ID for this card.
	 * @return the unique ID for the card
	 */
	public int getID()
	{
		return this.id;
	}
	/**
	 * @return A String Representation of the card in the form "RANK of SUITs, with ID=ID"
	 */
	@Override
	public String toString()
	{
		return "[" + this.rank + " of "+ this.suit + "S]";
		//+"S, with ID="+this.id;
	}

	/**
	* @param otherObject, the (Card)object to compare with this one
	* @return an int, positive if this card > otherCard, 0 if equal, negative.
	*/
	public int compareTo(Object otherObject)
	{
		Card otherCard = (Card)otherObject;
		int thisCardRank  = this.getRank().ordinal();
		int otherCardRank = otherCard.getRank().ordinal();
		return thisCardRank - otherCardRank;
	}
	public void setURL(int id)
	{
		imageURL=imageURL+Integer.toString(id)+".png";
	}
	public String getURL()
	{
		return imageURL;
	}
}

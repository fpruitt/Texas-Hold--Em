package com.forrestpruitt.texas;

import java.util.ArrayList;

import com.forrestpruitt.texas.Card.Rank;
import com.forrestpruitt.texas.Card.Suit;

public class Hand
{
	ArrayList<Card> hand = new ArrayList();
	
	public void insertCard(Card card)
	{
		hand.add(card);
	}
	
	//Removal = O(n)
	public void discardCard(int cardID)
	{
		for(Card card : hand)
		{
			if(cardID == card.getID())
			{
				DiscardPile.pile.add(card);
				hand.remove(card);
			}
		}
	}
	
	public String toString()
	{
		String returnString="";
		for(Card card : this.hand)
		{
			returnString+=card.toString()+"\n";
		}
		return returnString;
	}
}

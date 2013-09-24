package com.forrestpruitt.texas;

import java.util.ArrayList;

class DiscardPile
{
	static ArrayList<Card> pile = new ArrayList<Card>();
	
	public void add(Card card)
	{
		pile.add(card);
	}
	
	public String toString()
	{
		String returnString = "";
		for(Card card : pile)
		{
			returnString += card.toString()+"\n";
		}
		return returnString;
	}
}

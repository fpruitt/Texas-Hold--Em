package com.forrestpruitt.texas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.forrestpruitt.texas.Card.Rank;
import com.forrestpruitt.texas.Card.Suit;

public class Deck
{
	LinkedList<Card> deck = new LinkedList<Card>();
	
	public Deck()
	{
		int total = 0;
		//Fill the deck up with cards
		for(Suit suit : Suit.values())
		{
			for(Rank rank : Rank.values())
			{
				Card newCard = new Card(rank,suit,total);
				deck.add(newCard);
				total++;
				System.out.println(newCard);
			}
		}
	}
	
	//Use Java's Built-In Shuffle to shuffle the cards
	public void shuffle()
	{
		Collections.shuffle(deck);
	}
	
	//Returns an array list of hands
	public ArrayList<Hand> deal(int numOfCards, int numOfHands)
	{
		ArrayList<Hand> hands= new ArrayList();
		for(int i = 0; i<numOfHands; i++)
		{	
			Hand hand = new Hand();
			for(int j = 0; j<numOfCards; j++)
			{
				hand.hand.add(deck.pop());
			}
			hands.add(hand);
		}
		return hands;
	}
	
	
	
	

	// Return a string formatted as such:
	//  THREE of CLUBS
	//  TWO of DIAMONDS
	//  ACE of SPADES
	//  ...etc
	public String toString()
	{
		String returnString="";
		for (Card card : this.deck) 
		{
			returnString+=card.toString()+"\n";
		}
		return returnString;
	}
	
	
	
}

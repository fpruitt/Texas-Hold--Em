package com.forrestpruitt.texas;
import java.util.Collections;
import java.util.ArrayList;
import com.forrestpruitt.texas.Card.Rank;
import com.forrestpruitt.texas.Card.Suit;

public class PokerHand implements Comparable
{
	private HandType handType;
	private ArrayList<Card> cards;

	public enum HandType
	{
		HIGHCARD, ONEPAIR, TWOPAIR, THREEOFAKIND, STRAIGHT, FLUSH, FULLHOUSE, FOUROFAKIND, STRAIGHTFLUSH;
	}

	public PokerHand(ArrayList<Card> cardsIn)
	{
		cards = new ArrayList<Card>(cardsIn);
		Collections.sort(cards);
		setHandType();
	}

	public void setHandType()
	{
		if((getNumUniqueSuits() == 1) && (getHighCard().getRank().ordinal() - getLowCard().getRank().ordinal() == 4))
		{
			handType = HandType.STRAIGHTFLUSH;
			return;
		}

		if((getNumUniqueRanks() == 2) && ((getNumOccurencesOfRank(cards.get(0).getRank()) == 4) || (getNumOccurencesOfRank(cards.get(1).getRank()) == 4)))
		{
			handType = HandType.FOUROFAKIND;
			return;
		}

		if(getNumUniqueRanks() == 2)
		{
			handType = HandType.FULLHOUSE;
			return;
		}

		if(getNumUniqueSuits() == 1)
		{
			handType = HandType.FLUSH;
			return;
		}

		if((getHighCard().getRank().ordinal() - getLowCard().getRank().ordinal() == 4) && (getNumUniqueRanks() == 5))
		{
			handType = HandType.STRAIGHT;
			return;
		}

		if((getNumOccurencesOfRank(cards.get(0).getRank()) == 3) || (getNumOccurencesOfRank(cards.get(1).getRank()) == 3) || (getNumOccurencesOfRank(cards.get(2).getRank()) == 3))
		{
			handType = HandType.THREEOFAKIND;
			return;
		}

		if(getNumUniqueRanks() == 3)
		{
			handType = HandType.TWOPAIR;
			return;
		}

		if(getNumUniqueRanks() == 4)
		{
			handType = HandType.ONEPAIR;
			return;
		}

		handType = HandType.HIGHCARD;
		return;


	}

	public int getNumUniqueRanks()
	{
		ArrayList<Rank> ranks = new ArrayList<Rank>();
		for(Card card: cards)
		{
			if(!ranks.contains(card.getRank()))
			{
				ranks.add(card.getRank());
			}
		}
		return ranks.size();
	}

	public HandType getHandType()
	{
		return handType;
	}

	public int getNumUniqueSuits()
	{
		ArrayList<Suit> suits = new ArrayList<Suit>();
		for(Card card: cards)
		{
			if(!suits.contains(card.getSuit()))
			{
				suits.add(card.getSuit());
			}
		}
		return suits.size();
	}

	public int getNumOccurencesOfRank(Rank rankToCheck)
	{
		int count = 0;
		for(Card card: cards)
		{
			if(card.getRank() == rankToCheck)
			{
				count++;
			}
		}
		return count;
	}

	public Card getHighCard()
	{
		return cards.get(cards.size()-1);
	}

	public Card getLowCard()
	{
		return cards.get(0);
	}

	public ArrayList<Card> getCards()
	{
		return cards;
	}

	public int compareTo(PokerHand otherPokerHand)
	{
		int thisPokerHandRank  = this.getHandType().ordinal();
		int otherPokerHandRank = otherPokerHand.getHandType().ordinal();
		return thisPokerHandRank - otherPokerHandRank;
	}

	public int compareTo(Object otherObject)
	{
		PokerHand otherPokerHand = (PokerHand)otherObject;
		int thisPokerHandRank  = this.getHandType().ordinal();
		int otherPokerHandRank = otherPokerHand.getHandType().ordinal();
		return thisPokerHandRank - otherPokerHandRank;
	}

	public String toString()
	{
		return "[" + cards.get(0) + ", " + cards.get(1) + ", " + cards.get(2) + ", " + cards.get(3) + ", " + cards.get(4) + "]";
	}
}
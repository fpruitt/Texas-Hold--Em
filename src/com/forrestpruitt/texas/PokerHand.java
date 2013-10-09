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
		//Each of these 'if' conditions sets the handType and returns if it detects the associated handType.
		//The order of these conditions is important as I've simplified some of the logic based on
		//which types of hands have already been ruled out, i.e. the condition defining that handType
		//has been cleared without returning.

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

	//Finds and returns the number of unique ranks present in the PokerHand
	//Helps with determining handType
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

	//Finds and returns the number of unique suits present in the PokerHand
	//Helps with determining handType
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

	//Finds and returns the number of occurences of a specified rank in the PokerHand
	//Helps with determining handType
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

	//Returns the last element in the PokerHand, which has already been sorted according to rank
	public Card getHighCard()
	{
		return cards.get(cards.size()-1);
	}

	//Returns the first element in the PokerHand, which has already been sorted according to rank
	public Card getLowCard()
	{
		return cards.get(0);
	}

	public HandType getHandType()
	{
		return handType;
	}

	public ArrayList<Card> getCards()
	{
		return cards;
	}

	//Returns a positive int if thisCard > other card, 0 if equal, and a negative int otherwise
	public int breakTieHighCard(PokerHand otherPokerHand)
	{
		int index = 4;
		Card thisCard = this.getCards().get(index);
		Card otherCard = otherPokerHand.getCards().get(index);
		while(thisCard.getRank() == otherCard.getRank() && index >= 0)
		{
			thisCard = this.getCards().get(index);
			otherCard = otherPokerHand.getCards().get(index);
			index--;
		}
		return thisCard.getRank().ordinal() - otherCard.getRank().ordinal();
	}

	//Necessary for implementing 'Comparable' so that PokerHands can be sorted by Collections.sort
	//The PokerHandEvaluator sorts PokerHand objects to find the best (greatest handType) one
	public int compareTo(Object otherObject)
	{
		PokerHand otherPokerHand = (PokerHand)otherObject;
		int thisPokerHandRank  = this.getHandType().ordinal();
		int otherPokerHandRank = otherPokerHand.getHandType().ordinal();
		//If the handTypes of the hands are not the same
		if(thisPokerHandRank != otherPokerHandRank)
		{
			return thisPokerHandRank - otherPokerHandRank;
		}
		else //Tiebreakers
		{
			if(this.getHandType() == handType.HIGHCARD)
			{
				return breakTieHighCard(otherPokerHand);	
			}

			if(this.getHandType() == handType.ONEPAIR)
			{
				boolean found;
				int index;
				Rank thisPairRank = Rank.TWO;
				Rank otherPairRank = Rank.TWO;
				
				index = 4;
				found = false;
				//Find the rank of this PokerHand's pair
				while(!found)
				{
					if(this.getNumOccurencesOfRank(this.getCards().get(index).getRank()) == 2)
					{
						thisPairRank = this.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				index = 4;
				found = false;
				//Find the rank of the other PokerHand's pair
				while(!found)
				{
					if(otherPokerHand.getNumOccurencesOfRank(otherPokerHand.getCards().get(index).getRank()) == 2)
					{
						otherPairRank = otherPokerHand.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				//If the pairs are different ranks, return the winner
				if(thisPairRank.ordinal() != otherPairRank.ordinal())
				{
					return thisPairRank.ordinal() - otherPairRank.ordinal();
				}
				else //use high card to determine winner
				{
					return breakTieHighCard(otherPokerHand);
				}
			}

			if(this.getHandType() == handType.TWOPAIR)
			{
				boolean found;
				int index;
				Rank thisHighPairRank = Rank.TWO;
				Rank thisLowPairRank = Rank.TWO;
				Rank otherHighPairRank = Rank.TWO;
				Rank otherLowPairRank = Rank.TWO;
				
				index = 4;
				found = false;
				//Find the rank of this hand's first pair
				while(!found)
				{
					if(this.getNumOccurencesOfRank(this.getCards().get(index).getRank()) == 2)
					{
						thisHighPairRank = this.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				index = 4;
				found = false;
				//Find the rank of this hand's second pair
				while(!found)
				{
					if(this.getNumOccurencesOfRank(this.getCards().get(index).getRank()) == 2
					   && this.getCards().get(index).getRank() != thisHighPairRank)
					{
						if(this.getCards().get(index).getRank().ordinal() > thisHighPairRank.ordinal())
						{
							thisLowPairRank = thisHighPairRank;
							thisHighPairRank = this.getCards().get(index).getRank();
						}
						else
						{
							thisLowPairRank = this.getCards().get(index).getRank();
						}
						found = true;
					}
					index--;
				}

				index = 4;
				found = false;
				//Find the rank of the other hand's first pair
				while(!found)
				{
					if(otherPokerHand.getNumOccurencesOfRank(otherPokerHand.getCards().get(index).getRank()) == 2)
					{
						otherHighPairRank = otherPokerHand.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				index = 4;
				found = false;
				//Find the rank of the other hand's second pair
				while(!found)
				{
					if(otherPokerHand.getNumOccurencesOfRank(otherPokerHand.getCards().get(index).getRank()) == 2
					   && otherPokerHand.getCards().get(index).getRank() != otherHighPairRank)
					{
						if(otherPokerHand.getCards().get(index).getRank().ordinal() > otherHighPairRank.ordinal())
						{
							otherLowPairRank = otherHighPairRank;
							otherHighPairRank = otherPokerHand.getCards().get(index).getRank();
						}
						else
						{
							otherLowPairRank = otherPokerHand.getCards().get(index).getRank();
						}
						found = true;
					}
					index--;
				}

				//Compare high pairs' ranks and return winner
				if(thisHighPairRank.ordinal() != otherHighPairRank.ordinal())
				{
					return thisHighPairRank.ordinal() - otherHighPairRank.ordinal();
				}
				//If high pairs' ranks are the same, compare low pairs' ranks and return winner
				else if(thisLowPairRank.ordinal() != otherLowPairRank.ordinal())
				{
					return thisLowPairRank.ordinal() - otherLowPairRank.ordinal();
				}
				//If both high and low pairs are the same ranks, use high card
				else
				{
					return breakTieHighCard(otherPokerHand);
				}

			}

			if(this.getHandType() == handType.THREEOFAKIND)
			{
				boolean found;
				int index;
				Rank thisTrioRank = Rank.TWO;
				Rank otherTrioRank = Rank.TWO;
				
				index = 4;
				found = false;
				//Find the rank of this PokerHand's pair
				while(!found)
				{
					if(getNumOccurencesOfRank(this.getCards().get(index).getRank()) == 3)
					{
						thisTrioRank = this.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				index = 4;
				found = false;
				//Find the rank of the other PokerHand's pair
				while(!found)
				{
					if(otherPokerHand.getNumOccurencesOfRank(otherPokerHand.getCards().get(index).getRank()) == 3)
					{
						otherTrioRank = otherPokerHand.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				//If the pairs are different ranks, return the winner
				if(thisTrioRank.ordinal() != otherTrioRank.ordinal())
				{
					return thisTrioRank.ordinal() - otherTrioRank.ordinal();
				}
				else //use high card to determine winner
				{
					return breakTieHighCard(otherPokerHand);
				}	
			}

			if(this.getHandType() == handType.STRAIGHT)
			{
				return breakTieHighCard(otherPokerHand);
			}

			if(this.getHandType() == handType.FLUSH)
			{
				return breakTieHighCard(otherPokerHand);
			}

			if(this.getHandType() == handType.FULLHOUSE)
			{
				boolean found;
				int index;
				Rank thisTrioRank = Rank.TWO;
				Rank otherTrioRank = Rank.TWO;
				
				index = 4;
				found = false;
				//Find the rank of this PokerHand's pair
				while(!found)
				{
					if(getNumOccurencesOfRank(this.getCards().get(index).getRank()) == 3)
					{
						thisTrioRank = this.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				index = 4;
				found = false;
				//Find the rank of the other PokerHand's pair
				while(!found)
				{
					if(otherPokerHand.getNumOccurencesOfRank(otherPokerHand.getCards().get(index).getRank()) == 3)
					{
						otherTrioRank = otherPokerHand.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				//If the pairs are different ranks, return the winner
				if(thisTrioRank.ordinal() != otherTrioRank.ordinal())
				{
					return thisTrioRank.ordinal() - otherTrioRank.ordinal();
				}
				else //use high card to determine winner
				{
					return breakTieHighCard(otherPokerHand);
				}	
			}

			if(this.getHandType() == handType.FOUROFAKIND)
			{
				boolean found;
				int index;
				Rank thisQuartetRank = Rank.TWO;
				Rank otherQuartetRank = Rank.TWO;
				
				index = 4;
				found = false;
				//Find the rank of this PokerHand's pair
				while(!found)
				{
					if(getNumOccurencesOfRank(this.getCards().get(index).getRank()) == 4)
					{
						thisQuartetRank = this.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				index = 4;
				found = false;
				//Find the rank of the other PokerHand's pair
				while(!found)
				{
					if(otherPokerHand.getNumOccurencesOfRank(otherPokerHand.getCards().get(index).getRank()) == 4)
					{
						otherQuartetRank = otherPokerHand.getCards().get(index).getRank();
						found = true;
					}
					index--;
				}

				//If the pairs are different ranks, return the winner
				if(thisQuartetRank.ordinal() != otherQuartetRank.ordinal())
				{
					return thisQuartetRank.ordinal() - otherQuartetRank.ordinal();
				}
				else //use high card to determine winner
				{
					return breakTieHighCard(otherPokerHand);
				}	

			}

			if(this.getHandType() == handType.STRAIGHTFLUSH)
			{
				return breakTieHighCard(otherPokerHand);
			}

			return 0;
		}


	}

	public String toString()
	{
		return "[" + cards.get(0) + ", " + cards.get(1) + ", " + cards.get(2) + ", " + cards.get(3) + ", " + cards.get(4) + "]";
	}
}
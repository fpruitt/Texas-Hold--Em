package com.forrestpruitt.texas;

import java.util.ArrayList;
import java.util.Collections;

import com.forrestpruitt.texas.PokerHand.HandType;
import com.forrestpruitt.texas.Card.Rank;
import com.forrestpruitt.texas.Card.Suit;

public class PokerHandEvaluator
{
	public ArrayList<Card> cardList = new ArrayList<Card>();
	public ArrayList<PokerHand> pokerHandList = new ArrayList<PokerHand>();

	public PokerHandEvaluator(ArrayList<Card> cardsIn)
	{
		cardList = cardsIn;
		pokerHandList = generatePokerHandList();
		Collections.sort(pokerHandList);
	}

	public ArrayList<PokerHand> generatePokerHandList()
	{
		ArrayList<PokerHand> pokerHandsToReturn = new ArrayList<PokerHand>();

		ArrayList<Card> dynamicPokerHand = new ArrayList<Card>();

		dynamicPokerHand.add(0, cardList.get(0)); dynamicPokerHand.add(1, cardList.get(1));
		dynamicPokerHand.add(2, cardList.get(2)); dynamicPokerHand.add(3, cardList.get(3));
		dynamicPokerHand.add(4, cardList.get(4)); 
		PokerHand pokerHand0 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand0);
		//System.out.println(pokerHand0);

		dynamicPokerHand.set(4, cardList.get(5));
		PokerHand pokerHand1 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand1);
		//System.out.println(pokerHand1);

		dynamicPokerHand.set(4, cardList.get(6));
		PokerHand pokerHand2 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand2);
		//System.out.println(pokerHand2);

		dynamicPokerHand.set(3, cardList.get(4));
		PokerHand pokerHand3 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand3);
		//System.out.println(pokerHand3);

		dynamicPokerHand.set(4, cardList.get(6));
		PokerHand pokerHand4 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand4);
		//System.out.println(pokerHand4);

		dynamicPokerHand.set(3, cardList.get(5));
		PokerHand pokerHand5 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand5);
		//System.out.println(pokerHand5);

		dynamicPokerHand.set(2, cardList.get(3));
		dynamicPokerHand.set(3, cardList.get(4));
		dynamicPokerHand.set(4, cardList.get(5));
		PokerHand pokerHand6 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand6);
		//System.out.println(pokerHand6);

		dynamicPokerHand.set(4, cardList.get(6));
		PokerHand pokerHand7 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand7);
		//System.out.println(pokerHand7);

		dynamicPokerHand.set(3, cardList.get(5));
		PokerHand pokerHand8 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand8);
		//System.out.println(pokerHand8);

		dynamicPokerHand.set(2, cardList.get(4));
		PokerHand pokerHand9 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand9);
		//System.out.println(pokerHand9);

		dynamicPokerHand.set(1, cardList.get(2));
		dynamicPokerHand.set(2, cardList.get(3));
		dynamicPokerHand.set(3, cardList.get(4));
		dynamicPokerHand.set(4, cardList.get(5));
		PokerHand pokerHand10 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand10);
		//System.out.println(pokerHand10);

		dynamicPokerHand.set(4, cardList.get(6));
		PokerHand pokerHand11 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand11);
		System.out.println(pokerHand11);

		dynamicPokerHand.set(3, cardList.get(5));
		PokerHand pokerHand12 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand12);
		//System.out.println(pokerHand12);

		dynamicPokerHand.set(2, cardList.get(4));
		PokerHand pokerHand13 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand13);
		//System.out.println(pokerHand13);

		dynamicPokerHand.set(1, cardList.get(3));
		PokerHand pokerHand14 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand14);
		//System.out.println(pokerHand14);

		dynamicPokerHand.set(0, cardList.get(1));
		dynamicPokerHand.set(1, cardList.get(2));
		dynamicPokerHand.set(2, cardList.get(3));
		dynamicPokerHand.set(3, cardList.get(4));
		dynamicPokerHand.set(4, cardList.get(5));
		PokerHand pokerHand15 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand15);
		//System.out.println(pokerHand15);

		dynamicPokerHand.set(4, cardList.get(6));
		PokerHand pokerHand16 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand16);
		//System.out.println(pokerHand16);

		dynamicPokerHand.set(3, cardList.get(5));
		PokerHand pokerHand17 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand17);
		//System.out.println(pokerHand17);

		dynamicPokerHand.set(2, cardList.get(4));
		PokerHand pokerHand18 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand18);
		//System.out.println(pokerHand18);

		dynamicPokerHand.set(1, cardList.get(3));
		PokerHand pokerHand19 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand19);
		//System.out.println(pokerHand19);

		dynamicPokerHand.set(0, cardList.get(2));
		PokerHand pokerHand20 = new PokerHand(dynamicPokerHand);
		pokerHandsToReturn.add(pokerHand20);
		//System.out.println(pokerHand20);

		return pokerHandsToReturn;
	}

	public String toString()
	{
		return "" + pokerHandList.get(0).getHandType() + ", " + pokerHandList.get(1).getHandType()
		+ ", " + pokerHandList.get(2).getHandType() + ", " + pokerHandList.get(3).getHandType()
		+ ", " + pokerHandList.get(4).getHandType() + ", " + pokerHandList.get(5).getHandType()
		+ ", " + pokerHandList.get(6).getHandType() + ", " + pokerHandList.get(7).getHandType()
		+ ", " + pokerHandList.get(8).getHandType() + ", " + pokerHandList.get(9).getHandType()
		+ ", " + pokerHandList.get(10).getHandType() + ", " + pokerHandList.get(11).getHandType()
		+ ", " + pokerHandList.get(12).getHandType() + ", " + pokerHandList.get(13).getHandType()
		+ ", " + pokerHandList.get(14).getHandType() + ", " + pokerHandList.get(15).getHandType()
		+ ", " + pokerHandList.get(16).getHandType() + ", " + pokerHandList.get(17).getHandType()
		+ ", " + pokerHandList.get(18).getHandType()
		+ ", " + pokerHandList.get(19).getHandType()
		+ ", " + pokerHandList.get(20).getHandType();

	}
	public int getHandValue()
	{
		int maxHandValue = 0;
		for(int i = 0; i < 21; i++)
		{
			if(pokerHandList.get(i).getHandType().ordinal() > maxHandValue)
			{
				maxHandValue = pokerHandList.get(i).getHandType().ordinal();
			}
			
		}
		return maxHandValue;
	}
	public String getBestHand()
	{
		String maxHand = pokerHandList.get(0).toString();
		int maxHandValue = 0;
		
		for(int i = 0; i < 21; i++)
		{
			if(pokerHandList.get(i).getHandType().ordinal() > maxHandValue)
			{
				maxHandValue = pokerHandList.get(i).getHandType().ordinal();
				maxHand = pokerHandList.get(i).getHandType().toString();
				
			}
			
		}
		return maxHand;
	}
}
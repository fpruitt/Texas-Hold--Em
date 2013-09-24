package com.forrestpruitt.texas;

import java.util.ArrayList;

public class Driver
{
	public static void main(String args[])
	{
		Deck deck = new Deck();
		System.out.println(deck.toString());
		deck.shuffle();
		System.out.println(deck.toString());
		ArrayList<Hand> hands = new ArrayList();
		hands = deck.deal(5, 5);
		for(Hand hand : hands)
		{
			System.out.println(hand.toString());
		}
		System.out.println(deck.toString());
	}
}

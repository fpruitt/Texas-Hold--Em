package com.forrestpruitt.texas;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver
{
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static Pot pot = new Pot();
	
	public static void main(String args[])
	{
		//Create a new deck
		Deck deck = new Deck();
		
		//Print the deck out
		//Uses the toString() method of the deck, which
		//makes use of each card's toString() method.
		System.out.println(deck.toString());
		
		//Shuffle the deck. Uses Java's Collection.shuffle method.
		deck.shuffle();
		System.out.println(deck.toString());
		
		//Create an array list to keep track of current hands.
		ArrayList<Hand> hands = new ArrayList<Hand>();
		
		//Deal 5 hands of five cards each, then print them out.
		hands = deck.deal(5, 5);
		for(Hand hand : hands)
		{
			System.out.println(hand.toString());
		}
		
		//Demonstrate that the cards have actually been removed from the deck.
		System.out.println(deck.toString());
		
		//Show Discard Functionality (From Hand)
		Hand hand1 = hands.get(0);
		System.out.println(hand1);
		Scanner in = new Scanner(System.in);
		System.out.println("Enter ID to discard: ");
		int discardID = in.nextInt();
		hand1.discardCard(discardID);
		System.out.println("After Discard: \n"+hand1.toString());
		System.out.println("Discard Pile: ");
		System.out.println(DiscardPile.pile.toString());
		//Add another to the discard pile:
		System.out.println("Enter ID to discard: ");
		discardID = in.nextInt();
		hand1.discardCard(discardID);
		System.out.println("After Discard: \n"+hand1.toString());
		System.out.println("Discard Pile: ");
		System.out.println(DiscardPile.pile.toString());
		
		
		
	}
}

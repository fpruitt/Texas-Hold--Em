package com.forrestpruitt.texas;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game 
{
	Deck deck;
	Player player1;
	Player player2;
	
	ArrayList<Card> communityCards = new ArrayList<Card>();
	
	static final int SMALL_BLIND = 5;
	static final int BIG_BLIND = 10;
	
	//Start with an initial pot size of 0.
	public static int chipsInPot = 0;
	

	//betToCall holds the number of chips required to add in order to call for the next player.
	//The initial bet to call is the difference between the big blind and the small blind
	//public static int betToCall = BIG_BLIND-SMALL_BLIND;
	
	//keep up with who gets the small/big blinds.
	//Pick randomly at the start of each game.
	//If smallBlind=1, player 1 has the small blind, and player 2 has the big blind.
	//If smallBlind=2, player 2 has the small blind, and player 2 has the big blind.
	int smallBlind=1 + (int)(Math.random() * ((2 - 1) + 1));
	
	
	Scanner in = new Scanner(System.in);
	public Game(Player player1, Player player2)
	{
		//Creates and shuffles a deck.
		deck = new Deck();
		deck.shuffle();
		SoundPlayer.playSound(SoundPlayer.sound_shuffle);
		
		//Assign this round's players. Assumes two players, as stated in requirements.
		this.player1 = player1;
		this.player2 = player2;
		
		//Deal Hands (Two hands of Two Cards)
		ArrayList<Hand> hands = deck.deal(2, 2);
		
		//Assign Hands to Players
		this.player1.setHand(hands.get(0));
		this.player2.setHand(hands.get(1));
	}
	public int StartGameLoop()
	{
		ArrayList<Card> player1Hand = player1.getHand().getCards();
		ArrayList<Card> player2Hand = player2.getHand().getCards();

		System.out.println("---------------");
		System.out.println("|Starting Hand|");
		System.out.println("---------------");


		//No limit hold 'em allows for arbitrary betting amounts greater than or equal to
		//  the amount to 'call'. 
		System.out.println("The rules are No-Limit Hold 'Em.");
		printChipTotal(player1,player2);
		//Start off with the blinds.
		//Using a simple swap-off method for big blinds/small blinds in absene of
		//an actual dealer player.
		if(smallBlind==1)
		{
			System.out.println(player1.getName()+" has the Small Blind of "+SMALL_BLIND+" chips.");
			System.out.println(player2.getName()+" has the Big Blind of "+BIG_BLIND+" chips.");
			//Make sure both players can post their blinds.
			if(player1.getNumOfChips() < SMALL_BLIND)
			{
				System.out.println(player1.getName()+" has run out of chips!");
				//Exit game loop, do something about lack of chips
				return -1;
			}
			else
			{
				player1.betChips(SMALL_BLIND);
			}

			if(player2.getNumOfChips() < BIG_BLIND)
			{
				System.out.println(player2.getName()+" has run out of chips!");
				player1.refundChips(SMALL_BLIND);
				//Exit game loop, do something about lack of chips
				return -2;
			}
			else
			{
				player2.betChips(BIG_BLIND);
			}

			SoundPlayer.playSound(SoundPlayer.sound_betting);
			SoundPlayer.playSound(SoundPlayer.sound_betting);
		}
		else
		{
			System.out.println(player2.getName()+" has the Small Blind of "+SMALL_BLIND+" chips.");
			System.out.println(player1.getName()+" has the Big Blind of "+BIG_BLIND+" chips.");
			
			//Make sure both players can post their blinds.
			if(player1.getNumOfChips() < BIG_BLIND)
			{
				System.out.println(player1.getName()+" has run out of chips!");
				//Exit game loop, do something about lack of chips
				return -1;
			}
			else
			{
				player1.betChips(BIG_BLIND);
			}

			if(player2.getNumOfChips() < SMALL_BLIND)
			{
				System.out.println(player2.getName()+" has run out of chips!");
				player1.refundChips(BIG_BLIND);
				//Exit game loop, do something about lack of chips
				return -2;
			}
			else
			{
				player2.betChips(SMALL_BLIND);
			}

			SoundPlayer.playSound(SoundPlayer.sound_betting);
			SoundPlayer.playSound(SoundPlayer.sound_betting);
		}
		
		System.out.println("The pot currently contains "+chipsInPot);
		printChipTotal(player1,player2);
		//Now that blinds are posted, betting round 1 begins.
		//Minimum Bet = Small Blind.
		System.out.print(player1.getName()+"'s hand is: ");
		System.out.println(player1.getHand());

		System.out.println("------------------");
		System.out.println("|Starting Round 1|");
		System.out.println("------------------");
		
		//If player 1 has the small blind, they go first before the flop.
		if(smallBlind == 1)
		{
			Turn turn1 = new Turn(player1, player2);
			int results = turn1.takeTurn();
			if(results == -1)
			{
				//The Computer wins
				return 2;
			}
			Turn turn2 = new TurnComputer(player2, player1);
			int results2 = turn2.takeTurn();
			if(results2 == -1)
			{
				//The Player wins
				return 1;
			}
		}
		else
		{
			Turn turn1 = new TurnComputer(player2, player1);
			int results = turn1.takeTurn();
			if(results == -1)
			{
				//The Player Wins!
				return 1;
			}
			Turn turn2 = new Turn(player1, player2);
			int results2 = turn2.takeTurn();
			if(results2 == -1)
			{
				//The Computer Wins
				return 2;
			}
		}
		
		//THE FLOP HAPPENS HERE
		
		SoundPlayer.playSound(SoundPlayer.sound_flop);
		
		//Deal 1 'hand' of 3 cards. Add these to the community pool.
		ArrayList<Hand> tempHand = deck.deal(3, 1);
		ArrayList<Card> cards = tempHand.get(0).getCards();
		communityCards.add(cards.get(0));
		communityCards.add(cards.get(1));
		communityCards.add(cards.get(2));

		player1Hand.add(cards.get(0));
		player1Hand.add(cards.get(1));
		player1Hand.add(cards.get(2));

		player2Hand.add(cards.get(0));
		player2Hand.add(cards.get(1));
		player2Hand.add(cards.get(2));

		System.out.println("-----------");
		System.out.println("|The FLOP!|");
		System.out.println("-----------");

		printCommunityCards(communityCards);
		printChipTotal(player1,player2);
		//Round 2 Betting, the big blind player gets to bet first.
		
		System.out.println("------------------");
		System.out.println("|Starting Round 2|");
		System.out.println("------------------");
		if(smallBlind == 1)
		{
			Turn turn3 = new TurnComputer(player2, player1);
			int results = turn3.takeTurn();
			if(results == -1)
			{
				//The Player Wins!
				return 1;
			}
			
			Turn turn4 = new Turn(player1, player2);
			int results2 = turn4.takeTurn();
			if(results2 == -1)
			{
				//The Computer Wins!
				return 2;
			}
		}
		else
		{
			Turn turn3 = new Turn(player1, player2);
			int results = turn3.takeTurn();
			if(results == -1)
			{
				//The Computer Wins!
				return 2;
			}
			
			
			Turn turn4 = new TurnComputer(player2, player1);
			int results2 = turn4.takeTurn();
			if(results2 == -1)
			{
				//The Player Wins!
				return 1;
			}
		}
		
		
		//THE Turn HAPPENS HERE
		SoundPlayer.playSound(SoundPlayer.sound_turn);
		
		//Deal in the fourth community card.
		tempHand = deck.deal(1, 1);
		cards = tempHand.get(0).getCards();
		communityCards.add(cards.get(0));

		player1Hand.add(cards.get(0));

		player2Hand.add(cards.get(0));
		

		System.out.println("-----------");
		System.out.println("|The TURN!|");
		System.out.println("-----------");
		printCommunityCards(communityCards);
		printChipTotal(player1,player2);

		System.out.println("------------------");
		System.out.println("|Starting Round 3|");
		System.out.println("------------------");
		
		if(smallBlind == 1)
		{
			Turn turn5 = new TurnComputer(player2, player1);
			int results = turn5.takeTurn();
			if(results == -1)
			{
				//The Player Wins!
				return 1;
			}
			
			Turn turn6 = new Turn(player1, player2);
			int results2 = turn6.takeTurn();
			if(results2 == -1)
			{
				//The Computer Wins!
				return 2;
			}
		}
		else
		{
			Turn turn5 = new Turn(player1, player2);
			int results = turn5.takeTurn();
			if(results == -1)
			{
				//The Computer Wins!
				return 2;
			}
			Turn turn6 = new TurnComputer(player2, player1);
			int results2 = turn6.takeTurn();
			if(results2 == -1)
			{
				//The Player Wins!
				return 1;
			}
		}
		
		
		//THE RIVER HAPPENS HERE
		
		SoundPlayer.playSound(SoundPlayer.sound_river);
		
		System.out.println("------------");
		System.out.println("|The RIVER!|");
		System.out.println("------------");
		tempHand = deck.deal(1, 1);
		cards = tempHand.get(0).getCards();
		communityCards.add(cards.get(0));

		player1Hand.add(cards.get(0));

		player2Hand.add(cards.get(0));

		printCommunityCards(communityCards);
		printChipTotal(player1,player2);
		
		System.out.println("-----------------------");
		System.out.println("|Starting Final Round!|");
		System.out.println("-----------------------");
		if(smallBlind == 1)
		{
			Turn turn7 = new TurnComputer(player2, player1);
			int results = turn7.takeTurn();
			if(results == -1)
			{
				//The Player Wins!
				return 1;
			}
			else if(results == 2)
			{	
				Turn turn8 = new Turn(player1, player2);
				int results2 = turn8.takeTurn();
				if(results2 == -1)
				{
					//The Computer Wins!
					return 2;
				}
			}
		}
		else
		{
			Turn turn7 = new Turn(player1, player2);
			int results = turn7.takeTurn();
			if(results == -1)
			{
				//The Computer Wins!
				return 2;
			}
			else if(results == 2)
			{
				Turn turn8 = new TurnComputer(player2, player1);
				int results2 = turn8.takeTurn();
				if(results2 == -1)
				{
					//The Player Wins!
					return 1;
				}
			}
		}
		System.out.println("||||||||||||||||");
		System.out.println("|<|The Reveal|>|");
		System.out.println("||||||||||||||||");
		
		//DECIDE WHO WINS HERE
		//If computer has better hand, add a win for the computer(player2).
		//If player has best hand, add a win for the player (player1)
		
		PokerHandEvaluator evaluator = new PokerHandEvaluator(player1Hand);
		PokerHandEvaluator evaluator2 = new PokerHandEvaluator(player2Hand);
		System.out.println("Player 1's Best Hand: ");
		//int player1Value = evaluator.getHandValue();
		//int player2Value = evaluator2.getHandValue();
		System.out.println(evaluator.getBestHand().getHandType());
		System.out.println(evaluator.getBestHand());

		System.out.println("Player 2's Best Hand: ");
		System.out.println(evaluator2.getBestHand().getHandType());
		System.out.println(evaluator2.getBestHand());
		if(evaluator.compareTo(evaluator2) > 0)
		{
			player1.winChips(chipsInPot);
			chipsInPot = 0;
			return 1;
		}
		else if(evaluator.compareTo(evaluator2) == 0)
		{
			System.out.println("It's a tie! Players split the pot!");
			player1.winChips(chipsInPot/2);
			player2.winChips(chipsInPot/2);
			chipsInPot = 0;
			return 3;
		}
		else if(evaluator.compareTo(evaluator2) < 0)
		{
			player2.winChips(chipsInPot);
			chipsInPot = 0;
			return 2;
		}


		//Exit Successfully
		return 0;
		

	}
	private static void printCommunityCards(ArrayList<Card> communityCards)
	{
		System.out.println("Community Cards: ");
		for(Card card : communityCards)
		{
			System.out.println(card.toString());
		}
	}
	private static void printChipTotal(Player player1, Player player2)
	{
		System.out.println(player1.getName()+" has "+player1.getNumOfChips()+" chips. ");
		System.out.println(player2.getName()+" has "+player2.getNumOfChips()+" chips. ");
	}
}

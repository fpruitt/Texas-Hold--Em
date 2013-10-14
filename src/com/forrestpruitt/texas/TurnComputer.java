package com.forrestpruitt.texas;
import java.lang.Math;

public class TurnComputer extends Turn
{
	public TurnComputer(Player player, Player opponent)
	{
		super(player, opponent);
	}

	public int takeTurn()
	{
		//Determine the amount needed to call
		//int amountToCall = Game.betToCall;
		int amountToCall = Driver.amountToCall;
		if(amountToCall < 0)
		{
			amountToCall = 0;
		}
		System.out.println("Total Bets: PC:"+player.totalBetThisRound +" Human:"+opponent.totalBetThisRound);
		
		double chanceToFold = 1.0 * Math.random();
		if(player.getHand().getCards().get(0).getRank() == player.getHand().getCards().get(1).getRank())
		{
			chanceToFold -= 0.5 * Math.random();
		}
		if(player.getHand().getCards().get(0).getSuit() == player.getHand().getCards().get(1).getSuit())
		{
			chanceToFold -= 0.5 * Math.random();
		}

		//System.out.println(player.getName()+", Do you wish to call, bet, check, or fold?");

		int betAmount = (int)(player.getNumOfChips() * ((1 - chanceToFold)/6.0));

		int answer;
		//Look for input until input an amount in the correct range.
		//If they want to check, make sure the amountToCall is not 0, or they can't check.
		do
		{
			//System.out.println("0 to call, 1 to bet, 2 to check, 3 to fold");
			//Computer gives answer here
			//if chance of folding is very high AND the game is past the flop AND the computer is not all in
			if(chanceToFold > .7 && player.getHand().getCards().size() > 2 && !player.isAllIn())
			{
				answer = 3;
				//System.out.println("Computer wants to fold");
			}

			else if(chanceToFold < .2 && player.getNumOfChips() > 0 && betAmount > amountToCall)
			{
				answer = 1;
				//System.out.println("Computer wants to bet " + betAmount + " and thinks it has " + player.getNumOfChips());
			}
			else if(amountToCall == 0 || player.isAllIn())
			{
				answer = 2;
				//System.out.println("Computer wants to check and think it is " + player.isAllIn());
			}
			else
			{
				answer = 0;
				//System.out.println("Computer wants to call and it thinks amount to call is " + amountToCall);
			}

			if(answer == 2 && amountToCall != 0 && !player.isAllIn())
			{
				System.out.println("You can't check, you must call the current bet, bet higher, or fold.");
			}
			if(answer == 0 && amountToCall == 0)
			{
				System.out.println("There isn't anything to call. Did you mean check?");
			}
			if(answer == 1 && player.getNumOfChips() <= 0)
			{
				System.out.println("You don't have any chips to bet!");
			}
		}while(answer < 0 || answer > 3
			   || ((answer == 2 && amountToCall != 0) && !player.isAllIn())
			   || (answer == 0 && amountToCall == 0)
			   || (answer == 1 && player.getNumOfChips() <= 0));
		
		
		
		if(answer == 0)
		{
			//Code for calling.
			if(player.getNumOfChips() > amountToCall)
			{
				System.out.println(player.getName()+" is calling. This adds "+amountToCall+" to the pot.");
				player.betChips(amountToCall);
			}
			else
			{
				System.out.println(player.getName() + " is going all in! This adds " + player.getNumOfChips()+ " to the pot!");
				player.betChips(player.getNumOfChips());
				player.setIsAllIn(true);
			}
			SoundPlayer.playSound(SoundPlayer.sound_betting);
			System.out.println("The pot has "+Game.chipsInPot+" chips.");
			//Game.betToCall = 0;
			Driver.amountToCall = 0;
			return 0;
		}
		else if(answer == 1)
		{
			//Code for placing a bet.
			int minBet = amountToCall + Game.SMALL_BLIND;
			System.out.println("Got here and amount to call is " + amountToCall);

			//Find out how much user wants to bet.
			//int betAmount;

			do
			{
				//System.out.println("How much would you like to bet? (Min. Bet "+minBet+")");
				//System.out.println("(Computer chip count: "+player.getNumOfChips()+")");
				betAmount = (int)(player.getNumOfChips() * ((1 - chanceToFold)/6.0));
				//System.out.println("Computer trying to bet " + betAmount);
				if(betAmount > player.getNumOfChips())
				{
					betAmount = player.getNumOfChips();
				}

			}while(betAmount < minBet || betAmount > player.getNumOfChips());
			
			//Place Bet
			System.out.println(player.getName()+" is betting "+betAmount);
			if(betAmount == player.getNumOfChips())
			{
				System.out.println(player.getName() + " is going all in! This adds " + player.getNumOfChips()+ " to the pot!");				
				player.setIsAllIn(true);
			}
			player.betChips(betAmount);
			Driver.amountToCall = betAmount-amountToCall;
			SoundPlayer.playSound(SoundPlayer.sound_betting);
			
			//Adjust the new amount the next player needs to call.
			//Game.betToCall = betAmount - amountToCall;
			
		}
		else if(answer == 2)
		{
			//ADD CHECKING OPTION HERE.
			System.out.println(player.getName()+" is checking.");
			//If you can get here, you SHOULD be able to always pass this assertation.
			//assert(Game.betToCall == 0);
			Driver.amountToCall = 0;
			return 0;
			
		}
		else if(answer == 3)
		{
			System.out.println(player.getName()+" folded!");
			opponent.winChips(Game.chipsInPot);
			SoundPlayer.playSound(SoundPlayer.sound_fold);
			return -1;
		}
		
		return 0; //Game procedes as normal.
		
		
	}
}
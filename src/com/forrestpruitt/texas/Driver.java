package com.forrestpruitt.texas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver
{
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public static void main(String args[])
	{
		
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your name: ");
		String playerName = in.nextLine();
		System.out.println("Enter number of starting chips: ");
		int numOfStartingChips = in.nextInt();
		Player player1 = new Player(numOfStartingChips,playerName,0);
		Player player2 = new Player(numOfStartingChips,"Computer",1);
		
		int gamesPlayed = 0;
		
		boolean again = true;
		while(again)
		{
			Game testGame = new Game(player1, player2);
		
			//If StartGameLoop returns -1, the human player ran out of chips.
			//If StartGameLoop returns -2, the computer player ran out of chips.
			//If it returns 1, the human player won.
			//If it returns 2, the computer player won.
			int returnValue = testGame.StartGameLoop();
			gamesPlayed++;
			
			//Handle endgame conditions
			if(returnValue == -1)
			{
				System.out.println("You have run out of chips. ");
				System.out.println("Press 1 to add more chips, 2 to quit");
				int ans = in.nextInt();
				if(ans == 1)
				{
					System.out.println("Enter the number of chips you would like to add: ");
					int chips = in.nextInt();
					player1.winChips(chips);
				}
				else if(ans == 2)
				{
					System.out.println("Your final stats for this game were: ");
					//Print stats here
					System.out.println("Exiting...");
					again = false;
				}
			}
			else if(returnValue == -2)
			{
				System.out.println("The computer has run out of chips!");
				System.out.println("Press 1 to give it more chips, or 2 to quit.");
				int ans = in.nextInt();
				if(ans == 1)
				{
					System.out.println("Enter the number of chips you would like to add: ");
					int chips = in.nextInt();
					player2.winChips(chips);
				}
				else if(ans == 2)
				{
					System.out.println("Your final stats for this game were: ");
					//Print stats here
					System.out.println("Exiting...");
					again = false;
				}
			}
			else if(returnValue == 1)
			{
				SoundPlayer.playSound(SoundPlayer.sound_win);

				System.out.println("You won!");
				System.out.println("Your current stats are: ");
				System.out.println("Would you like to play again? 1 for yes, 0 for no: ");
				int ans = in.nextInt();
				if(ans == 0)
					again = false;
			}
			else if(returnValue == 2)
			{
				SoundPlayer.playSound(SoundPlayer.sound_lose);

				System.out.println("The computer won the game.");
				System.out.println("Your current stats are: ");
				printStats(player1, gamesPlayed);
				System.out.println("Would you like to play again? 1 for yes, 0 for no: ");
				int ans = in.nextInt();
				if(ans == 0)
					again = false;
			}
			
			
			//Reset static variables
			Game.chipsInPot = 0;
			Game.betToCall = 0;
			
		}
	}
	private static void printStats(Player player, int gamesPlayed)
	{
		System.out.println("Wins: "+ player.getWins());
		System.out.println("Losses: "+ (gamesPlayed - player.getWins()));
		System.out.println("Win Percentage: "+Math.round((player.getWins()/gamesPlayed))+"%");
	}
}

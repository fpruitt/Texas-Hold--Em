package com.forrestpruitt.texas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.forrestpruitt.texas.Card.Rank;
import com.forrestpruitt.texas.Card.Suit;

import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Driver
{
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static int gamesPlayed = 0;
	public static Player player1;
	public static Player player2;
	public static Game game;
	public static TexasGUI gui;
	public static boolean shouldReset = false;
	
	public static void main(String args[])
	{
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TexasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TexasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TexasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TexasGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Creates and displays the Texas GUI and sets the window to center screen */
        //java.awt.EventQueue.invokeLater(new Runnable() {
            //public void run() {

               
               //Temporary player construction
               //Should get this info from player using dialog box.
       			int numOfStartingChips = 2000;
    			player1 = new Player(numOfStartingChips,"Kathy",0);
    			player2 = new Player(numOfStartingChips,"Computer",1);
    			
    			game = new Game(player1, player2);
                gui = new TexasGUI(game, player1, player2);
                gui.setVisible(true);
                //Sets the Texas GUI to center screen on open
                gui.setLocationRelativeTo(null);
                //newGUI.startGame(player1,player2, game);
                
        		boolean again = true;
        		while(again ==true)
        		{
        			gameLoop();
        			game = new Game(player1, player2);
        			gui.newGame(game);
        		}
        			
                

                
            //}
        //}
	}

	public static boolean isPlayer1Turn = false;
    public static int amountToCall;
    /**
     * 
     * @return true to play again, false to quit.
     */
    static boolean gameLoop()
    {
    	
    	//Place the label for 'small blind' and 'big blind' appropriately
    	if(game.smallBlind == 1)
    	{
    		//Player 1 has the small blind
    		gui.lblPlayer1Blind.setText("Small Blind");
    		gui.lblPlayer2Blind.setText("Big Blind");
    	}
    	else
    	{
    		//Player 2 has the small blind
    		gui.lblPlayer2Blind.setText("Small Blind");
    		gui.lblPlayer1Blind.setText("Big Blind");
    	}
    	
    	//Place Player 1 cards where they belong on the GUI
    	gui.cardUser1.setIcon(new ImageIcon(player1.getHand().getCards().get(0).getURL()));
    	gui.cardUser2.setIcon(new ImageIcon(player1.getHand().getCards().get(1).getURL()));
    	gui.cardUser1.setVisible(true);
    	gui.cardUser2.setVisible(true);
    	
    	//Decide if it is the human player's turn first or not
    	if(game.smallBlind==1)
    	{
    		isPlayer1Turn = true;
    	}
    	else
    	{
    		isPlayer1Turn = false;
    	}
    	System.out.println("Player 1 turn: "+isPlayer1Turn);
    	//-------------------------
    	// Blind Betting Round
    	//-------------------------
    	if(game.smallBlind==1)
		{
    		
    		//Player 1 has the small blind.
			//Make sure both players can post their blinds.
			if(player1.getNumOfChips() < game.SMALL_BLIND)
			{
				//Player 1 has run out of chips.
				//Exit game loop, do something about lack of chips
				int returnCode = endTurn(-1);
				if(returnCode == 1)
				{
					return true;
				}
			}
			else
			{
				player1.betChips(game.SMALL_BLIND);
				System.out.println("Player betting "+game.SMALL_BLIND);
			}

			if(player2.getNumOfChips() < game.BIG_BLIND)
			{
				//Player 2 is out of chips.
				//player1.refundChips(game.SMALL_BLIND); //<-- Uncertain about this? TODO: Ask Paul.
				//Exit game loop, do something about lack of chips
				int returnCode = endTurn(-2);
				if(returnCode == 1)
				{
					return true;
				}
			}
			
			else
			{
				player2.betChips(game.BIG_BLIND);
				System.out.println("Computer betting "+game.BIG_BLIND);
			}

			SoundPlayer.playSound(SoundPlayer.sound_betting);
			SoundPlayer.playSound(SoundPlayer.sound_betting);
		}
		else
		{
			//Player 1 has the big blind.
			//Make sure both players can post their blinds.
			if(player1.getNumOfChips() < game.BIG_BLIND)
			{
				//Player 1 has run out of chips.
				//Exit game loop, do something about lack of chips
				int returnCode = endTurn(-1);
				if(returnCode == 1)
				{
					return true;
				}
			}
			else
			{
				player1.betChips(game.BIG_BLIND);
			}

			if(player2.getNumOfChips() < game.SMALL_BLIND)
			{
				//player1.refundChips(game.BIG_BLIND);
				//Exit game loop, do something about lack of chips
				int returnCode = endTurn(-2);
				if(returnCode == 1)
				{
					return true;
				}
			}
			else
			{
				player2.betChips(game.SMALL_BLIND);
			}

			SoundPlayer.playSound(SoundPlayer.sound_betting);
			SoundPlayer.playSound(SoundPlayer.sound_betting);
		}
    	
    	//************
    	// ROUND 1
    	//************
    	//If player 1 has the small blind, they go first before the flop.
    	
    	//If takeTurn() returns true, the game should reset.
		boolean returnBool  = takeTurn();
		if(returnBool)
			return true;
		
		//******************
		// ROUND 2: THE FLOP
		//******************
		SoundPlayer.playSound(SoundPlayer.sound_flop);
		
		//Deal out the community cards
		ArrayList<Hand> tempHand = game.deck.deal(3, 1);
		ArrayList<Card> cards = tempHand.get(0).getCards();
		game.communityCards.add(cards.get(0));
		game.communityCards.add(cards.get(1));
		game.communityCards.add(cards.get(2));
		gui.doFlop(game.communityCards.get(0), game.communityCards.get(1), game.communityCards.get(2));
		
		//Whoever paid the big blind goes first after the flop
		if(game.smallBlind == 1)
		{
			isPlayer1Turn = false;
		}
		else
		{
			isPlayer1Turn = true;
		}
		
		//Play the next two rounds of betting
		returnBool  = takeTurn();
		if(returnBool)
			return true;
		
		//******************
		// ROUND 3: THE TURN
		//******************
		SoundPlayer.playSound(SoundPlayer.sound_turn);
		//Deal one hand of one card, get the hand, get the cards of that hand, and then get the first card in those cards.
		game.communityCards.add(game.deck.deal(1, 1).get(0).getCards().get(0));
		gui.doTurn(game.communityCards.get(3));
		
		
		if(game.smallBlind == 1)
		{
			isPlayer1Turn = false;
		}
		else
		{
			isPlayer1Turn = true;
		}
		
		returnBool  = takeTurn();
		if(returnBool)
			return true;
		
		//******************
		// ROUND 4: THE RIVER
		//******************
		SoundPlayer.playSound(SoundPlayer.sound_river);
		//Deal one hand of one card, get the hand, get the cards of that hand, and then get the first card in those cards.
		game.communityCards.add(game.deck.deal(1, 1).get(0).getCards().get(0));
		gui.doRiver(game.communityCards.get(4));
		
		if(game.smallBlind == 1)
		{
			isPlayer1Turn = false;
		}
		else
		{
			isPlayer1Turn = true;
		}

		returnBool  = takeTurn();
		if(returnBool)
			return true;
		
		//**************************
		// THE REVEAL
		//**************************
		
		//This is where we evaluate hands and decide who wins.
		//First, add all the community cards to the players hand.
		//The evaluator takes a 'hand' of seven cards.
			
		gui.showOpponentsHand(player2.getHand().getCards().get(0), player2.getHand().getCards().get(1));
		ArrayList<Card> hand1 = player1.getHand().getCards();
		hand1.add(game.communityCards.get(0));
		hand1.add(game.communityCards.get(1));
		hand1.add(game.communityCards.get(2));
		hand1.add(game.communityCards.get(3));
		hand1.add(game.communityCards.get(4));
		
		ArrayList<Card> hand2 = player2.getHand().getCards();
		hand2.add(game.communityCards.get(0));
		hand2.add(game.communityCards.get(1));
		hand2.add(game.communityCards.get(2));
		hand2.add(game.communityCards.get(3));
		hand2.add(game.communityCards.get(4));
		
		PokerHandEvaluator evaluator = new PokerHandEvaluator(hand1);
		PokerHandEvaluator evaluator2 = new PokerHandEvaluator(hand2);
		String player1BestHand = evaluator.getBestHand().toString();
		String player2BestHand = evaluator2.getBestHand().toString();
		System.out.println("Player 1's Hand: "+player1BestHand);
		System.out.println("Player 2's Hand: "+player2BestHand);
		
		
		//Using Paul's Evaluator Comparison Methods to figure out which hand is best
		//If the comparison is greater than 0, the first player wins.
		//If the comparison is less than 0, the second player wins.
		//If the comparison is exactly 0, there has been a tie (same hand, same high card).
		if(evaluator.compareTo(evaluator2) > 0)
		{
			SoundPlayer.playSound(SoundPlayer.sound_win);
			gui.lblHand.setText(evaluator.getBestHand().getHandType().toString());
			gui.lblHand.setVisible(true);
			int returnCode = endTurn(1);
			if(returnCode == 1)
			{
				System.out.println("Game resetting...");
				return true;
			}
			
		}
		else if(evaluator.compareTo(evaluator2) < 0)
		{
			SoundPlayer.playSound(SoundPlayer.sound_lose);
			gui.lblHand.setText(evaluator2.getBestHand().getHandType().toString());
			gui.lblHand.setVisible(true);
			int returnCode = endTurn(2);
			if(returnCode == 1)
			{
				System.out.println("Game resetting...");
				return true;
			}
			
		}	
		else if(evaluator.compareTo(evaluator2) == 0)
		{
			SoundPlayer.playSound(SoundPlayer.sound_win);
			gui.lblHand.setText("Tie with: "+evaluator2.getBestHand().getHandType().toString());
			gui.lblHand.setVisible(true);
			int returnCode = endTurn(3);
			if(returnCode == 1)
			{
				System.out.println("Game resetting...");
				return true;
			}
		}
		return true;
    }
    	/**
    	 * 
    	 * @param statusCode
    	 * @return 0 if game continues, -1 if game should quit, 1 if the game should reset.
    	 */
        public static int endTurn(int statusCode)
        {
            
        	
            gui.btnCall.setEnabled(false);
            gui.btnCheck.setEnabled(false);
            gui.btnBet.setEnabled(false);
            gui.btnFold.setEnabled(false);
            gui.updateLabels();
        	if(statusCode == 0)
        	{
        		//Switch turns
        		//Game continues, let other player take turn
        		if(isPlayer1Turn == false)
        		{
        			isPlayer1Turn = true;
        			System.out.println("PC's turn is over.");
        			gui.lblYourTurn.setVisible(false);
        		}
        		else
        		{
        			
        			System.out.println("Player's Turn is over...");
        			isPlayer1Turn = false;
        		}
        		gui.updateLabels();
        		return 0;
        	}
        	else if(statusCode == -1)
        	{
        		//Human Player out of chips
        		int response = gui.showPlayerOutOfChips();
        		if(response == 0)
        		{
        			player1.winChips(200);
        			return 0;
        		}
        		else
        		{
        			System.exit(0);
        			return -1;
        		}
        	}
        	else if(statusCode == -2)
        	{
        		//Computer out of chips
        		int response = gui.showComputerOutOfChips();
        		if(response == 0)
        		{
        			//Add 200 chips to player 2
        			player2.winChips(200);
        			return 0;
        			
        			//TODO: Add reset code here
        		}
        		else
        		{
        			System.exit(0);
        			return -1;
        		}
        	}
        	
        	else if(statusCode == 1)
        	{
        		//Human player wins!
        		player1.winGame();
           		player1.winChips(game.chipsInPot);
           		game.chipsInPot=0;
           		
    			int response = gui.showPlayer1Win();
    			if(response == 0)
    			{
    				return 1;
    				//TODO add reset game code here.
    			}
    			else
    			{
    				return -1;
    			}
        		
        	}
        	
        	else if(statusCode == 2)
        	{
           		player1.loseGame();
           		player2.winChips(game.chipsInPot);
           		game.chipsInPot=0;
           		
           		int response = gui.showPlayer2Win();
    			if(response == 0)
    			{
    				return 1;
    			}
    			else
    			{
    				System.exit(0);
    				return -1;
    			}
        	}
        	
        	else if(statusCode == 3)
        	{
        		//Players have tied!
        		//Award both players of the pot.
        		//Does not count as a win or loss.
        		int awardAmount = game.chipsInPot/2;
        		player1.winChips(awardAmount);
        		player2.winChips(awardAmount);
        		
        		int response = gui.showTie();
    			if(response == 0)
    			{
    				return 1;
    			}
    			else
    			{
    				System.exit(0);
    				return -1;
    			}
        	}
        	
        	else
        	{
        		System.out.println("Invalid status code received: \""+statusCode+"\"");
        		return -3;
        	}
        }
        
        /**
         * 
         * @return True if game should reset.
         */
        public static boolean takeTurn()
        {
    		if(isPlayer1Turn)
    		{

    			boolean resetBool = guiTurn(gui, player1, player2);
    			if(resetBool)
    			{
    				return true;
    			}
    			boolean returnCode = cpuTurn(player1, player2);
    			if(returnCode == true)
    			{
    				return true;
    			}
    		}
    		else
    		{
    			boolean returnCode = cpuTurn(player1, player2);
    			if(returnCode == true)
    			{
    				return true;
    			}
    			boolean resetBool = guiTurn(gui, player1, player2);
    			if(resetBool)
    			{
    				return true;
    			}
    		}
    		return false;
        }
        
        public static boolean cpuTurn(Player player1, Player player2)
        {
    		Turn computerTurn1 = new TurnComputer(player2, player1);
    		int results = computerTurn1.takeTurn();
    		if(results == -1)
    		{
    			//The player wins because the computer folded.
    			int returnCode = endTurn(1);
    			if(returnCode == 1)
    			{
    				System.out.println("Game resetting...");
    				return true;
    			}
    		}
    		else
    			endTurn(0);
    		return false;
        }

        /**
         * 
         * @param gui
         * @param player1
         * @param player2
         * @return true if game should reset, false otherwise.
         */
    	private static boolean guiTurn(TexasGUI gui, Player player1, Player player2)
    	{
    		gui.lblYourTurn.setVisible(true);
    		//Figure out if there is an outstanding bet to call
    		Driver.amountToCall = player2.getTotalBetThisRound() - player1.getTotalBetThisRound();
    		if(amountToCall < 0)
    			amountToCall = 0;
    		
    		gui.lblAmountToCall.setText("Amount to call: "+String.valueOf(amountToCall));
    		
    		//Enable and disable appropriate buttons
    		gui.btnFold.setEnabled(true);
    		gui.btnBet.setEnabled(true);
    		if(amountToCall != 0 && !player2.isAllIn())
    		{
    			//Player shouldn't be able to check, they must call, fold, or bet.
    			gui.btnCheck.setEnabled(false);
    		}
    		else
    		{
    			gui.btnCheck.setEnabled(true);
    		}
    		
    		//If there isn't an amount to call, the user should check, bet, or fold, not call.
    		if(amountToCall == 0)
    			gui.btnCall.setEnabled(false);
    		else
    			gui.btnCall.setEnabled(true);

    		
    		//Wait here until Player1 has taken his turn.
    		while(isPlayer1Turn)
    		{
    			//Block
    			//System.out.println("Waiting for player1...");
    			try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		if(shouldReset == true)
        		{
        			System.out.println("Should be resetting...");
        			shouldReset = false;
        			return true;
        		}
    		}
    		//Make sure it's no longer player 1's turn.
    		assert(isPlayer1Turn == false);
    		return false;

    		
    	}

	private static void printStats(Player player, int gamesPlayed)
	{
		System.out.println("> " + "Wins: "+ player.getWins());
		System.out.println("> " + "Losses: "+ (gamesPlayed - player.getWins()));
		//Rounds to nearest int
		System.out.println("> " + "Win Percentage: "+Math.round(((double)player.getWins()/gamesPlayed)*100)+"%");
	}
	
}

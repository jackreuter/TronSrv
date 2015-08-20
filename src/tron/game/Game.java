package tron.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tron.enums.*;
import tron.data.TronBoard;

public class Game implements Runnable{
	private Player p1;
	private Player p2;
	private TronBoard tb;
	
	public Game(Player p1In, Player p2In){
		p1=p1In;		
		p2=p2In;
		
		tb = new TronBoard();
	}
	
	public static Direction stringToDir(String s) {
		if (s.equals("1")) {
			return Direction.NORTH;
		} else if (s.equals("2")) {
			return Direction.EAST;
		} else if (s.equals("3")) {
			return Direction.SOUTH;
		} else {
			return Direction.WEST;
		}
	}
	
	@Override
	public void run() {		
		p1.tell("The Game is starting.");
		p2.tell("The Game is starting.");
		p1.tell("You are Player 1.");
		p2.tell("You are Player 2.");
		p1.tell();
		p2.tell();
		
    int winner = -1;
		while (winner==-1) {
			try {
			    Thread.sleep(50);                 //1000 milliseconds is one second
			} catch(InterruptedException ex) {
				System.out.println("Thread shit gone wrong");
			    Thread.currentThread().interrupt();
			}
			if (p1.isBot()) {
        p1.tell(tb.botString());
      }
			p1.tell(tb.toBigString());
      if (p2.isBot()) {
        p2.tell(tb.botString());
      }
      		p2.tell(tb.toBigString());
      
      p1.tell("Enter a direction: ");
      p2.tell("Enter a direction: ");
      String move1 = p1.getMoveString();
      String move2 = p2.getMoveString();
      
      if (move1.equals(0)) {}

      tb.update(1,stringToDir(move1));
      tb.update(2,stringToDir(move2));
      
      winner = tb.isGameOver();
		}
		
		p1.tell(tb.toBigString());
		p2.tell(tb.toBigString());
		
		if(winner==0){
			p1.tell("Tie");
			p2.tell("Tie");
		}
		else if(winner==1){
			p1.tell("You wins");
			p2.tell("You lose biiiiiiitch");
		}else{
			p1.tell("You lose biiiiiiitch");
			p2.tell("You wins");
		}
	}
}
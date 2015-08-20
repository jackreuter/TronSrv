package tron.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tron.data.TronBoard;
import tron.data.Move;

public class Game implements Runnable{
	private Player p1;
	private Player p2;
	private TronBoard tb;
	
	public Game(Player p1In, Player p2In){
		p1=p1In;		
		p2=p2In;
		
		tb = new TronBoard();
	}
	
	@Override
	public void run() {		
		p1.tell("The Game is starting.");
		p2.tell("The Game is starting.");
		p1.tell("You are Player 1.");
		p2.tell("You are Player 2.");
		p1.tell();
		p2.tell();
		
    boolean gameOver = false;
		while (!gameOver) {
			if (p1.isBot()) {
        p1.tell(tb.botString());
      } else {
				p1.tell(tb.toBigString());
			}	
      if (p2.isBot()) {
        p2.tell(tb.botString());
      } else {
				p2.tell(tb.toBigString());
			}	
			
      p1.tell("Enter a direction: ");
      p2.tell("Enter a direction: ");
      String move1 = p1.getMoveString();
      String move2 = p2.getMoveString();

      tb.update(move1,move2);
      gameOver = tb.isGameOver();
		}
		
		p1.tell(cb.toBigString());
		p2.tell(cb.toBigString());
		
		Team winner=(cb.whoWon());
		if(winner==0){
			p1.tell("draw");
			p2.tell("draw");
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

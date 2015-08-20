package tron.data;

import tron.enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TronBoard {
	private static final int BIKE1 = 1;
	private static final int BIKE2 = 2;
	private static final int TAIL1 = 10;
	private static final int TAIL2 = 20;
	private static final int WALL = 50;
	private static final int BOARD_HEIGHT = 17;
	private static final int BOARD_WIDTH = 30;
	private int[][] board= new int[BOARD_HEIGHT+2][BOARD_WIDTH+2];
	private int player1Row;
	private int player1Col;
  private int player2Row;
  private int player2Col;
  private Direction player1Dir;
  private Direction player2Dir;
	
	public TronBoard(){
    player1Dir = Direction.EAST;
    player2Dir = Direction.WEST;
    Random r = new Random();
    player1Row = r.nextInt(BOARD_HEIGHT)+1;
    player1Col = r.nextInt(BOARD_WIDTH)+1;
    player2Row = r.nextInt(BOARD_HEIGHT)+1;
    player2Col = r.nextInt(BOARD_WIDTH)+1;

    for (int i=0; i<BOARD_HEIGHT+2; i++) {
      if (i==0 || i==BOARD_HEIGHT+1) {
        for (int j=0; j<BOARD_WIDTH+2; j++) {
          board[i][j]=5;
        }
      } else {
        for (int j=0; j<BOARD_WIDTH+2; j++) {
          if (j==0 || j==BOARD_WIDTH+1) {
            board[i][j]=5;
          } else {
            board[i][j]=0;
          }
        }
      }
    }
    board[player1Row][player1Col]=BIKE1;
    board[player2Row][player2Col]=BIKE2;
  }
	
  // public TronBoard(String boardString) {
  //   String[] boardArr = boardString.split(";");
  //   for (int i=0; i<boardArr.length-1; i++) {
  //     String rowString = boardArr[i];
  //     int j = 0;
  //     for (String pieceString : rowString.split(",")) {
  //       if (pieceString.contains("null")) {
  //         board[i][j] = null;
  //       } else {
  //         board[i][j] = new Piece(pieceString);
  //       }
  //       j++;
  //     }
  //   }
  //   int i = boardArr.length-1;
  //   String[] extraInfo = boardArr[i].split(",");
  //   lastPawnMoveFile = Integer.parseInt(extraInfo[0]);
  //   whiteCanCastelLeft = Boolean.parseBoolean(extraInfo[1]);
  //   whiteCanCastelRight = Boolean.parseBoolean(extraInfo[2]);
  //   blackCanCastelLeft = Boolean.parseBoolean(extraInfo[3]);
  //   blackCanCastelRight = Boolean.parseBoolean(extraInfo[4]);
  // }

  public int[][] getBoard() {
    return board;
  }
  
  public void update(int player, Direction move) {
	  if (player==1) {
		  board[player1Row][player1Col]=TAIL1;
	  } else {
		  board[player2Row][player2Col]=TAIL2;
	  }
	  switch (move) {
	  case NORTH:
		  if (player==1) {
			  player1Row -= 1;
		  } else {
			  player2Row -= 1;
		  }
		  break;
	  case SOUTH:
		  if (player==1) {
			  player1Row += 1;
		  } else {
			  player2Row += 1;
		  }
		  break;
	  case EAST:
		  if (player==1) {
			  player1Col += 1;
		  } else {
			  player2Col += 1;
		  }
		  break;
	  case WEST:
		  if (player==1) {
			  player1Col -= 1;
		  } else {
			  player2Col -= 1;
		  }
		  break;
	  }
	  if (player==1) {
		  board[player1Row][player1Col]+=BIKE1;
	  } else {
		  board[player2Row][player2Col]+=BIKE2;
	  }
  }

  public String botString() {
	  String botString = "";
	  for (int i=0; i<BOARD_HEIGHT+2; i++) {
		  for (int j=0; j<BOARD_WIDTH+1; j++) {
			  botString += board[i][j]+",";
		  }
		  botString += board[i][BOARD_WIDTH+1] + ";";
	  }
	  return botString.substring(0,botString.length()-1)+"BOARD";
  }
  
  public String toBigString() {

		    String boardString = "";
		    boardString += "       ___    @   _______  ___     __     _    _      @    ___          \n\r";
		    boardString += "      __|____/       |     |  \\   /  \\    |\\   |       \\____|__         \n\r";
		    boardString += "  _._/  |  / \\_._    |     |  |  |    |   |    |    _._/ \\  |  \\_._     \n\r";
		    boardString += " / |/\\  | /  /\\| \\   |     |__/  |    |   | \\  |   / |/\\  \\ |  /\\| \\    \n\r";
		    boardString += " :-+-:_<%^   :-+-:   |     |  \\   \\  /    |  \\ |   :-+-:   ^%>_:-+-:    \n\r";
		    boardString += " \\_|_/       \\_|_/   |     |   \\   --     -    -   \\_|_/       \\_|_/    \n\r";

			// boardString += "   /===============================\    "
			// boardString += "  / _______  ___     __     _    _  \   "
			// boardString += " /     |     |  \   /  \    |\   |   \  "
			// boardString += "||     |     |__/  |    |   | \  |   || "
			// boardString += " \     |     |  \   \  /    |  \ |   /  "
			// boardString += "  \    |     |   \   --     -    -  /   "
			// boardString += "   \===============================/    "

		    String rowString="+";
		    for (int i=0; i<BOARD_WIDTH-1; i++) {
		      rowString+="---.";
		    }
		    rowString+="---+";
		    boardString += rowString + "\n\r"; //prints out the top line

		    //prints out the body of the board
		    for (int row=0; row<(2*BOARD_HEIGHT-1); row++) {
		      if (row%2==0) { //vertical rows
		        rowString="|";
		        for (int j=0; j<BOARD_WIDTH; j++) {
		          rowString+=" ";
		          rowString+=intToAscii(board[row/2+1][j+1]); //fill with values from gameState
		          //rowString+=""
		          rowString+=" |";
		        }
		        boardString += rowString + "\n\r";
		      } else { //horizontal rows
		        rowString="|";
		        for (int j=0; j<BOARD_WIDTH; j++) {
		            rowString+="---+";
		        }
		        boardString += rowString + "\n\r";
		      }
		    }
	        rowString="+";
	        for (int j=0; j<BOARD_WIDTH-1; j++) {
	          rowString+="---^";
	        }
	        rowString+="---+";
	        boardString += rowString + "\n\r"; //prints out the bottom line
		    return boardString;
  }
  
  public String intToAscii(int entry) {
	  if (entry==0) {
		  return " ";
	  } else if (entry==BIKE1) {
		  return "o";
	  } else if (entry==BIKE2) {
		  return "X";
	  } else if (entry==TAIL1) {
		  return "M";
	  } else if (entry==TAIL2) {
		  return "+";
	  } else {
		  return "*";
	  }
  }
  
  public int isGameOver() {
	  if (board[player2Row][player2Col]==BIKE1+BIKE2) {
		  return 0;
	  } else if (board[player1Row][player1Col]!=BIKE1) {
		  return 2;
	  } else if (board[player2Row][player2Col]!=BIKE2) {
		  System.out.println("2"+board[player2Row][player2Col]);
		  return 1;
	  } else {
		  return -1;
	  }
  }

}
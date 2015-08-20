package tron.data;
import static tron.enums.Direction.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import tron.enums.Team;
import tron.enums.Unit;

public class TronBoard {
	private static final int BOARD_HEIGHT = 15;
	private static final int BOARD_WIDTH = 15;
	private int[][] board= new Piece[BOARD_HEIGHT+2][BOARD_WIDTH+2];
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
        for (int j=0; i<BOARD_WIDTH+2; j++) {
          board[i][j]=5;
        }
      } else {
        for (int j=0; i<BOARD_WIDTH+2; j++) {
          if (j==0 || j==BOARD_WIDTH+1) {
            board[i][j]=5;
          } else {
            board[i][j]=0;
          }
        }
      }
    }
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
  
  public void update(String move1, String move2) {
    
  }

  public String botString() {return "";}

  public String toBigString() {
    String boardString = "";
    boardString += "       ___    @   _______  ___     __     _    _      @    ___          ";
    boardString += "      __|____/       |     |  \   /  \    |\   |       \____|__         ";
    boardString += "  _._/  |  / \_._    |     |  |  |    |   |    |    _._/ \  |  \_._     ";
    boardString += " / |/\  | /  /\| \   |     |__/  |    |   | \  |   / |/\  \ |  /\| \    ";
    boardString += " :-+-:_<%^   :-+-:   |     |  \   \  /    |  \ |   :-+-:   ^%>_:-+-:    ";
    boardString += " \_|_/       \_|_/   |     |   \   --     -    -   \_|_/       \_|_/    ";

	// boardString += "   /===============================\    "
	// boardString += "  / _______  ___     __     _    _  \   "
	// boardString += " /     |     |  \   /  \    |\   |   \  "
	// boardString += "||     |     |__/  |    |   | \  |   || "
	// boardString += " \     |     |  \   \  /    |  \ |   /  "
	// boardString += "  \    |     |   \   --     -    -  /   "
	// boardString += "   \===============================/    "

    String rowString="+";
    for (int i=0; i<BOARD_HEIGHT-1; i++) {
      rowString+="---.";
    }
    rowString+="---+";
    boardString += rowString; //prints out the top line

    //prints out the body of the board
    for (int row=0; row<(2*BOARD_HEIGHT-1); row++) {
      if (row%2==0) { //vertical rows
        rowString="|";
        for (int j=0; j<BOARD_WIDTH; j++) {
          rowString+=" ";
          if (board[row/2+1][j+1]==0)
            rowString+=" "; //fill with values from gameState
          else
            rowString+=board[row/2+1][j+1]; //fill with values from gameState
          //rowString+=""
          rowString+=" |";
        }
        boardString += rowString;
      } else { //horizontal rows
        rowString="|";
        for (int j=0; j<BOARD_WIDTH-1; j++) {
            rowString+="---+";
            boardString += rowString+"---|";
        }
        rowString="+";
        for (int j=0; j<BOARD_WIDTH-1; j++) {
          rowString+="---^";
        }
        rowString+="---+";
        boardString += rowString; //prints out the top line
      }
      return boardString;
  }

  public boolean isGameOver() {return false;}

}

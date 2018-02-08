/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexapawn;

/**
 *
 * @author Ari
 */
class Board {
    int size; 
    int[][] theBoard;

    public Board() {
        size = Global.GAMESIZE;
        theBoard = new int[size][size];
        setup();
    }
    public Board(Board b) {
        this();
        size = Global.GAMESIZE;
        theBoard = copy(b);
    }
    

    private int[][] copy(Board b) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                theBoard[i][j] = b.theBoard[i][j];
            }
        }
        return theBoard;
    }

    private void setup() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0) {
                    theBoard[0][j] = -1;
                }
                else {
                    theBoard[size-1][j] = 1;
                }
            }
        }
    }

    void makeMove(int fromRow, int fromCol, int row, int col, int turn) {
        theBoard[fromRow][fromCol] = 0;
        theBoard[row][col] = turn;
    }
    
    public String toString() {
        String returnMe = "";
        for (int i = 0; i < Global.GAMESIZE; i++) {
            for (int j = 0; j < Global.GAMESIZE; j++) {
                returnMe += theBoard[i][j] + " ";
            }
            returnMe += "\n";
        }
        return returnMe;
    }

    boolean isEqual(Board move) {
        for (int i = 0; i < Global.GAMESIZE; i++) {
            for (int j = 0; j < Global.GAMESIZE; j++) {
                if (theBoard[i][j] != move.theBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
}

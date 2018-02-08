/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hexapawn;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ari
 */
class NaryTree extends ArrayList<NaryTree> {

    Board currentBoard;
    boolean gameover;
    int turn; // true = black, -1     false = white, 1

    ArrayList<Board> nextMoveList = new ArrayList<Board>();
    private ArrayList<Board> losingMovesList = new ArrayList();

    NaryTree(Board b, int turn, boolean go) {
        currentBoard = b;
        this.turn = turn;
        gameover = go;
    }

    public int[][] getCurrBoard() {
        return currentBoard.theBoard;

    }

    public Board validMove(int fromRow, int fromCol, int row, int col) {
        Board move = new Board(currentBoard);
        move.makeMove(fromRow, fromCol, row, col, turn);
        if (win(move)) {
            gameover = true;
        }
//        System.out.println("nextMoveList = \n" + nextMoveList);
        for (Board nextMove : nextMoveList) {
            if (nextMove.isEqual(move)) {
//                System.out.println("valid move");
                return move;
            }
        }
        return currentBoard;
    }

    public Board bestMove() {
        int rand = (int) (Math.random() * nextMoveList.size());
        for (Board nextMove : nextMoveList) {
            if (win(nextMove)) {

                gameover = true;
                return nextMove;
            }
        }
        if (nextMoveList.isEmpty()) {
            gameover = true;
            return currentBoard;
        }
        
        return nextMoveList.get(rand);
    }

    void addChildren(int d) {
        if (d > 0 && !gameover) {
            for (int i = 0; i < Global.GAMESIZE; i++) {
                for (int j = 0; j < Global.GAMESIZE; j++) {
                    if (currentBoard.theBoard[i][j] == turn) {
                        checkMoves(i, j);
                    }
                }
            }
        }
    }

    

    private void checkMoves(int row, int col) {
        Board b = new Board(currentBoard);

        if (row - turn >= 0 && row - turn < Global.GAMESIZE) {
            if (getCurrBoard()[row - turn][col] == 0) { //forward clear
                b.theBoard[row][col] = 0;
                b.theBoard[row - turn][col] = turn;
                nextMoveList.add(b);
                b = new Board(currentBoard);
                //        System.out.println("nextMoveList = \n" + nextMoveList);

            }
        }
        if (row - turn >= 0 && col + turn >= 0 && row - turn < Global.GAMESIZE && col + turn < Global.GAMESIZE) {
            if (getCurrBoard()[row - turn][col + turn] == -turn) { //can attack to left
                b.theBoard[row][col] = 0;
                b.theBoard[row - turn][col + turn] = turn;
                nextMoveList.add(b);
                b = new Board(currentBoard);
            }
        }
        if (row - turn >= 0 && col - turn < Global.GAMESIZE && row - turn < Global.GAMESIZE && col - turn >= 0) {
            if (getCurrBoard()[row - turn][col - turn] == -turn) { //can attack to right
                b.theBoard[row][col] = 0;
                b.theBoard[row - turn][col - turn] = turn;
                nextMoveList.add(b);
                b = new Board(currentBoard);

            }
        }

    }

    public Board aiRandomMove() {
//        TimeUnit.SECONDS.sleep(2);
        Board move = new Board(currentBoard);
        int rand = (int) (Math.random() * nextMoveList.size());
//         System.out.println("rand = " + rand);
        if (win(nextMoveList.get(rand))) {
            gameover = true;
        }
        return nextMoveList.get(rand);
    }

    public Board aiMove(ArrayList<Board> losingMovesList) {
        this.losingMovesList = losingMovesList;
//        boolean valid = false;
        Board move;
//        while (!valid) {
        int rand = (int) (Math.random() * nextMoveList.size());
//            if (!trim(nextMoveList.get(rand))) {
        move = new Board(nextMoveList.get(rand));
//                valid = true;
//            }
//        }
//        if (win(move)) {
//            gameover = true;
//        }
        for (Board nextMove : nextMoveList) {
            boolean bad = false;
            for (Board badMove : losingMovesList) {
                if (nextMove.isEqual(badMove)) {
                    bad = true;
                }
            }
//            System.out.println("nextMove");
            if (!bad) {
                if (win(nextMove)) {
//                    System.out.println("gameover");
                    gameover = true;
                }
                return nextMove;
            }
        }

//        System.out.println("no good moves left");
        return move;
    }

    public boolean trim(Board b) {
        for (Board move : losingMovesList) {
            if (b.isEqual(move)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String returnMe = "Current Board : \n";
        returnMe += currentBoard.toString();
        returnMe += "Next Move List :";
        for (Board nextMove : nextMoveList) {
            returnMe += "\n" + nextMove.toString();
        }
        return returnMe;
    }

    private boolean win(Board move) {

        if (noMoves(move)) {
//            System.out.println("nothin");
            return true;
        } else if (turn == 1) {
            for (int i = 0; i < Global.GAMESIZE; i++) {
                if (move.theBoard[0][i] == turn) {
                    return true;
                }
            }
            return false;

        } else if (turn == -1) {
            for (int i = 0; i < Global.GAMESIZE; i++) {
                if (move.theBoard[Global.GAMESIZE - 1][i] == turn) {
                    return true;
                }
            }
            return false;

        }
        return false;
    }

    private boolean noMoves(Board b) {
//        System.out.println(" in no moves");
        NaryTree newTree = new NaryTree(b, -turn, false);
        newTree.addChildren(1);

        if (newTree.nextMoveList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}

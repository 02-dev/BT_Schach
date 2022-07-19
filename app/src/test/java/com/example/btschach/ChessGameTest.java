package com.example.btschach;

import static org.junit.Assert.*;

import com.example.btschach.controller.ChessGame;
import com.example.btschach.model.Bishop;
import com.example.btschach.model.Chesspiece;
import com.example.btschach.model.King;
import com.example.btschach.model.Knight;
import com.example.btschach.model.Pawn;
import com.example.btschach.model.Queen;
import com.example.btschach.model.Rook;

import org.junit.Before;
import org.junit.Test;

public class ChessGameTest {

    private ChessGame gameStart;
    private ChessGame boardEmpty;

    @Before
    public void setUp() {
        gameStart = new ChessGame();
        boardEmpty = new ChessGame();
        boardEmpty.testClearBoard();
        boardEmpty.testPlaceChesspiece(new King("white"), 7, 7);
        boardEmpty.testPlaceChesspiece(new King("black"), 0, 0);
    }

    // Pawn
        // Move
            // Black
                // Possible
                    @Test
                    public void pawnMoveBlackPossible(){
                        gameStart.testSetCurrentPlayer(false);
                        gameStart.selectBoardSpace(1, 0, false, true);
                        gameStart.selectBoardSpace(2, 0, false, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[2][0].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[2][0].get(0)).getColor(), "black");
                        assertNull(gameStart.getChessboard()[1][0].get(0));
                    }
                // Impossible
                    @Test
                    public void pawnMoveBlackImpossible(){
                        gameStart.testSetCurrentPlayer(false);
                        gameStart.testPlaceChesspiece(new Queen("white"), 2,0);
                        gameStart.selectBoardSpace(1, 0, false, true);
                        gameStart.selectBoardSpace(2, 0, false, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[1][0].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[1][0].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[2][0].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[2][0].get(0)).getColor(), "white");
                    }
            // White
                // Possible
                    @Test
                    public void pawnMoveWhitePossible(){
                        gameStart.selectBoardSpace(6, 3, true, true);
                        gameStart.selectBoardSpace(4, 3, true, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[4][3].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[4][3].get(0)).getColor(), "white");
                        assertNull(gameStart.getChessboard()[6][3].get(0));
                    }
                // Impossible
                    @Test
                    public void pawnMoveWhiteImpossible(){
                        gameStart.testPlaceChesspiece(new Queen("black"), 5,3);
                        gameStart.selectBoardSpace(6, 3, true, true);
                        gameStart.selectBoardSpace(4, 3, true, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[6][3].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[6][3].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[5][3].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[5][3].get(0)).getColor(), "black");
                    }
        // Capture
            // Black
                // Possible
                    @Test
                    public void pawnCaptureBlackPossible(){
                        gameStart.testSetCurrentPlayer(false);
                        gameStart.testPlaceChesspiece(new Queen("white"), 2,1);
                        gameStart.selectBoardSpace(1, 0, false, true);
                        gameStart.selectBoardSpace(2, 1, false, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[2][1].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[2][1].get(0)).getColor(), "black");
                        assertNull(gameStart.getChessboard()[1][0].get(0));
                    }
                // Impossible
                    @Test
                    public void pawnCaptureBlackImpossible(){
                        gameStart.testSetCurrentPlayer(false);
                        gameStart.selectBoardSpace(1, 0, false, true);
                        gameStart.selectBoardSpace(2, 1, false, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[1][0].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[1][0].get(0)).getColor(), "black");
                        assertNull(gameStart.getChessboard()[2][1].get(0));
                    }
            // White
                // Possible
                    @Test
                    public void pawnCaptureWhitePossible(){
                        gameStart.testPlaceChesspiece(new Queen("black"), 5,4);
                        gameStart.selectBoardSpace(6, 3, true, true);
                        gameStart.selectBoardSpace(5, 4, true, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[5][4].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[5][4].get(0)).getColor(), "white");
                        assertNull(gameStart.getChessboard()[6][3].get(0));
                    }
                // Impossible
                    @Test
                    public void pawnCaptureWhiteImpossible(){
                        gameStart.selectBoardSpace(6, 3, true, true);
                        gameStart.selectBoardSpace(5, 4, true, true);
                        assertEquals(((Chesspiece)gameStart.getChessboard()[6][3].get(0)).getName(), "Pawn");
                        assertEquals(((Chesspiece)gameStart.getChessboard()[6][3].get(0)).getColor(), "white");
                        assertNull(gameStart.getChessboard()[5][4].get(0));
                    }
        // Promotion
            // Black
            // White

    // Rook
        // Move
            // Possible
                // Up - Black
                    @Test
                    public void rookMovePossibleUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(0, 4, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down - White
                    @Test
                    public void rookMovePossibleDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(7, 4, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Left - Black
                @Test
                public void rookMovePossibleLeftBlack(){
                    boardEmpty.testSetCurrentPlayer(false);
                    boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                    boardEmpty.selectBoardSpace(4, 4, false, true);
                    boardEmpty.selectBoardSpace(4, 0, false, true);
                    assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][0].get(0)).getName(), "Rook");
                    assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][0].get(0)).getColor(), "black");
                    assertNull(boardEmpty.getChessboard()[4][4].get(0));
                }
                // Right - White
                    @Test
                    public void rookMovePossibleRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(4, 7, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][7].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][7].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
            // Impossible
                // Up - Black
                    @Test
                    public void rookMoveImpossibleUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 2, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(0, 4, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[0][4].get(0));
                    }
                // Down - White
                    @Test
                    public void rookMoveImpossibleDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 6, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(7, 4, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[7][4].get(0));
                    }
                // Left - Black
                    @Test
                    public void rookMoveImpossibleLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 2);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(4, 0, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][0].get(0));
                    }
                // Right - White
                    @Test
                    public void rookMoveImpossibleRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(4, 7, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][7].get(0));
                    }
        // Capture
            // Possible
                // Up - Black
                    @Test
                    public void rookCapturePossibleUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 2, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(2, 4, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down - White
                    @Test
                    public void rookCapturePossibleDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 6, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 4, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Left - Black
                    @Test
                    public void rookCapturePossibleLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 2);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(4, 2, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][2].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][2].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Right - White
                    @Test
                    public void rookCapturePossibleRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(4, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][6].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][6].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
            // Impossible
                // Up - Black
                    @Test
                    public void rookCaptureImpossibleUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 2, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(2, 4, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][4].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");

                    }
                // Down - White
                    @Test
                    public void rookCaptureImpossibleDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 6, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 4, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][4].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                    }
                // Left - Black
                    @Test
                    public void rookCaptureImpossibleLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 2);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(4, 2, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][2].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][2].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                    }
                // Right - White
                    @Test
                    public void rookCaptureImpossibleRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(4, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][6].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][6].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                    }

    // Knight
        // Move
            // Possible
                // Up-left - Black
                    @Test
                    public void knightMovePossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(2, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][3].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Up-right - White
                    @Test
                    public void knightMovePossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(2, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][5].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-left - Black
                    @Test
                    public void knightMovePossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(6, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][3].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-right - White
                    @Test
                    public void knightMovePossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][5].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Left-up - Black
                    @Test
                    public void knightMovePossibleLeftUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 2, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][2].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][2].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Left-down - White
                    @Test
                    public void knightMovePossibleLeftDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 2, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][2].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][2].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Right-up - Black
                    @Test
                    public void knightMovePossibleRightUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 6, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][6].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][6].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Right-down - White
                    @Test
                    public void knightMovePossibleRightDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][6].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][6].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
            // Impossible
                // Up-left - Black
                    @Test
                    public void knightMoveImpossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 2, 3);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(2, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][3].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][3].get(0)).getColor(), "black");
                    }
                // Up-right - White
                    @Test
                    public void knightMoveImpossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 2, 5);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(2, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][5].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][5].get(0)).getColor(), "white");
                    }
                // Down-left - Black
                    @Test
                    public void knightMoveImpossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 6, 3);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(6, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][3].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][3].get(0)).getColor(), "black");
                    }
                // Down-right - White
                    @Test
                    public void knightMoveImpossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 6, 5);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][5].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][5].get(0)).getColor(), "white");
                    }
                // Left-up - Black
                    @Test
                    public void knightMoveImpossibleLeftUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 3, 2);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 2, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][2].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][2].get(0)).getColor(), "black");
                    }
                // Left-down - White
                    @Test
                    public void knightMoveImpossibleLeftDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 5, 2);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 2, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][2].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][2].get(0)).getColor(), "white");
                    }
                // Right-up - Black
                    @Test
                    public void knightMoveImpossibleRightUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 3, 6);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 6, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][6].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][6].get(0)).getColor(), "black");
                    }
                // Right-down - White
                    @Test
                    public void knightMoveImpossibleRightDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 5, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][6].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][6].get(0)).getColor(), "white");
                    }
        // Capture
            // Possible
                // Up-left - Black
                    @Test
                    public void knightCapturePossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 2, 3);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(2, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][3].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Up-right - White
                    @Test
                    public void knightCapturePossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 2, 5);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(2, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][5].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[2][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-left - Black
                    @Test
                    public void knightCapturePossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 6, 3);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(6, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][3].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-right - White
                    @Test
                    public void knightCapturePossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 6, 5);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][5].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Left-up - Black
                    @Test
                    public void knightCapturePossibleLeftUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 3, 2);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 2, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][2].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][2].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Left-down - White
                    @Test
                    public void knightCapturePossibleLeftDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 5, 2);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 2, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][2].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][2].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Right-up - Black
                    @Test
                    public void knightCapturePossibleRightUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Knight("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 3, 6);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 6, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][6].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][6].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Right-down - White
                    @Test
                    public void knightCapturePossibleRightDownWhite(){
                        boardEmpty.testPlaceChesspiece(new Knight("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 5, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][6].get(0)).getName(), "Knight");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][6].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
            // Impossible - same check as for impossible movement, therefore no additional tests needed

    // Bishop
        // Move
            // Possible
                // Up-left - Black
                    @Test
                    public void bishopMovePossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(1, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][1].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][1].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Up-right - White
                    @Test
                    public void bishopMovePossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(1, 7, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][7].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][7].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-left - Black
                    @Test
                    public void bishopMovePossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(7, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-right - White
                    @Test
                    public void bishopMovePossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][6].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][6].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
            // Impossible
                // Up-left - Black
                    @Test
                    public void bishopMoveImpossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 3, 3);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(1, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[1][1].get(0));
                    }
                // Up-right - White
                    @Test
                    public void bishopMoveImpossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 2, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(1, 7, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[1][7].get(0));
                    }
                // Down-left - Black
                    @Test
                    public void bishopMoveImpossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 6, 2);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(7, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[7][1].get(0));
                    }
                // Down-right - White
                    @Test
                    public void bishopMoveImpossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 5, 5);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[6][6].get(0));
                    }
        // Capture
            // Possible
                // Up-left - Black
                    @Test
                    public void bishopCapturePossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 1, 1);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(1, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][1].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][1].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Up-right - White
                    @Test
                    public void bishopCapturePossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 1, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(1, 7, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][7].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][7].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-left - Black
                    @Test
                    public void bishopCapturePossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 7, 1);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(7, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-right - White
                    @Test
                    public void bishopCapturePossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 6, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][6].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][6].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
            // Impossible
                // Up-left - Black
                    @Test
                    public void bishopCaptureImpossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 1, 1);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(1, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][1].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][1].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                    }
                // Up-right - White
                    @Test
                    public void bishopCaptureImpossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 1, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(1, 7, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][7].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[1][7].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                    }
                // Down-left - Black
                    @Test
                    public void bishopCaptureImpossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new Bishop("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 7, 1);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(7, 1, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                    }
                // Down-right - White
                    @Test
                    public void bishopCaptureImpossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new Bishop("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 6, 6);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(6, 6, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][6].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[6][6].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "Bishop");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                    }

    // Queen - uses the same movement patterns as the rook and the bishop, therefore no tests needed

    // King
        // Move
            // Possible
                // Up - Black
                    @Test
                    public void kingMovePossibleUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 4, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down - White
                    @Test
                    public void kingMovePossibleDownWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 4, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Left - Black
                    @Test
                    public void kingMovePossibleLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(4, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][3].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Right - White
                    @Test
                    public void kingMovePossibleRightWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(4, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Up-left - Black
                    @Test
                    public void kingMovePossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][3].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Up-right - White
                    @Test
                    public void kingMovePossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(3, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-left - Black
                    @Test
                    public void kingMovePossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(5, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][3].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-right - White
                    @Test
                    public void kingMovePossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
            // Impossible
                // Up - Black - blocked
                    @Test
                    public void kingMoveImpossibleUpBlackBlocked(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 3, 4);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 4, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][4].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][4].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                    }
                // Down - White - blocked
                    @Test
                    public void kingMoveImpossibleDownWhiteBlocked(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 5, 4);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 4, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][4].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][4].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");

                    }
                // Left - Black - blocked
                    @Test
                    public void kingMoveImpossibleLeftBlackBlocked(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 4, 3);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(4, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][3].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][3].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                    }
                // Right - White - blocked
                    @Test
                    public void kingMoveImpossibleRightWhiteBlocked(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 4, 5);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(4, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][5].get(0)).getName(), "Queen");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][5].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                    }
                // Up-left - Black - checked
                    @Test
                    public void kingMoveImpossibleUpLeftBlackChecked(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 3, 2);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[3][3].get(0));
                    }
                // Up-right - White - checked
                    @Test
                    public void kingMoveImpossibleUpRightWhiteChecked(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 3, 2);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(3, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[3][5].get(0));
                    }
                // Down-left - Black - checked
                    @Test
                    public void kingMoveImpossibleDownLeftBlackChecked(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 5, 2);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(5, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[5][3].get(0));
                    }
                // Down-right - White - checked
                    @Test
                    public void kingMoveImpossibleDownRightWhiteChecked(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 5, 2);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[5][5].get(0));
                    }
        // Capture
            // Possible
                // Up - Black
                    @Test
                    public void kingCapturePossibleUpBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 3, 4);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 4, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][4].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down - White
                    @Test
                    public void kingCapturePossibleDownWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 5, 4);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 4, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][4].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][4].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));

                    }
                // Left - Black
                    @Test
                    public void kingCapturePossibleLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 4, 3);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(4, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][3].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Right - White
                    @Test
                    public void kingCapturePossibleRightWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 4, 5);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(4, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[4][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));

                    }
                // Up-left - Black
                    @Test
                    public void kingCapturePossibleUpLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 3, 3);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(3, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][3].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Up-right - White
                    @Test
                    public void kingCapturePossibleUpRightWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 3, 5);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(3, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[3][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));

                    }
                // Down-left - Black
                    @Test
                    public void kingCapturePossibleDownLeftBlack(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("white"), 5, 3);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.selectBoardSpace(4, 4, false, true);
                        boardEmpty.selectBoardSpace(5, 3, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][3].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][3].get(0)).getColor(), "black");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));
                    }
                // Down-right - White
                    @Test
                    public void kingCapturePossibleDownRightWhite(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 4, 4);
                        boardEmpty.testPlaceChesspiece(new Queen("black"), 5, 5);
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.selectBoardSpace(4, 4, true, true);
                        boardEmpty.selectBoardSpace(5, 5, true, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[5][5].get(0)).getColor(), "white");
                        assertNull(boardEmpty.getChessboard()[4][4].get(0));

                    }
            // Impossible - same check as for impossible movement, therefore no additional tests needed

        // Castling
            // Black
                // Left
                    @Test
                    public void castlingBlackLeft(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(new King("black"), 0, 3);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 0, 0);
                        boardEmpty.selectBoardSpace(0, 3, false, true);
                        boardEmpty.selectBoardSpace(0, 0, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][1].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][1].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][2].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][2].get(0)).getColor(), "black");
                    }
                // Right
                    @Test
                    public void castlingBlackRight(){
                        boardEmpty.testSetCurrentPlayer(false);
                        boardEmpty.testPlaceChesspiece(null, 0, 0);
                        boardEmpty.testPlaceChesspiece(new King("black"), 0, 3);
                        boardEmpty.testPlaceChesspiece(new Rook("black"), 0, 7);
                        boardEmpty.selectBoardSpace(0, 3, false, true);
                        boardEmpty.selectBoardSpace(0, 7, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][5].get(0)).getColor(), "black");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[0][4].get(0)).getColor(), "black");
                    }
            // White
                // Left
                    @Test
                    public void castlingWhiteLeft(){
                        boardEmpty.testPlaceChesspiece(null, 7, 7);
                        boardEmpty.testPlaceChesspiece(new King("white"), 7, 3);
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 7, 0);
                        boardEmpty.selectBoardSpace(7, 3, false, true);
                        boardEmpty.selectBoardSpace(7, 0, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][1].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][2].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][2].get(0)).getColor(), "white");
                    }
                // Right
                    @Test
                    public void castlingWhiteRight(){
                        boardEmpty.testPlaceChesspiece(new King("white"), 7, 3);
                        boardEmpty.testPlaceChesspiece(new Rook("white"), 7, 7);
                        boardEmpty.selectBoardSpace(7, 3, false, true);
                        boardEmpty.selectBoardSpace(7, 7, false, true);
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][5].get(0)).getName(), "King");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][5].get(0)).getColor(), "white");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][4].get(0)).getName(), "Rook");
                        assertEquals(((Chesspiece)boardEmpty.getChessboard()[7][4].get(0)).getColor(), "white");
                    }

    // Game Finished
        // Draw
            // Black
                @Test
                public void gameFinishedDrawBlack(){
                    boardEmpty.testSetCurrentPlayer(false);
                    boardEmpty.testPlaceChesspiece(new Rook("black"), 6, 0);
                    boardEmpty.testPlaceChesspiece(new Rook("black"), 0, 5);

                    boardEmpty.selectBoardSpace(0, 5, false, true);
                    boardEmpty.selectBoardSpace(0, 6, false, true);
                    assertEquals("draw", gameStart.getVictory());
                }
            // White
                @Test
                public void gameFinishedDrawWhite(){
                    boardEmpty.testPlaceChesspiece(new Bishop("white"), 6, 7);
                    boardEmpty.testPlaceChesspiece(new Bishop("white"), 7, 6);
                    boardEmpty.testPlaceChesspiece(new Bishop("white"), 1, 3);
                    boardEmpty.selectBoardSpace(1, 3, true, true);
                    boardEmpty.selectBoardSpace(0, 2, true, true);
                    assertEquals("draw", gameStart.getVictory());
                }
        // Checkmate
            // Black
                @Test
                public void gameFinishedCheckmateBlack(){
                    gameStart.testSetCurrentPlayer(false);
                    gameStart.testPlaceChesspiece(new Bishop("black"), 4, 1);
                    gameStart.testPlaceChesspiece(new Queen("black"), 4, 7);

                    gameStart.selectBoardSpace(4, 7, false, true);
                    gameStart.selectBoardSpace(6, 5, false, true);
                    assertEquals("victory", gameStart.getVictory());
                }
            // White
                @Test
                public void gameFinishedCheckmateWhite(){
                    gameStart.testPlaceChesspiece(new Bishop("white"), 4, 2);
                    gameStart.testPlaceChesspiece(new Queen("white"), 3, 7);

                    gameStart.selectBoardSpace(3, 7, true, true);
                    gameStart.selectBoardSpace(1, 5, true, true);
                    assertEquals("victory", gameStart.getVictory());
                }
}
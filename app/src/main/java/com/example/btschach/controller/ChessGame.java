package com.example.btschach.controller;

import android.util.Log;

import com.example.btschach.model.Bishop;
import com.example.btschach.model.Chesspiece;
import com.example.btschach.model.King;
import com.example.btschach.model.Knight;
import com.example.btschach.model.Pawn;
import com.example.btschach.model.Queen;
import com.example.btschach.model.Rook;

import java.io.Serializable;
import java.util.ArrayList;

public class ChessGame implements Serializable {

    //format: {Chesspiece chesspiece, String spaceState}
    private ArrayList<Object>[][] chessboard;

    // format: {Chesspiece chesspiece, int locationI, int locationJ}
    private ArrayList<Object> currentlySelected;

    // true when it is the host's turn (white), false when it is the other player's turn (black)
    private boolean currentPlayer;

    // needed for the castling check
    private boolean wkStartingPosition = true;
    private boolean bkStartingPosition = true;
    private boolean wrlStartingPosition = true;
    private boolean wrrStartingPosition = true;
    private boolean brlStartingPosition = true;
    private boolean brrStartingPosition = true;

    // needed to check whether the game is over
    private String victory = null;

    public ChessGame() {
        currentPlayer = true;
        currentlySelected = new ArrayList<>();
        currentlySelected.add(null);
        currentlySelected.add(null);
        currentlySelected.add(null);

        chessboard = new ArrayList[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 && (j == 0 || j == 7)) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Rook("black"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 0 && (j == 1 || j == 6)) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Knight("black"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 0 && (j == 2 || j == 5)) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Bishop("black"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 0 && j == 3) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Queen("black"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 0 && j == 4) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new King("black"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 1) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Pawn("black"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 6) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Pawn("white"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 7 && (j == 0 || j == 7)) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Rook("white"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 7 && (j == 1 || j == 6)) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Knight("white"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 7 && (j == 2 || j == 5)) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Bishop("white"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 7 && j == 3) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new Queen("white"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else if (i == 7 && j == 4) {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(new King("white"));
                    temp.add(null);
                    chessboard[i][j] = temp;
                } else {
                    ArrayList<Object> temp = new ArrayList<>();
                    temp.add(null);
                    temp.add(null);
                    chessboard[i][j] = temp;
                }
            }
        }
    }

    public ArrayList<Object>[][] getChessboard() {
        return chessboard;
    }

    public ArrayList<Object> getCurrentlySelected() {
        return  currentlySelected;
    }

    public boolean getCurrentPlayer() {
        return currentPlayer;
    }

    public String getVictory() { return victory; }

    public void selectBoardSpace(int i, int j, boolean currentPlayer, boolean clicked){
        if (this.currentPlayer == currentPlayer) {
            if (currentlySelected.get(0) == (null)) {
                if (chessboard[i][j].get(0) != null) {
                    Chesspiece chesspiece = (Chesspiece)chessboard[i][j].get(0);
                    if ((chesspiece.getColor().equals("white") && this.currentPlayer) || (chesspiece.getColor().equals("black") && !this.currentPlayer)) {
                        if (clicked) {
                            chessboard[i][j].set(1, "blue");
                        }
                        switch (chesspiece.getName()) {
                            case "Pawn":
                                if (chesspiece.getColor().equals("white")) {
                                    if (i == 6) {
                                        if (chessboard[i-2][j].get(0) == null && chessboard[i-1][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j)) {
                                                chessboard[i - 2][j].set(1, "green");
                                            }
                                        }
                                    }
                                    if (chessboard[i-1][j].get(0) == null) {
                                        if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j)) {
                                            chessboard[i - 1][j].set(1, "green");
                                        }
                                    }
                                    if (j-1 >= 0 && chessboard[i-1][j-1].get(0) != null) {
                                        Chesspiece capturableChesspiece = (Chesspiece)chessboard[i-1][j-1].get(0);
                                        if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j-1)) {
                                                chessboard[i - 1][j - 1].set(1, "red");
                                            }
                                        }
                                    }
                                    if (j+1 <= 7 && chessboard[i-1][j+1].get(0) != null) {
                                        Chesspiece capturableChesspiece = (Chesspiece)chessboard[i-1][j+1].get(0);
                                        if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j+1)) {
                                                chessboard[i - 1][j + 1].set(1, "red");
                                            }
                                        }
                                    }
                                } else {
                                    if (i == 1) {
                                        if (chessboard[i + 2][j].get(0) == null && chessboard[i + 1][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j)) {
                                                chessboard[i + 2][j].set(1, "green");
                                            }
                                        }
                                    }
                                    if (chessboard[i + 1][j].get(0) == null) {
                                        if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j)) {
                                            chessboard[i + 1][j].set(1, "green");
                                        }
                                    }
                                    if (j+1 <= 7 && chessboard[i + 1][j + 1].get(0) != null) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i + 1][j + 1].get(0);
                                        if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j+1)) {
                                                chessboard[i + 1][j + 1].set(1, "red");
                                            }
                                        }
                                    }
                                    if (j-1 >= 0 && chessboard[i + 1][j - 1].get(0) != null) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i + 1][j - 1].get(0);
                                        if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j-1)) {
                                                chessboard[i + 1][j - 1].set(1, "red");
                                            }
                                        }
                                    }
                                }break;
                            case "Rook":
                                if (chesspiece.getColor().equals("white")) {
                                    // up
                                    for (int x = i-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // down
                                    for (int x = i+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // left
                                    for (int x = j-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // right
                                    for (int x = j+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                } else {
                                    // up
                                    for (int x = i-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // down
                                    for (int x = i+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // left
                                    for (int x = j-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // right
                                    for (int x = j+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                } break;
                            case "Knight":
                                if (chesspiece.getColor().equals("white")) {
                                    // up
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j-1].get(0);
                                        if (chessboard[i-2][j-1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j-1)) {
                                                chessboard[i - 2][j - 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j-1)) {
                                                chessboard[i - 2][j - 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j+1].get(0);
                                        if (chessboard[i-2][j+1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j+1)) {
                                                chessboard[i - 2][j + 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j+1)) {
                                                chessboard[i - 2][j + 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j-1].get(0);
                                        if (chessboard[i+2][j-1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j-1)) {
                                                chessboard[i + 2][j - 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j-1)) {
                                                chessboard[i + 2][j - 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j+1].get(0);
                                        if (chessboard[i+2][j+1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j+1)) {
                                                chessboard[i + 2][j + 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j+1)) {
                                                chessboard[i + 2][j + 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j-2].get(0);
                                        if (chessboard[i-1][j-2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j-2)) {
                                                chessboard[i - 1][j - 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j-2)) {
                                                chessboard[i - 1][j - 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j-2].get(0);
                                        if (chessboard[i+1][j-2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j-2)) {
                                                chessboard[i + 1][j - 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j-2)) {
                                                chessboard[i + 1][j - 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j+2].get(0);
                                        if (chessboard[i-1][j+2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j+2)) {
                                                chessboard[i - 1][j + 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j+2)) {
                                                chessboard[i - 1][j + 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j+2].get(0);
                                        if (chessboard[i+1][j+2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j+2)) {
                                                chessboard[i + 1][j + 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j+2)) {
                                                chessboard[i + 1][j + 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                } else {
                                    // up
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j-1].get(0);
                                        if (chessboard[i-2][j-1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j-1)) {
                                                chessboard[i - 2][j - 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j-1)) {
                                                chessboard[i - 2][j - 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j+1].get(0);
                                        if (chessboard[i-2][j+1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j+1)) {
                                                chessboard[i - 2][j + 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-2, j+1)) {
                                                chessboard[i - 2][j + 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j-1].get(0);
                                        if (chessboard[i+2][j-1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j-1)) {
                                                chessboard[i + 2][j - 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j-1)) {
                                                chessboard[i + 2][j - 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j+1].get(0);
                                        if (chessboard[i+2][j+1].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j+1)) {
                                                chessboard[i + 2][j + 1].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+2, j+1)) {
                                                chessboard[i + 2][j + 1].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j-2].get(0);
                                        if (chessboard[i-1][j-2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j-2)) {
                                                chessboard[i - 1][j - 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j-2)) {
                                                chessboard[i - 1][j - 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j-2].get(0);
                                        if (chessboard[i+1][j-2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j-2)) {
                                                chessboard[i + 1][j - 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j-2)) {
                                                chessboard[i + 1][j - 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j+2].get(0);
                                        if (chessboard[i-1][j+2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j+2)) {
                                                chessboard[i - 1][j + 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i-1, j+2)) {
                                                chessboard[i - 1][j + 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j+2].get(0);
                                        if (chessboard[i+1][j+2].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j+2)) {
                                                chessboard[i + 1][j + 2].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i+1, j+2)) {
                                                chessboard[i + 1][j + 2].set(1, "red");
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                } break;
                            case "Bishop":
                                if (chesspiece.getColor().equals("white")) {
                                    // up left
                                    int y = j-1;
                                    for (int x = i-1; x >= 0 && y >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // up right
                                    y = j+1;
                                    for (int x = i-1; x >= 0 && y <= 7; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                    // down left
                                    y = j-1;
                                    for (int x = i+1; x <= 7 && y >= 0; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // down right
                                    y = j+1;
                                    for (int x = i+1; x <= 7 && y <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                } else {
                                    // up left
                                    int y = j-1;
                                    for (int x = i-1; x >= 0 && y >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // up right
                                    y = j+1;
                                    for (int x = i-1; x >= 0 && y <= 7; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                    // down left
                                    y = j-1;
                                    for (int x = i+1; x <= 7 && y >= 0; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // down right
                                    y = j+1;
                                    for (int x = i+1; x <= 7 && y <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                } break;
                            case "Queen":
                                if (chesspiece.getColor().equals("white")) {
                                    // up
                                    for (int x = i-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // down
                                    for (int x = i+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // left
                                    for (int x = j-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // right
                                    for (int x = j+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // up left
                                    int y = j-1;
                                    for (int x = i-1; x >= 0 && y >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // up right
                                    y = j+1;
                                    for (int x = i-1; x >= 0 && y <= 7; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                    // down left
                                    y = j-1;
                                    for (int x = i+1; x <= 7 && y >= 0; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // down right
                                    y = j+1;
                                    for (int x = i+1; x <= 7 && y <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("black")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                } else {
                                    // up
                                    for (int x = i-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // down
                                    for (int x = i+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                                        if (chessboard[x][j].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, j)) {
                                                chessboard[x][j].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // left
                                    for (int x = j-1; x >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // right
                                    for (int x = j+1; x <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                                        if (chessboard[i][x].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, i, x)) {
                                                chessboard[i][x].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                    }
                                    // up left
                                    int y = j-1;
                                    for (int x = i-1; x >= 0 && y >= 0; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // up right
                                    y = j+1;
                                    for (int x = i-1; x >= 0 && y <= 7; x--) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                    // down left
                                    y = j-1;
                                    for (int x = i+1; x <= 7 && y >= 0; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "red");
                                            }
                                            break;
                                        } else {break;}
                                        y--;
                                    }
                                    // down right
                                    y = j+1;
                                    for (int x = i+1; x <= 7 && y <= 7; x++) {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                                        if (chessboard[x][y].get(0) == null) {
                                            if (!simulateMoveKingChecked(chesspiece, i, j, x, y)) {
                                                chessboard[x][y].set(1, "green");
                                            }
                                        } else if (capturableChesspiece.getColor().equals("white")) {
                                            chessboard[x][y].set(1, "red");
                                            break;
                                        } else {break;}
                                        y++;
                                    }
                                } break;
                            case "King":
                                if (chesspiece.getColor().equals("white")) {
                                    // up
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i - 1][j].get(0);
                                        if (chessboard[i - 1][j].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i-1, j, "white")) {
                                                if (chessboard[i - 1][j].get(0) == null) {
                                                    chessboard[i - 1][j].set(1, "green");
                                                } else {
                                                    chessboard[i - 1][j].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i + 1][j].get(0);
                                        if (chessboard[i + 1][j].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i+1, j, "white")) {
                                                if (chessboard[i + 1][j].get(0) == null) {
                                                    chessboard[i + 1][j].set(1, "green");
                                                } else {
                                                    chessboard[i + 1][j].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][j-1].get(0);
                                        if (chessboard[i][j-1].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i, j-1, "white")) {
                                                if (chessboard[i][j-1].get(0) == null) {
                                                    chessboard[i][j-1].set(1, "green");
                                                } else {
                                                    chessboard[i][j-1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][j+1].get(0);
                                        if (chessboard[i][j+1].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i, j+1, "white")) {
                                                if (chessboard[i][j+1].get(0) == null) {
                                                    chessboard[i][j+1].set(1, "green");
                                                } else {
                                                    chessboard[i][j+1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // up left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j-1].get(0);
                                        if (chessboard[i-1][j-1].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i-1, j-1, "white")) {
                                                if (chessboard[i-1][j-1].get(0) == null) {
                                                    chessboard[i-1][j-1].set(1, "green");
                                                } else {
                                                    chessboard[i-1][j-1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // up right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j+1].get(0);
                                        if (chessboard[i-1][j+1].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i-1, j+1, "white")) {
                                                if (chessboard[i-1][j+1].get(0) == null) {
                                                    chessboard[i-1][j+1].set(1, "green");
                                                } else {
                                                    chessboard[i-1][j+1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j-1].get(0);
                                        if (chessboard[i+1][j-1].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i+1, j-1, "white")) {
                                                if (chessboard[i+1][j-1].get(0) == null) {
                                                    chessboard[i+1][j-1].set(1, "green");
                                                } else {
                                                    chessboard[i+1][j-1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j+1].get(0);
                                        if (chessboard[i+1][j+1].get(0) == null || capturableChesspiece.getColor().equals("black")) {
                                            if (!kingChecked(i+1, j+1, "white")) {
                                                if (chessboard[i+1][j+1].get(0) == null) {
                                                    chessboard[i+1][j+1].set(1, "green");
                                                } else {
                                                    chessboard[i+1][j+1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // castling
                                    if (wkStartingPosition) {
                                        if (wrlStartingPosition) {
                                            if (chessboard[7][1].get(0) == null && chessboard[7][2].get(0) == null && chessboard[7][3].get(0) == null) {
                                                if (!kingChecked(7, 4, "white") && !kingChecked(7, 3, "white") && !kingChecked(7, 2, "white")) {
                                                    chessboard[7][0].set(1, "green");
                                                }
                                            }
                                        }
                                        if (wrrStartingPosition) {
                                            if (chessboard[7][6].get(0) == null && chessboard[7][5].get(0) == null) {
                                                if (!kingChecked(7, 4, "white") && !kingChecked(7, 5, "white") && !kingChecked(7, 6, "white")) {
                                                    chessboard[7][7].set(1, "green");
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    // up
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i - 1][j].get(0);
                                        if (chessboard[i - 1][j].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i-1, j, "black")) {
                                                if (chessboard[i - 1][j].get(0) == null) {
                                                    chessboard[i - 1][j].set(1, "green");
                                                } else {
                                                    chessboard[i - 1][j].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i + 1][j].get(0);
                                        if (chessboard[i + 1][j].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i+1, j, "black")) {
                                                if (chessboard[i + 1][j].get(0) == null) {
                                                    chessboard[i + 1][j].set(1, "green");
                                                } else {
                                                    chessboard[i + 1][j].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][j-1].get(0);
                                        if (chessboard[i][j-1].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i, j-1, "black")) {
                                                if (chessboard[i][j-1].get(0) == null) {
                                                    chessboard[i][j-1].set(1, "green");
                                                } else {
                                                    chessboard[i][j-1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][j+1].get(0);
                                        if (chessboard[i][j+1].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i, j+1, "black")) {
                                                if (chessboard[i][j+1].get(0) == null) {
                                                    chessboard[i][j+1].set(1, "green");
                                                } else {
                                                    chessboard[i][j+1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // up left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j-1].get(0);
                                        if (chessboard[i-1][j-1].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i-1, j-1, "black")) {
                                                if (chessboard[i-1][j-1].get(0) == null) {
                                                    chessboard[i-1][j-1].set(1, "green");
                                                } else {
                                                    chessboard[i-1][j-1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // up right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j+1].get(0);
                                        if (chessboard[i-1][j+1].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i-1, j+1, "black")) {
                                                if (chessboard[i-1][j+1].get(0) == null) {
                                                    chessboard[i-1][j+1].set(1, "green");
                                                } else {
                                                    chessboard[i-1][j+1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down left
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j-1].get(0);
                                        if (chessboard[i+1][j-1].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i+1, j-1, "black")) {
                                                if (chessboard[i+1][j-1].get(0) == null) {
                                                    chessboard[i+1][j-1].set(1, "green");
                                                } else {
                                                    chessboard[i+1][j-1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // down right
                                    try {
                                        Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j+1].get(0);
                                        if (chessboard[i+1][j+1].get(0) == null || capturableChesspiece.getColor().equals("white")) {
                                            if (!kingChecked(i+1, j+1, "black")) {
                                                if (chessboard[i+1][j+1].get(0) == null) {
                                                    chessboard[i+1][j+1].set(1, "green");
                                                } else {
                                                    chessboard[i+1][j+1].set(1, "red");
                                                }
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
                                    // castling
                                    if (bkStartingPosition) {
                                        if (brlStartingPosition) {
                                            if (chessboard[0][1].get(0) == null && chessboard[0][2].get(0) == null && chessboard[0][3].get(0) == null) {
                                                if (!kingChecked(0, 4, "black") && !kingChecked(0, 3, "black") && !kingChecked(0, 2, "black")) {
                                                    chessboard[0][0].set(1, "green");
                                                }
                                            }
                                        }
                                        if (brrStartingPosition) {
                                            if (chessboard[0][6].get(0) == null && chessboard[0][5].get(0) == null) {
                                                if (!kingChecked(0, 4, "black") && !kingChecked(0, 5, "black") && !kingChecked(0, 6, "black")) {
                                                    chessboard[0][7].set(1, "green");
                                                }
                                            }
                                        }
                                    }
                                } break;
                        }
                        // unmark kings
                        for (int x = 0; x < 8; x++) {
                            for (int y = 0; y < 8; y++) {
                                if (chessboard[x][y].get(0) != null && ((Chesspiece) chessboard[i][j].get(0)).getName().equals("King")) {
                                    if (chessboard[x][y].get(1) != null && !chessboard[x][y].get(1).equals("blue"))
                                    chessboard[x][y].set(1, null);
                                }
                            }
                        }
                        if (clicked) {
                            currentlySelected.set(0, chesspiece);
                            currentlySelected.set(1, i);
                            currentlySelected.set(2, j);
                        }
                    }
                }
            } else {
                if (chessboard[i][j].get(1) != null && (chessboard[i][j].get(1).equals("red") || chessboard[i][j].get(1).equals("green"))) {
                    if (chessboard[i][j].get(0) != null && ((Chesspiece)currentlySelected.get(0)).getColor().equals(((Chesspiece)chessboard[i][j].get(0)).getColor())) {
                        if (i == 7) {
                            if (j == 0) {
                                chessboard[7][0].set(0, null);
                                chessboard[7][4].set(0, null);
                                chessboard[7][3].set(0, new Rook("white"));
                                chessboard[7][2].set(0, new King("white"));
                            } else {
                                chessboard[7][7].set(0, null);
                                chessboard[7][4].set(0, null);
                                chessboard[7][5].set(0, new Rook("white"));
                                chessboard[7][6].set(0, new King("white"));
                            }
                        } else {
                            if (j == 0) {
                                chessboard[0][0].set(0, null);
                                chessboard[0][4].set(0, null);
                                chessboard[0][3].set(0, new Rook("black"));
                                chessboard[0][2].set(0, new King("black"));
                            } else {
                                chessboard[0][7].set(0, null);
                                chessboard[0][4].set(0, null);
                                chessboard[0][5].set(0, new Rook("black"));
                                chessboard[0][6].set(0, new King("black"));
                            }
                        }
                    } else {
                        chessboard[i][j].set(0, currentlySelected.get(0));
                        chessboard[(int)currentlySelected.get(1)][(int)currentlySelected.get(2)].set(0, null);
                    }

                    if (((Chesspiece)currentlySelected.get(0)).getName().equals("King")) {
                        // white king
                        if ((int)currentlySelected.get(1) == 7 && (int)currentlySelected.get(2) == 4) {
                            wkStartingPosition = false;
                        }
                        // black king
                        if ((int)currentlySelected.get(1) == 0 && (int)currentlySelected.get(2) == 4) {
                            bkStartingPosition = false;
                        }
                    }
                    if (((Chesspiece)currentlySelected.get(0)).getName().equals("Rook")) {
                        // white rook left
                        if ((int)currentlySelected.get(1) == 7 && (int)currentlySelected.get(2) == 0) {
                            wrlStartingPosition = false;
                        }
                        // white rook right
                        if ((int)currentlySelected.get(1) == 7 && (int)currentlySelected.get(2) == 7) {
                            wrrStartingPosition = false;
                        }
                        // black rook left
                        if ((int)currentlySelected.get(1) == 0 && (int)currentlySelected.get(2) == 0) {
                            brlStartingPosition = false;
                        }
                        // black rook right
                        if ((int)currentlySelected.get(1) == 0 && (int)currentlySelected.get(2) == 7) {
                            brrStartingPosition = false;
                        }
                    }
                    this.currentPlayer = !this.currentPlayer;
                    checkVictory();
                    if (victory != null) {
                        this.currentPlayer = !this.currentPlayer;
                    }
                }

                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {
                        chessboard[x][y].set(1, null);
                    }
                }
                currentlySelected.set(0, null);
                currentlySelected.set(1, null);
                currentlySelected.set(2, null);
            }
        }
    }

    private boolean kingChecked(int i, int j, String color) {
        boolean checked = false;
        if (color.equals("white")) {
            // up
            for (int x = i-1; x >= 0; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                if (chessboard[x][j].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == i-1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
            }
            // down
            for (int x = i+1; x <= 7; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                if (chessboard[x][j].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == i+1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
            }
            // left
            for (int x = j-1; x >= 0; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                if (chessboard[i][x].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == j-1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[i][x].get(0) != null && !((Chesspiece) chessboard[i][x].get(0)).getName().equals("King")) {break;}
            }
            // right
            for (int x = j+1; x <= 7; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                if (chessboard[i][x].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == j+1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[i][x].get(0) != null && !((Chesspiece) chessboard[i][x].get(0)).getName().equals("King")) {break;}
            }
            // up left
            int y = j-1;
            for (int x = i-1; x >= 0 && y >= 0; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == i-1 && (capturableChesspiece.getName().equals("King") || capturableChesspiece.getName().equals("Pawn"))) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][y].get(0) != null && !((Chesspiece) chessboard[x][y].get(0)).getName().equals("King")) {break;}
                y--;
            }
            // up right
            y = j+1;
            for (int x = i-1; x >= 0 && y <= 7; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == i-1 && (capturableChesspiece.getName().equals("King") || capturableChesspiece.getName().equals("Pawn"))) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][y].get(0) != null && !((Chesspiece) chessboard[x][y].get(0)).getName().equals("King")) {break;}
                y++;
            }
            // down left
            y = j-1;
            for (int x = i+1; x <= 7 && y >= 0; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == i+1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][y].get(0) != null && !((Chesspiece) chessboard[x][y].get(0)).getName().equals("King")) {break;}
                y--;
            }
            // down right
            y = j+1;
            for (int x = i+1; x <= 7 && y <= 7; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("black")) {
                    if (x == i+1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][y].get(0) != null && !((Chesspiece) chessboard[x][y].get(0)).getName().equals("King")) {break;}
                y++;
            }
            // knight up
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j-1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j+1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            // knight down
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j-1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j+1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            // knight left
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j-2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j-2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            // knight right
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j+2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j+2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("black") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
        } else {
            // up
            for (int x = i-1; x >= 0; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                if (chessboard[x][j].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == i-1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
            }
            // down
            for (int x = i+1; x <= 7; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][j].get(0);
                if (chessboard[x][j].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == i+1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
            }
            // left
            for (int x = j-1; x >= 0; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                if (chessboard[i][x].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == j-1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
            }
            // right
            for (int x = j+1; x <= 7; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i][x].get(0);
                if (chessboard[i][x].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == j+1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Rook") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
            }
            // up left
            int y = j-1;
            for (int x = i-1; x >= 0 && y >= 0; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == i-1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
                y--;
            }
            // up right
            y = j+1;
            for (int x = i-1; x >= 0 && y <= 7; x--) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == i-1 && capturableChesspiece.getName().equals("King")) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
                y++;
            }
            // down left
            y = j-1;
            for (int x = i+1; x <= 7 && y >= 0; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == i+1 && (capturableChesspiece.getName().equals("King") || capturableChesspiece.getName().equals("Pawn"))) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
                y--;
            }
            // down right
            y = j+1;
            for (int x = i+1; x <= 7 && y <= 7; x++) {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[x][y].get(0);
                if (chessboard[x][y].get(0) != null && capturableChesspiece.getColor().equals("white")) {
                    if (x == i+1 && (capturableChesspiece.getName().equals("King") || capturableChesspiece.getName().equals("Pawn"))) {
                        checked = true;
                    }
                    if (capturableChesspiece.getName().equals("Bishop") || capturableChesspiece.getName().equals("Queen")) {
                        checked = true;
                    } break;
                } else if (chessboard[x][j].get(0) != null && !((Chesspiece) chessboard[x][j].get(0)).getName().equals("King")) {break;}
                y++;
            }
            // knight up
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j-1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-2][j+1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            // knight down
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j-1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+2][j+1].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            // knight left
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j-2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j-2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            // knight right
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i-1][j+2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
            try {
                Chesspiece capturableChesspiece = (Chesspiece) chessboard[i+1][j+2].get(0);
                if (capturableChesspiece != null && capturableChesspiece.getColor().equals("white") && capturableChesspiece.getName().equals("Knight")) {
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e) {e.printStackTrace();}
        }
        return checked;
    }

    private boolean simulateMoveKingChecked(Chesspiece chesspiece, int locCurI, int locCurJ, int locSimI, int locSimJ) {
        Chesspiece capturedPiece = (Chesspiece) chessboard[locSimI][locSimJ].get(0);
        chessboard[locSimI][locSimJ].set(0, chesspiece);
        chessboard[locCurI][locCurJ].set(0, null);

        int kingLocI = -1;
        int kingLocJ = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j].get(0) != null && ((Chesspiece) chessboard[i][j].get(0)).getName().equals("King") && ((Chesspiece) chessboard[i][j].get(0)).getColor().equals(chesspiece.getColor())) {
                    kingLocI = i;
                    kingLocJ = j;
                }
            }
        }
        boolean checked = kingChecked(kingLocI, kingLocJ, chesspiece.getColor());
        chessboard[locSimI][locSimJ].set(0, capturedPiece);
        chessboard[locCurI][locCurJ].set(0, chesspiece);
        return checked;
    }

    private void checkVictory() {
        boolean movesLeft = false;
        int kingI = -1;
        int kingJ = -1;
        String color;
        if (currentPlayer) {
            color = "white";
        } else {
            color = "black";
        }
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                chessboard[x][y].set(1, null);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j].get(0) != null && ((Chesspiece)chessboard[i][j].get(0)).getName().equals("King") && ((Chesspiece)chessboard[i][j].get(0)).getColor().equals(color)) {
                    kingI = i;
                    kingJ = j;
                }
                selectBoardSpace(i,j,currentPlayer, false);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <8; j++) {
                if (chessboard[i][j].get(1) != null) {
                    movesLeft = true;
                }
            }
        }
        if (!movesLeft) {
            if (kingChecked(kingI, kingJ, color)) {
                victory = "victory";
            } else {
                victory = "draw";
            }
        }
    }

    // just for Unit Tests
    public void testClearBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessboard[i][j].set(0, null);
                chessboard[i][j].set(1, null);
            }
        }
    }

    // just for Unit Tests
    public void testPlaceChesspiece(Chesspiece chesspiece, int i, int j) {
        chessboard[i][j].set(0, chesspiece);
    }

    // just for Unit Tests
    public void testSetCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}

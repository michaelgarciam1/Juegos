package com.example.juegos;

import android.util.Log;

import java.util.Random;

public class board2048 {
    private int[][] board;
    private int[][] boardAux;
    private int score;

    private int size;

    public board2048(int size) {
        this.size = size;
        this.board = new int[size][size];
        this.score = 0;
        addRandomNumber();

    }

    public void move(Direction direction) {
        //copia del tablero para el undomove
        boardAux = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardAux[i][j] = board[i][j];
            }
        }
        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
        if (!equals(boardAux)) {
            addRandomNumber();
        }
    }

    private void moveUp() {
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    int k;
                    for (k = i; k > 0 && board[k - 1][j] == 0; k--) {
                        board[k - 1][j] = board[k][j];
                        board[k][j] = 0;
                    }
                    mergeUp(k, j);
                }
            }
        }
    }

    private void mergeUp(int i, int j) {
        if (i > 0 && board[i - 1][j] == board[i][j]) {
            board[i - 1][j] *= 2;
            board[i][j] = 0;
            score += board[i - 1][j];
        }
    }

    private void moveDown() {
        for (int i = size - 2; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0) {
                    int k;
                    for (k = i; k < size - 1 && board[k + 1][j] == 0; k++) {
                        board[k + 1][j] = board[k][j];
                        board[k][j] = 0;
                    }
                    mergeDown(k, j);
                }
            }
        }
    }

    private void mergeDown(int i, int j) {
        if (i < size - 1 && board[i + 1][j] == board[i][j]) {
            board[i + 1][j] *= 2;
            board[i][j] = 0;
            score += board[i + 1][j];
        }
    }

    private void moveLeft() {
        for (int j = 1; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (board[i][j] != 0) {
                    int k;
                    for (k = j; k > 0 && board[i][k - 1] == 0; k--) {
                        board[i][k - 1] = board[i][k];
                        board[i][k] = 0;
                    }
                    mergeLeft(i, k);
                }
            }
        }
    }

    private void mergeLeft(int i, int j) {
        if (j > 0 && board[i][j - 1] == board[i][j]) {
            board[i][j - 1] *= 2;
            board[i][j] = 0;
            score += board[i][j - 1];
        }
    }

    private void moveRight() {
        for (int j = size - 2; j >= 0; j--) {
            for (int i = 0; i < size; i++) {
                if (board[i][j] != 0) {
                    int k;
                    for (k = j; k < size - 1 && board[i][k + 1] == 0; k++) {
                        board[i][k + 1] = board[i][k];
                        board[i][k] = 0;
                    }
                    mergeRight(i, k);
                }
            }
        }
    }

    private void mergeRight(int i, int j) {
        if (j < size - 1 && board[i][j + 1] == board[i][j]) {
            board[i][j + 1] *= 2;
            board[i][j] = 0;
            score += board[i][j + 1];
        }
    }

    private void addRandomNumber() {
        Random ran = new Random();
        int x, y;
        x = ran.nextInt(4);
        y = ran.nextInt(4);
        boolean empty = false;

        while (!empty) {
            Log.d("addRandomNumber", "x: " + x + " y: " + y);
            if (board[x][y] == 0) {
                board[x][y] = (2);
                empty = true;
            } else {
                x = ran.nextInt(4);
                y = ran.nextInt(4);
            }
        }
    }

    public void undoMove() {
        if (boardAux == null) {
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = boardAux[i][j];
            }
        }

    }

    public int isFinished() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //devuelve uno si es victoria
                if (board[i][j] == 2048) {
                    return 1;
                }
                //devuelve dos si se puede seguir jugando
                if (board[i][j] == 0) {
                    return 2;
                }
                if (i < size - 1 && board[i][j] == board[i + 1][j]) {
                    return 2;
                }
                if (j < size - 1 && board[i][j] == board[i][j + 1]) {
                    return 2;
                }
            }
        }
        //devuelve cero si es derrota
        return 0;
    }

    private boolean equals(int[][] boardAux) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != boardAux[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }
}

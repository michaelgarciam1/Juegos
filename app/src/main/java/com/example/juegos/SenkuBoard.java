package com.example.juegos;

public class SenkuBoard {
    private int[][] table = new int[7][7];
    private int[][] tableUndo;
    private int active = 0;

    public SenkuBoard() {

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if ((i < 2 && j < 2) || (i > 4 && j < 2) || (i < 2 && j > 4) || (i > 4 && j > 4)) {
                    table[i][j] = -1;
                } else {
                    table[i][j] = 1;
                    active++;

                }
            }
        }

        table[3][3] = 0;
        // se decrementa active por la casilla central
        active--;
        tableUndo = table;
    }

    public boolean isValidMove(int x, int y, int newX, int newY) {
        if (table[x][y] == 1 && table[newX][newY] == 0) {
            if (y != newY) {
                //movimiento derecha
                if (y < newY) {
                    if (y == newY - 2 && table[x][y + 1] == 1) {
                        tableUndo = copiarMatriz(table);
                        table[x][y] = 0;
                        table[x][y + 1] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
                //movimiento izquierda
                else {

                    if (y == newY + 2 && table[x][y - 1] == 1) {
                        tableUndo = copiarMatriz(table);
                        table[x][y] = 0;
                        table[x][y - 1] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
                //vertical
            } else {
                //movimiento abajo
                if (x < newX) {
                    if (x == newX - 2 && table[x + 1][y] == 1) {
                        tableUndo = copiarMatriz(table);
                        table[x][y] = 0;
                        table[x + 1][y] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
                //movimiento arriba
                else {
                    if (x == newX + 2 && table[x - 1][y] == 1) {
                        tableUndo = copiarMatriz(table);
                        table[x][y] = 0;
                        table[x - 1][y] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean move(int x, int y, int newX, int newY) {

        if (isValidMove(x, y, newX, newY)) {
            active--;
            return true;

        } else {
            System.out.println("Movimiento no valido");
            return false;
        }
    }

    private boolean existMove() {
        //devuelve true si existe algun movimiento valido sino devuelve false
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (table[i][j] == 1) {
                    //movimiento derecha
                    if (j < 5 && table[i][j + 1] == 1 && table[i][j + 2] == 0) {
                        return true;
                    }
                    //movimiento izquierda
                    if (j > 1 && table[i][j - 1] == 1 && table[i][j - 2] == 0) {
                        return true;
                    }
                    //movimiento abajo
                    if (i < 5 && table[i + 1][j] == 1 && table[i + 2][j] == 0) {
                        return true;
                    }
                    //movimiento arriba
                    if (i > 1 && table[i - 1][j] == 1 && table[i - 2][j] == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int finishGame() {
        if (active == 1) {
            // es una victoria
            return 1;
        }
        if (active > 1 && !existMove()) {
            // es una derrota
            return 2;
        }
        //no se ha acabado
        return 0;
    }

    private int[][] copiarMatriz(int[][] matriz) {
        int[][] copia = new int[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            copia[i] = matriz[i].clone();
        }
        return copia;
    }

    public void undo() {
        if (active == 32) {
            return;
        }
        table = copiarMatriz(tableUndo);
        active++;
    }

    public int getPosition(int x, int y) {
        return table[x][y];
    }
}


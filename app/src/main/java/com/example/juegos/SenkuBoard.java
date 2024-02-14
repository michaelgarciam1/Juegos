package com.example.juegos;

public class SenkuBoard {
    private int[][] table = new int[7][7];
    private int[][] tableUndo = new int[7][7];
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
        // se decrementa active porque ahora hay una casilla menos ocupada (la central)
        active--;

        tableUndo = table;
    }

    public boolean isValidMove(int x, int y, int newX, int newY) {
        //si el movimiento no es valido, se devuelve false
        //un movimiento valido siempre sera en linea recta, ya sea horizontal o vertical
        //si las coordenadas son validas y la casilla de destino esta vacia se puede hacer el movimiento
        System.out.println("x  " + x + "y  " + y + "newX  " + newX + "newY  " + newY);
        if (table[x][y] == 1 && table[newX][newY] == 0) {

            //horizontal
            if (y != newY) {
                //movimiento derecha
                if (y < newY) {
                    //verificamos que la casilla intermedia este ocupada (table[x+1][y]==1)
                    //y x==newX-2 para que el movimiento sea de 2 casillas
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
                    //Comprobamos que la casilla intermedia este ocupada y que el movimiento sea de 2 casillas
                    if (y == newY + 2 && table[x][y - 1] == 1) {
                        tableUndo = copiarMatriz(table);
                        table[x][y] = 0;
                        table[x][y - 1] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
                //vertical0000000000000
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


    public void move(int x, int y, int newX, int newY) {

        if (isValidMove(x, y, newX, newY)) {
            System.out.println("Movimiento valido");
            //se decrementa active porque se ha movido una ficha
            active--;

        } else {
            System.out.println("Movimiento no valido");
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
        table = copiarMatriz(tableUndo);
    }

    public int[][] getTable() {
        return table;
    }

    public int getPosition(int x, int y) {
        return table[x][y];
    }
}


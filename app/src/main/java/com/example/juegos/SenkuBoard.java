package com.example.juegos;

public class SenkuBoard {
    private int[][] table = new int[7][7];
    private int active = 0;

    public SenkuBoard() {
        //inicializar tablero
        //1 = casilla ocupada
        //0 = casilla vacia
        //-1 = casilla no valida
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                //Si la celda está en una de las esquinas del tablero, se marca como no válida
                if ((i < 2 && j < 2) || (i > 4 && j < 2) || (i < 2 && j > 4) || (i > 4 && j > 4)) {
                    table[i][j] = -1;
                } else {
                    table[i][j] = 1;
                    active++;
                }
            }
        }
        //casilla central vacia
        table[3][3] = 0;
        // se decrementa active porque ahora hay una casilla menos ocupada (la central)
        active--;
        System.out.println(active);
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
                    if (y == newY - 2 && table[x][y+1] == 1) {
                        table[x][y] = 0;
                        table[x][y+1] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
                //movimiento izquierda
                else {
                    //Comprobamos que la casilla intermedia este ocupada y que el movimiento sea de 2 casillas
                    if (y == newY + 2 && table[x][y-1] == 1) {
                        table[x][y] = 0;
                        table[x][y-1] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
                //vertical0000000000000
            } else {

                //movimiento abajo
                if (x < newX) {
                    if (x == newX - 2 && table[x+1][y] == 1) {
                        table[x][y] = 0;
                        table[x+1][y] = 0;
                        table[newX][newY] = 1;
                        return true;
                    }
                }
                //movimiento arriba
                else {
                    if (x == newX + 2 && table[x - 1][y] == 1) {
                        table[x][y] = 0;
                        table[x-1][y] = 0;
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

    public int finishGame() {
        if (active > 1) {
            System.out.println("No se puede terminar el juego");
            return -1;
        } else if (active == 1 && table[3][3] == 1) {
            System.out.println("Has ganado");
            return 1;
        } else {
            System.out.println("Has perdido");
            return 0;
        }

    }

    public int[][] getTable() {
        return table;
    }

    public int getPosition(int x, int y) {
        return table[x][y];
    }
}


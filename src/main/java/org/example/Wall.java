package org.example;

public class Wall {

    private int y;
    private int x;
    private char symbol;

    public Wall(int y, int x) {
        this.y = y;
        this.x = x;
        this.symbol = '\u2588'; //2589
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public char getSymbol() {
        return symbol;
    }

}



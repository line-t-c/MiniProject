package org.example;

public class Player extends GameObject {
    public Player (int x, int y, char symbol) {
        super(x, y, symbol);
        super.previousX = x;
        super.previousY = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getGetPreviousY() {
        return previousY;
    }

    public void moveUp(){
        previousX = x;
        previousY = y;
        y--;
    }

    public void moveDown(){
        previousX = x;
        previousY = y;
        y++;
    }

    public void moveLeft(){
        previousX = x;
        previousY = y;
        x--;
    }

    public void moveRight(){
        previousX = x;
        previousY = y;
        x++;
    }
}

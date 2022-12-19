package org.example;

public class FallingObjects extends GameObject {

    int lenght;
    int hight;

    public FallingObjects (int x, int y, char symbol, int lenght, int hight) {
        super(x, y, symbol);
        super.previousX = x;
        super.previousY = y;
        this.lenght = lenght;
        this.hight = hight;
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

    public int getPreviousY() {
        return previousY;
    }

    //    Så de rykker sig nedad, men ikke til siden
    public void fall(){
        previousY = y;
        y++;
    }
}

package org.example;

public class GameObject {
    protected int x;
    protected int y;
    protected char symbol;
    protected int previousX;
    protected int previousY;

    public GameObject (int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
    }
}

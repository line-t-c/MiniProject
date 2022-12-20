package org.example;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GameObject {

//    DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
//    Terminal terminal = terminalFactory.createTerminal();
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

//    public void createObject (int b, int l) {
//        for (int i = 0; i < b; i++){
//            for (int j = 0; j < l; j++){
//                terminal.setCursorPosition(x, y);
//                terminal.putCharacter(symbol);
//            }
//            terminal.setCursorPosition(x, y);
//            terminal.putCharacter(symbol);
//        }
//    }


//    Definere her og metode for hvordan man rykker den her --> samme for player og obstacles
}

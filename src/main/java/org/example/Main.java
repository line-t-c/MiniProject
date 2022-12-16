package org.example;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
//        terminal.setCursorVisible(false); //Så kan man ikke se pilen

        final char car = 'X';
        final char block = '\u2588';
        final char point = '*';

        System.out.println("IDEAS");
        System.out.println("Bilspil");



    }
}
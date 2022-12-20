package org.example;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();

//        Så kan man ikke se pilen
        terminal.setCursorVisible(false);

//        Importerer random, så vi kan sætte random forhindringer
        Random r = new Random();

        final char obstacle = '☢';
        final char walls = '\u2590';
        final char points = '\u26ab';
        int pointsTotal = 0;

        List<FallingObjects> pointsList = new ArrayList<>();
        List<FallingObjects> obstacleList = new ArrayList<>();

//        Set walls. Terminal size fra 0,0 til 80,24
        printWalls(walls, terminal);

//        Print text on right and left side
        printSides(terminal);

//        Place player before start
        Player player = new Player(40, 22, '☃');
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.setForegroundColor(TextColor.ANSI.CYAN); //Fucker lidt op i farven

        terminal.flush();

//        Set up at den bliver ved at loope

        boolean continueReadingInput = true;

        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();

//            Printe i consollen
            System.out.println(type);
            System.out.println(c);

//            Sætte op, hvad der sker, når man trykker på tasterne, så den ikke kan rykke ind i væggen og top/bund

            switch (type) {
                case ArrowUp -> {
                    if (player.getY() != 0) {
                        player.moveUp();
                    }
                }
                case ArrowDown -> {
                    if (player.getY() != 23) {
                        player.moveDown();
                    }
                }
                case ArrowRight -> {
                    if (player.getX() != 59) {
                        player.moveRight();
                    }
                }
                case ArrowLeft -> {
                    if (player.getX() != 21) {
                        player.moveLeft();
                    }
                }
            }

//            Sætte at player rykker sig, men ikke har en hale efter sig
            terminal.setCursorPosition(player.getX(), player.getY());
            terminal.putCharacter(player.getSymbol());
            terminal.setCursorPosition(player.getPreviousX(), player.getGetPreviousY());
            terminal.putCharacter(' ');

            terminal.flush();

//        Add falling points and obstacles

            falls(terminal, points, pointsList, 0.2, TextColor.ANSI.YELLOW);
            falls(terminal, obstacle, obstacleList,0.3, TextColor.ANSI.GREEN);

            terminal.flush();

            ifHitsPoints(pointsList,terminal,player,pointsTotal);
            ifHitsObstacle(obstacleList,terminal, player);

            terminal.flush();

//            Add quit

            if (c == Character.valueOf('q')) {
                continueReadingInput = false;
                System.out.println("quit");
                terminal.close();
            }

            terminal.flush();

        }
    }


    public static void printWalls (char walls, Terminal terminal) throws IOException {
        for (int column = 0; column <= 24; column++) {
            terminal.setCursorPosition(20, column);
            terminal.putCharacter(walls);
        }

        for (int column = 0; column <= 24; column++) {
            terminal.setCursorPosition(60, column);
            terminal.putCharacter(walls);
        }
    }

    public static void printSides(Terminal terminal) throws IOException {

        String text = "CAR GAME";

        char[] carGame = text.toCharArray();
        for (int i = 0; i < carGame.length; i++) {
            terminal.setCursorPosition(i + 2, 2);
            terminal.putCharacter(carGame[i]);
        }

        String textQuit = "Quit game = \"q\"";

        char[] quitText = textQuit.toCharArray();
        for (int i = 0; i < quitText.length; i++) {
            terminal.setCursorPosition(i + 2, 20);
            terminal.putCharacter(quitText[i]);
        }

        String pointsText = "Points: ";

        char[] charAti = pointsText.toCharArray();
        for (int i = 0; i < charAti.length; i++) {
            terminal.setCursorPosition(i + 65, 2);
            terminal.putCharacter(charAti[i]);
        }
        terminal.setCursorPosition(74, 2);
        terminal.putCharacter('0');
        terminal.setCursorPosition(75, 2);
        terminal.putCharacter('0');
    }

    public static void falls(Terminal terminal, char symbol, List<FallingObjects> fallList, double probability, TextColor.ANSI color) throws IOException {
        double probabliity = ThreadLocalRandom.current().nextDouble();
        if (probabliity <= probability) {
            fallList.add(new FallingObjects(ThreadLocalRandom.current().nextInt(39) + 21, 0, symbol));
        }

        for (FallingObjects fallingObjects : fallList) {
            fallingObjects.fall(); // altså y++

            terminal.setCursorPosition(fallingObjects.getX(), fallingObjects.getY());
            terminal.putCharacter(fallingObjects.getSymbol());

            terminal.setCursorPosition(fallingObjects.getPreviousX(), fallingObjects.getPreviousY());
            terminal.putCharacter(' ');

            terminal.setForegroundColor(color);
        }
    }

    public static void ifHitsPoints(List<FallingObjects> list, Terminal terminal, Player player, int pointsTotal) throws IOException {

        for (FallingObjects fallingObjects : list) {

            if (fallingObjects.getX() == player.getX() && fallingObjects.getY() == player.getY()) {
                pointsTotal++;
                terminal.setCursorPosition(74, 2);
                char p = (char) pointsTotal;
                terminal.putCharacter('x');

                System.out.println("Points: " + pointsTotal);

            }
        }
        System.out.println("Points: " + pointsTotal);

        terminal.flush();

    }

    public static void ifHitsObstacle(List<FallingObjects> list, Terminal terminal, Player player) throws IOException {

        for (FallingObjects fallingObjects : list) {

            if (fallingObjects.getX() == player.getX() && fallingObjects.getY() == player.getY()) {
                terminal.clearScreen();
                String gameOver = "GAME OVER ";
                char[] charAtj = gameOver.toCharArray();
                for (int i = 0; i < charAtj.length; i++) {
                    terminal.setCursorPosition(i + 35, 15);
                    terminal.putCharacter(charAtj[i]);
                }
            }
        }
    }
}
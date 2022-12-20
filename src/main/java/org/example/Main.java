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

        final char obstacle = '\u25b2';
        final char walls = '\u2590';
        final char points = '\u26ab';
        final char playerChar = '\u2588';
        int pointsTotal = 0;

        List<FallingObjects> pointsList = new ArrayList<>();
        List<FallingObjects> obstacleList = new ArrayList<>();

//        Set walls. Terminal size fra 0,0 til 80,24
        printWalls(walls, terminal);

//        Print text on right and left side
        printSides(terminal);

//        Player, større

        Player player = new Player(40, 20, playerChar);
//        Player playerUp = new Player(40, 19, playerChar);
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.setForegroundColor(TextColor.ANSI.RED);
//        terminal.setCursorPosition(playerUp.getX(), playerUp.getY());
//        terminal.putCharacter(playerUp.getSymbol());

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

            switch (type) {
                case ArrowUp -> {
                    if (player.getY() != 0) {
                        player.moveUp();
//                        playerUp.moveUp();
                        terminal.setForegroundColor(TextColor.ANSI.RED);
                    }
                }
                case ArrowDown -> {
                    if (player.getY() != 24) {
                        player.moveDown();
//                        playerUp.moveDown();
                        terminal.setForegroundColor(TextColor.ANSI.RED);
                    }
                }
                case ArrowRight -> {
                    if (player.getX() != 59) {
//                        playerUp.moveRight();
                        player.moveRight();
                        terminal.setForegroundColor(TextColor.ANSI.RED);
                    }
                }
                case ArrowLeft -> {
                    if (player.getX() != 21) {
                        player.moveLeft();
//                        playerUp.moveLeft();
                        terminal.setForegroundColor(TextColor.ANSI.RED);
                    }
                }

            }

//            Sætte at playerChar rykker sig, men ikke har en hale efter sig
            terminal.setCursorPosition(player.getX(), player.getY());
            terminal.putCharacter(player.getSymbol());

            terminal.setCursorPosition(player.getPreviousX(), player.getGetPreviousY());
            terminal.putCharacter(' ');

//            terminal.setCursorPosition(player.getX(), player.getY() + 1);
//            terminal.putCharacter(player.getSymbol());
//            terminal.setForegroundColor(TextColor.ANSI.RED);

//            switch (type) {
//                case ArrowUp -> {
//                    terminal.setCursorPosition(player.getPreviousX(), player.getGetPreviousY());
//                    terminal.putCharacter(playerChar);
//                    terminal.setCursorPosition(player.getPreviousX(), player.getGetPreviousY());
//                    terminal.putCharacter(' ');
//                }
//                case ArrowDown -> {
//                    terminal.setCursorPosition(playerUp.getPreviousX(), playerUp.getGetPreviousY());
//                    terminal.putCharacter(' ');
//                    terminal.setCursorPosition(player.getPreviousX(), player.getGetPreviousY());
//                    terminal.putCharacter(playerChar);
//                }
//                case ArrowRight, ArrowLeft -> {
//                    terminal.setCursorPosition(playerUp.getPreviousX(), playerUp.getGetPreviousY());
//                    terminal.putCharacter(' ');
//                    terminal.setCursorPosition(player.getPreviousX(), player.getGetPreviousY());
//                    terminal.putCharacter(' ');
//                }
//            }



//        Add falling points and obstacles

            falls(terminal, points, pointsList, 0.3, TextColor.ANSI.YELLOW);
            falls(terminal, obstacle, obstacleList,0.2, TextColor.ANSI.GREEN);
//            fallObstacle(terminal, 'X', obstacleList, 0.1, TextColor.ANSI.CYAN);

            ifHitsPoints(pointsList,terminal,player,pointsTotal);
//            ifHitsPoints(pointsList,terminal,playerUp,pointsTotal);
            ifHitsObstacle(obstacleList,terminal, player, true, pointsTotal);
//            ifHitsObstacle(obstacleList,terminal, playerUp, true, pointsTotal);

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
        for (int column = 0; column <= 24; column++) {
            terminal.setCursorPosition(28, column);
            terminal.putCharacter('\u2584');
        }

        for (int column = 0; column <= 24; column++) {
            terminal.setCursorPosition((60-8), column);
            terminal.putCharacter('\u2584');
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

    }

    public static void falls(Terminal terminal, char symbol, List<FallingObjects> fallList, double probability, TextColor.ANSI color) throws IOException {
        double probabliity = ThreadLocalRandom.current().nextDouble();
        if (probabliity <= probability) {
            fallList.add(new FallingObjects(ThreadLocalRandom.current().nextInt(29,51), 0, symbol));
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
    public static void fallObstacle(Terminal terminal, char symbol, List<FallingObjects> fallList, double probability, TextColor.ANSI color) throws IOException {
        double probabliity = ThreadLocalRandom.current().nextDouble();
        if (probabliity <= probability) {
            fallList.add(new FallingObjects(ThreadLocalRandom.current().nextInt(29,51), 0, symbol));
        }

        for (FallingObjects fallingObjects : fallList) {
            fallingObjects.fall();
            terminal.setCursorPosition(fallingObjects.getX(), fallingObjects.getY());
            terminal.putCharacter(fallingObjects.getSymbol());
            terminal.setForegroundColor(color);

            int extraObject = 1;

            Random r = new Random();
            for (int i = 0; i < r.nextInt(1,4); i++){
                terminal.setCursorPosition(fallingObjects.getX() + extraObject, fallingObjects.getY());
                terminal.putCharacter(symbol);
                terminal.setForegroundColor(color);
                terminal.setCursorPosition(fallingObjects.getPreviousX() + extraObject, fallingObjects.getPreviousY());
                terminal.putCharacter(' ');

                extraObject += extraObject;
            }
        }
    }

    public static void ifHitsPoints(List<FallingObjects> list, Terminal terminal, Player player, int pointsTotal) throws IOException {

        for (FallingObjects fallingObjects : list) {

            if (fallingObjects.getX() == player.getX() && fallingObjects.getY() == player.getY()) {
                pointsTotal += 1;

                String points = String.valueOf(pointsTotal);

                char[] charAtk = points.toCharArray();
                for (int i = 0; i < charAtk.length; i++) {
                    terminal.setCursorPosition(i + 74, 2);
                    terminal.putCharacter(charAtk[i]);
                }
            }
        }
        System.out.println("Points: " + pointsTotal);
    }

    public static void ifHitsObstacle(List<FallingObjects> list, Terminal terminal, Player player, boolean continueReadingInput, int pointsTotal) throws IOException {

        for (FallingObjects fallingObjects : list) {

            if (fallingObjects.getX() == player.getX() && fallingObjects.getY() == player.getY()) {
                continueReadingInput = false;
                terminal.clearScreen();
                String gameOver = "GAME OVER ";
                char[] charAtj = gameOver.toCharArray();
                for (int i = 0; i < charAtj.length; i++) {
                    terminal.setCursorPosition(i + 35, 12);
                    terminal.putCharacter(charAtj[i]);
                }
                String pointsText = "Points: ";

                char[] charAti = pointsText.toCharArray();
                for (int i = 0; i < charAti.length; i++) {
                    terminal.setCursorPosition(i + 65, 2);
                    terminal.putCharacter(charAti[i]);
                }

                String points = String.valueOf(pointsTotal);

                char[] charAtk = points.toCharArray();
                for (int i = 0; i < charAtk.length; i++) {
                    terminal.setCursorPosition(i + 74, 2);
                    terminal.putCharacter(charAtk[i]);
                }
            }
        }
    }
}
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
//        final char points = '☆';
        final char points = '\u26ab';
        int pointsTotal = 0;
        final char walls = '\u2590';

        List<FallingObjects> pointsList = new ArrayList<>();
        List<FallingObjects> obstacleList = new ArrayList<>();
        List<FallingObjects> obstacleTreeList = new ArrayList<>();

//        Set walls. Terminal size fra 0,0 til 80,24

        for (int column = 0; column <=24; column++) {
            terminal.setCursorPosition(20, column);
            terminal.putCharacter(walls);
        }

        for (int column = 0; column <=24; column++) {
            terminal.setCursorPosition(60, column);
            terminal.putCharacter(walls);
        }

//        Set Game Name
        String text = "CAR GAME";

        char[] carGame = text.toCharArray();
        for (int i = 0; i < carGame.length; i++) {
            terminal.setCursorPosition(i+2, 2);
            terminal.putCharacter(carGame[i]);
        }

        //        Set Quit Game
        String textQuit = "Quit game = \"q\"";

        char[] quitText = textQuit.toCharArray();
        for (int i = 0; i < quitText.length; i++) {
            terminal.setCursorPosition(i+2, 20);
            terminal.putCharacter(quitText[i]);
        }

//        Points
        String pointsText = "Points: ";

        char[] charAti = pointsText.toCharArray();
        for (int i = 0; i < charAti.length; i++) {
            terminal.setCursorPosition(i+65, 2);
            terminal.putCharacter(charAti[i]);
        }
        terminal.setCursorPosition(74,2);
        terminal.putCharacter('0');
        terminal.setCursorPosition(75,2);
        terminal.putCharacter('0');

//        Place player
        Player player = new Player(40,22,'☃');
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.setForegroundColor(TextColor.ANSI.CYAN); //Fucker lidt op i farven

        terminal.flush();

        boolean continueReadingInput = true;

        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

        KeyType type = keyStroke.getKeyType();
        Character c = keyStroke.getCharacter();
        System.out.println(type);
        System.out.println(c);

        switch (type){
            case ArrowUp -> {
                if (player.getY()!=0) {
                    player.moveUp();
                } break;
            }
            case ArrowDown -> {
                if (player.getY()!=23) {
                    player.moveDown();
                } break;
            }
            case ArrowRight -> {
                if (player.getX()!=59) {
                    player.moveRight();
                } break;
            }
            case ArrowLeft -> {
                if (player.getX()!=21) {
                    player.moveLeft();
                } break;
            }
        }

        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.setCursorPosition(player.getPreviousX(), player.getGetPreviousY());
        terminal.putCharacter(' ');

        terminal.flush();

//        Add Points
        double probabliity = ThreadLocalRandom.current().nextDouble();
        if (probabliity <= 0.2) {
            pointsList.add(new FallingObjects(ThreadLocalRandom.current().nextInt(39) + 21, 0, points, 1, 1));
        }

        for (FallingObjects fallingObjects : pointsList) {
            fallingObjects.fall(); // altså y++
        }

        for (FallingObjects fallingObjects : pointsList){
            terminal.setCursorPosition(fallingObjects.getX(),fallingObjects.getY());
            terminal.putCharacter(fallingObjects.getSymbol());
            terminal.setCursorPosition(fallingObjects.getPreviousX(),fallingObjects.getPreviousY());
            terminal.putCharacter(' ');
            terminal.setForegroundColor(TextColor.ANSI.YELLOW);
        }

//        Points skal forsvinde når player rør dem
//            Ikke alle bliver talt med i points
        for (FallingObjects fallingObjects : pointsList) {
            if (fallingObjects.getX() == player.getX() && fallingObjects.getY() == player.getY()) {
                terminal.setCursorPosition(fallingObjects.getX(),fallingObjects.getY());
                terminal.putCharacter(' ');
                pointsTotal++;
                terminal.setCursorPosition(74,2);
                char p = (char)pointsTotal;
                terminal.putCharacter(p);
            }
        }

        System.out.println("Points: "+pointsTotal);

        terminal.flush();


//        Add Obstacles

        if (probabliity <= 0.3) {
            obstacleList.add(new FallingObjects(ThreadLocalRandom.current().nextInt(39) + 21, 0, obstacle, 3, 1));
        }

        for (FallingObjects fallingObjects : obstacleList) {
            fallingObjects.fall(); // altså y++
        }

        for (FallingObjects fallingObjects : obstacleList){
            terminal.setCursorPosition(fallingObjects.getX(),fallingObjects.getY());
            terminal.putCharacter(fallingObjects.getSymbol());
            terminal.setCursorPosition(fallingObjects.getPreviousX(),fallingObjects.getPreviousY());
            terminal.putCharacter(' ');
            terminal.setForegroundColor(TextColor.ANSI.GREEN);
        }

        for (FallingObjects fallingObjects : obstacleList) {
            if (fallingObjects.getX() == player.getX() && fallingObjects.getY() == player.getY()) {
                continueReadingInput = false;
                terminal.clearScreen();

                String gameOver = "GAME OVER ";
                char[] charAtj = gameOver.toCharArray();
                for (int i = 0; i < charAtj.length; i++) {
                    terminal.setCursorPosition(i+35, 15);
                    terminal.putCharacter(charAtj[i]);
                }
            }
        }

//        add bus
//        obstacleTreeList.add(new FallingObjects(ThreadLocalRandom.current().nextInt(39) + 21, 0, '\u2589', 3, 1));
//
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 3; j++) {
//                terminal.setCursorPosition();
//                terminal.putCharacter('\u2589');
//                terminal.setForegroundColor(TextColor.ANSI.YELLOW);
//            }

        if (c == Character.valueOf('q')) {
            continueReadingInput = false;
            System.out.println("quit");
            terminal.close();
        }

        terminal.flush();

        }
    }
//    public static boolean isPoint (Player player, Position points){
//        return position.getX()==points.getX() && position.getY()==points.getY();
//        pointsTotal++;
//    }

//    public static boolean isObstacle (List<FallingObjects> fallingObjectsList, Player player){
//        return player.getX()==fallingObjectsList.getX && player.getY()==fallingObjectsList.getY();
//    }

//    if (isObstacle(new Position(x,y),bombPosition)){
//        terminal.close();
//        System.out.println("\n\t\tBOMB went off and closed the program");
//        break;
//    }
}
package org.example;

import org.example.Exception.DuplicateSymbolException;
import org.example.Exception.InvalidBotCountException;
import org.example.Exception.InvalidPlayerCountException;
import org.example.controller.GameController;
import org.example.models.*;
import org.example.strategies.winning.ColumnWinningStrategy;
import org.example.strategies.winning.DiagonalWinningStrategy;
import org.example.strategies.winning.RowWinningStrategy;
import org.example.strategies.winning.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws InvalidBotCountException, DuplicateSymbolException, InvalidPlayerCountException {
        System.out.println( "Starting Game!!" );
        int dimensionOfGame = 3;

        List<Player> players = new ArrayList<>();
        players.add(
                new Player(1L, "Naman", new Symbol('X'), PlayerType.HUMAN)
        );

        players.add(
                new Bot(2L, "GPT", new Symbol('O'), BotDifficultyLevel.EASY)
        );

        List<WinningStrategy> winningStrategies = List.of(
                new RowWinningStrategy(),
                new ColumnWinningStrategy(),
                new DiagonalWinningStrategy()
        );

        GameController gameController = new GameController();
        try {
            Game game = gameController.startGame(dimensionOfGame, players, winningStrategies);
            while(gameController.checkState(game).equals(GameState.IN_PROGRESS)){
                gameController.printBoard(game);

                Scanner scanner = new Scanner(System.in);
                System.out.println("Do you want to undo last move? (y/n)");
                if(scanner.next().equalsIgnoreCase("y")){
                    gameController.undo(game);
                    continue;
                }

                gameController.makeMove(game);
            }
            System.out.println("Game is completed");
            GameState state = gameController.checkState(game);

            if (state.equals(GameState.DRAWN)) {
                System.out.println("It is a draw");
            } else {
                System.out.println("Winner is " + gameController.getWinner(game).getName());
            }
        } catch (Exception e) {
            throw e;
        }
    }
}

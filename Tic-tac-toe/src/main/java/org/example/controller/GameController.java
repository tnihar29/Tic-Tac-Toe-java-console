package org.example.controller;

import org.example.Exception.DuplicateSymbolException;
import org.example.Exception.InvalidBotCountException;
import org.example.Exception.InvalidPlayerCountException;
import org.example.models.Game;
import org.example.models.GameState;
import org.example.models.Player;
import org.example.strategies.winning.WinningStrategy;

import java.util.List;

public class GameController {

    public Game startGame(int size, List<Player> players, List<WinningStrategy> winningStrategies) throws InvalidBotCountException, DuplicateSymbolException, InvalidPlayerCountException {
        return Game.getBuilder().setSize(size).setPlayers(players).setWinningStrategies(winningStrategies).build();
    }

    public void makeMove(Game game){
        game.makeMove();
    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    public Player getWinner(Game game){
        return game.getWinner();
    }

    public void printBoard(Game game){
        game.printBoard();
    }

    public void undo(Game game){
        game.handleUndo();
    }
}

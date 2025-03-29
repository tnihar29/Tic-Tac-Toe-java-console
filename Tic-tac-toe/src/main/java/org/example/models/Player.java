package org.example.models;

import java.util.Scanner;

public class Player {
    private long id;

    private String name;

    private PlayerType playerType;

    private Symbol symbol;

    private Scanner scanner;

    public Player(long id, String name, Symbol symbol, PlayerType playerType){
        this.id=id;
        this.name = name;
        this.playerType = playerType;
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Move makeMove(Board board){
        System.out.println("Please select the row (starting from 0)");
        int row = scanner.nextInt();
        System.out.println("Please select the column (starting from 0)");
        int column = scanner.nextInt();
        return new Move(new Cell(row, column),this);
    }
}

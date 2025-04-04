package org.example.models;

public class Cell {

    private int row;

    private int column;

    private Player player;

    private CellState cellState;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        this.cellState = CellState.EMPTY;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public CellState getCellState() {
        return cellState;
    }

    public void setCellState(CellState cellState) {
        this.cellState = cellState;
    }

    public void display(){
        if(this.cellState.equals(CellState.EMPTY)){
            System.out.print("| - |");
        }else if(this.cellState.equals(CellState.FILLED)){
            System.out.print("| "+this.player.getSymbol().getaChar()+" |");
        }
    }
}

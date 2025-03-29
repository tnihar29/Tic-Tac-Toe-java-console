package org.example.strategies.winning;

import org.example.models.Board;
import org.example.models.Move;
import org.example.models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{

    Map<Symbol, Integer> leftDiagonalCount = new HashMap<>();
    Map<Symbol, Integer> rightDiagonalCount = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        if(row == col){
            leftDiagonalCount.put(symbol, leftDiagonalCount.getOrDefault(symbol,0)+1);

            if(leftDiagonalCount.get(symbol) == board.getSize()) return true;
        }

        if(row + col == board.getSize()-1){
            rightDiagonalCount.put(symbol, rightDiagonalCount.getOrDefault(symbol,0)+1);

            return rightDiagonalCount.get(symbol) == board.getSize();
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        if(row == col){
            leftDiagonalCount.put(symbol, leftDiagonalCount.get(symbol)-1);
        }

        if(row + col == board.getSize()-1){
            rightDiagonalCount.put(symbol, rightDiagonalCount.get(symbol)-1);
        }
    }
}

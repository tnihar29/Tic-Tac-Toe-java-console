package org.example.strategies.winning;

import org.example.models.Board;
import org.example.models.Move;
import org.example.models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{

    Map<Integer, Map<Symbol, Integer>> countSymbols = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        countSymbols.computeIfAbsent(row, k -> new HashMap<>());

        Map<Symbol,Integer> rowMap = countSymbols.get(row);

        rowMap.put(symbol, rowMap.getOrDefault(symbol,0)+1);

        return rowMap.get(symbol) == board.getSize();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol,Integer> rowMap = countSymbols.get(row);

        rowMap.put(symbol, rowMap.get(symbol) - 1);
    }
}

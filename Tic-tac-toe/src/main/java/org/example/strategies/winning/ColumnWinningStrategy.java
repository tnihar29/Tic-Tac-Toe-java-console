package org.example.strategies.winning;

import org.example.models.Board;
import org.example.models.Move;
import org.example.models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColumnWinningStrategy implements WinningStrategy{

    Map<Integer, Map<Symbol, Integer>> countSymbols = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        countSymbols.computeIfAbsent(col, k -> new HashMap<>());

        Map<Symbol,Integer> colMap = countSymbols.get(col);

        colMap.put(symbol, colMap.getOrDefault(symbol,0)+1);

        return colMap.get(symbol) == board.getSize();
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getColumn();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol,Integer> colMap = countSymbols.get(col);

        colMap.put(symbol, colMap.get(symbol) - 1);
    }
}

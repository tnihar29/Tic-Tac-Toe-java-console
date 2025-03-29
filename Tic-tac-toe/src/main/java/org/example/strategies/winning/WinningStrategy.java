package org.example.strategies.winning;

import org.example.models.Board;
import org.example.models.Move;

public interface WinningStrategy {

    boolean checkWinner(Board board, Move move);

    void handleUndo(Board board, Move move);
}

package org.example.models;

import org.example.Exception.DuplicateSymbolException;
import org.example.Exception.InvalidBotCountException;
import org.example.Exception.InvalidPlayerCountException;
import org.example.strategies.winning.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private Board board;

    private List<Player> players;

    private GameState gameState;

    private List<Move> moves;

    private Player winner;

    private int nextPlayerIndex;

    private List<WinningStrategy> winningStrategies;

    public static Builder getBuilder(){
        return new Builder();
    }

    private Game(int size, List<Player> players, List<WinningStrategy> winningStrategies){
        this.players = players;
        this.winningStrategies = winningStrategies;
        nextPlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
        moves = new ArrayList<>();
        board = new Board(size);
    }

    public static class Builder{
        private int size;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        private Builder(){
            size=0;
            players = new ArrayList<>();
            winningStrategies = new ArrayList<>();
        }

        private void validatePlayersCount() throws InvalidPlayerCountException {
            if(players.size() != size-1) throw new InvalidPlayerCountException();
        }

        private void validateBotCount() throws InvalidPlayerCountException, InvalidBotCountException {
            int count = 0;
            for(Player player : players){
                if(player.getPlayerType().name().equalsIgnoreCase("BOT")) count++;
            }
            if(count>1) throw new InvalidBotCountException();
        }

        private void validatePlayerSymbol() throws DuplicateSymbolException {
            Map<Character,Integer> symbolCount = new HashMap<>();
            for(Player player : players){
                char c = player.getSymbol().getaChar();
                symbolCount.put(c,symbolCount.getOrDefault(c,0)+1);
                if(symbolCount.get(c)>1) throw new DuplicateSymbolException();
            }
        }

        public Game build() throws InvalidPlayerCountException, InvalidBotCountException, DuplicateSymbolException {
            validatePlayersCount();
            validateBotCount();
            validatePlayerSymbol();
            return new Game(size, players, winningStrategies);
        }

        public Builder setSize(int size) {
            this.size = size;
            return(this);
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return(this);
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return(this);
        }
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Player getWinner() {
        return winner;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void printBoard(){
        board.print();
    }

    private boolean validateMove(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();

        if(row>= board.getSize() || col>= board.getSize()) return false;
        if(row<0 || col<0) return false;
        if(board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)) return true;
        return false;
    }

    private boolean checkWinner(Move move){
        for(WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(board,move)) return true;
        }
        return false;
    }

    public void makeMove(){
        Player currentPlayer = players.get(nextPlayerIndex);
        System.out.print("It is "+currentPlayer.getName()+"'s move. ");
        if(currentPlayer.getPlayerType().equals(PlayerType.BOT))
            System.out.println("Making move....");
        else
            System.out.println("Please make your move!!");
        Move move = currentPlayer.makeMove(board);
        if(!validateMove(move)){
            System.out.println("It is an invalid move. Please select a move as per the cell available on the board");
            return;
        }
        int row = move.getCell().getRow();
        int col = move.getCell().getColumn();
        System.out.println(currentPlayer.getName()+" has made a move on "+row+","+col);

        Cell currentCell = board.getBoard().get(row).get(col);
        currentCell.setCellState(CellState.FILLED);
        currentCell.setPlayer(currentPlayer);

        moves.add(new Move(currentCell, currentPlayer));
        nextPlayerIndex+=1;
        nextPlayerIndex%=players.size();

        if(checkWinner(move)){
            this.gameState = GameState.SUCCESS;
            this.winner = currentPlayer;
            printBoard();
            return;
        }else if(moves.size() == (board.getSize() * board.getSize())){
            this.gameState = GameState.DRAWN;
            printBoard();
        }
    }

    public void handleUndo(){
        if(moves.isEmpty()){
            System.out.println("No Move to undo");
            return;
        }

        Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);

        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        for (WinningStrategy winningStrategy: winningStrategies) {
            winningStrategy.handleUndo(board, lastMove);
        }

        nextPlayerIndex -= 1;
        nextPlayerIndex = (nextPlayerIndex + players.size()) % players.size();
    }
}

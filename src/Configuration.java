import models.Piece;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Configuration {

    private int[][] board;
    private List<Piece> pieces;

    public Configuration(int[][] board) {
        this.board = board;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}

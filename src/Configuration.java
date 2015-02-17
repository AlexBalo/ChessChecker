import models.Piece;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This class is in charge of storing the information about a specific configuration
 */
public class Configuration {

    /**
     * The board used for this configuration
     */
    private int[][] board;
    /**
     * A list of pieces that are part of this configuration
     */
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

    /**
     * This method checks if another configuration contains the same pieces
     *
     * @param piecesToAdd the pieces of another configuration
     * @return true if this configuration contains the same pieces that were provided as input
     */
    public boolean isContainingSamePieces(List<Piece> piecesToAdd) {
        int equalsItem = 0;
        for (Piece savedPiece : pieces) {
            int equalsInConfig = 0;
            for (Piece pieceToBeSaved : piecesToAdd) {
                if (savedPiece.equals(pieceToBeSaved)) {
                    equalsInConfig++;
                    equalsItem++;
                }
            }
            if (equalsInConfig == 0) {
                return false;
            }
        }
        return equalsItem == piecesToAdd.size();
    }
}

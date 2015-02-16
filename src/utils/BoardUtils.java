package utils;

import models.Piece;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This is a utility class that provides methods to interact with the board
 */
public class BoardUtils {

    /**
     * This method is used by the pieces to mark a certain position on the board as taken
     *
     * @param rowIndex    the row index of the position
     * @param columnIndex the column index of the position
     * @param boardSpots  the board used to check empty spots
     */
    public static void markSpotAsTaken(int rowIndex, int columnIndex, boolean[][] boardSpots) {
        boardSpots[rowIndex][columnIndex] = true;
    }

    /**
     * This method determines if a certain spot can be taken based on the coordinates and the already placed pieces
     *
     * @param rowIndex     the index of the row
     * @param columnIndex  the index of the column
     * @param placesPieces the list of already placed pieces on the board
     * @return true when the current piece can take the corresponding spot
     */
    public static boolean canPieceTakeSpot(int rowIndex, int columnIndex, List<Piece> placesPieces) {
        for (Piece piece : placesPieces) {
            if (piece.getRow() == rowIndex && piece.getColumn() == columnIndex) {
                return false;
            }
        }
        return true;
    }
}

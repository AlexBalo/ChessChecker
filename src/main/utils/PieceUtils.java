package main.utils;

import main.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alessandro.balocco
 * This is a utility class provides utility methods related to pieces
 */
public class PieceUtils {

    /**
     * Return a piece of the correct type based on the one which was considered
     *
     * @param piece the piece to consider
     * @return a new piece of the same type
     */
    public static Piece getPieceOfType(Piece piece) {
        Piece pieceToReturn = null;
        if (piece instanceof King) {
            pieceToReturn = new King();
        }
        if (piece instanceof Rook) {
            pieceToReturn = new Rook();
        }
        if (piece instanceof Queen) {
            pieceToReturn = new Queen();
        }
        if (piece instanceof Bishop) {
            pieceToReturn = new Bishop();
        }
        if (piece instanceof Knight) {
            pieceToReturn = new Knight();
        }
        return pieceToReturn;
    }

    /**
     * Initialize the pieces depending on Builder inputs
     *
     * @return a list of pieces to place on the board
     */
    public static List<Piece> initializePiecesListFromInputs(int kings, int rooks, int queens, int bishops, int knights) {
        List<Piece> pieces = new ArrayList<Piece>();
        populateListWithPieces(PieceType.KING, kings, pieces);
        populateListWithPieces(PieceType.ROOK, rooks, pieces);
        populateListWithPieces(PieceType.QUEEN, queens, pieces);
        populateListWithPieces(PieceType.BISHOP, bishops, pieces);
        populateListWithPieces(PieceType.KNIGHT, knights, pieces);
        return pieces;
    }

    /**
     * Populate list with the input given by the user
     *
     * @param type     type of piece to insert
     * @param quantity of pieces of same type
     * @param pieces   the list to insert the pieces into
     */
    private static void populateListWithPieces(PieceType type, int quantity, List<Piece> pieces) {
        if (quantity == 0) {
            return;
        }

        for (int i = 0; i < quantity; i++) {
            switch (type) {
                case KING:
                    pieces.add(new King());
                    break;
                case ROOK:
                    pieces.add(new Rook());
                    break;
                case QUEEN:
                    pieces.add(new Queen());
                    break;
                case BISHOP:
                    pieces.add(new Bishop());
                    break;
                case KNIGHT:
                    pieces.add(new Knight());
                    break;
            }
        }
    }
}

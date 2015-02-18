import models.Piece;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This class is in charge of storing the information about a specific configuration
 */
public class Configuration {
    /**
     * A list of pieces that are part of this configuration
     */
    private List<Piece> pieces;


    public Configuration() {
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}

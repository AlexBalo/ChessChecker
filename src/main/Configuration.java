package main;

import main.models.Piece;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This class is in charge of storing the information about a specific configuration
 */
public class Configuration {
    /**
     * The incremental number used to represent this configuration
     */
    private int configurationNumber;
    /**
     * The size of the board used by this configuration
     */
    private int boardSize;
    /**
     * A list of pieces that are part of this configuration
     */
    private List<Piece> pieces;

    public Configuration() {
    }

    public int getConfigurationNumber() {
        return configurationNumber;
    }

    public void setConfigurationNumber(int configurationNumber) {
        this.configurationNumber = configurationNumber;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}

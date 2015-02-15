import models.King;
import models.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Analyser {

    private int[][] board;
    private King[] kings;

    private List<Configuration> configurations;
    private List<Piece> configurationPieces;
    private List<Piece> availablePieces;

    private Analyser(Builder builder) {
        board = builder.board;
        kings = builder.kings;
        checkValidity();
    }

    private void checkValidity() {
        if (kings.length == 0) {
            throw new RuntimeException("At least one piece should be added");
        }
    }

    private List<Piece> getAllPieces() {
        List<Piece> pieces = new ArrayList<Piece>();
        Collections.addAll(pieces, kings);
        return pieces;
    }

    public List<Configuration> calculateConfigurations() {
        configurations = new ArrayList<Configuration>();
        availablePieces = getAllPieces();
        int rowsLength = board.length;
        for (int i = 0; i < rowsLength; i++) {
            int columnsLength = board[i].length;
            for (int j = 0; j < columnsLength; j++) {
                configurationPieces = new ArrayList<Piece>();
                initConfigurationResearch(i, j);
            }
        }

        return configurations;
    }

    private void initConfigurationResearch(int row, int column) {
        Piece piece = availablePieces.get(0);
        piece.setRow(row);
        piece.setColumn(column);

        Piece pieceToAdd = getPieceToAdd(piece);
        configurationPieces.add(pieceToAdd);

        int size = availablePieces.size();
        if (size > 1) {
            int indexToStartSearchingFor = 1;
            loopForSpaces(indexToStartSearchingFor);
        } else {
            createConfiguration();
        }
    }

    private void loopForSpaces(int consideredIndex) {
        int rowsLength = board.length;
        for (int i = 0; i < rowsLength; i++) {
            int columnsLength = board[i].length;
            for (int j = 0; j < columnsLength; j++) {
                Piece piece = availablePieces.get(consideredIndex);
                boolean[][] availableSpots = calculateAvailableSpots();
                boolean spotTaken = availableSpots[i][j];
                boolean canPieceTakeSpot = piece.canPieceTakeSpot(i, j, availableSpots, configurationPieces);
                if (spotTaken || !canPieceTakeSpot) {
                    continue;
                }

                piece.setRow(i);
                piece.setColumn(j);
                Piece pieceToAdd = getPieceToAdd(piece);
                configurationPieces.add(pieceToAdd);

                int configurationSize = configurationPieces.size();
                int availableSize = availablePieces.size();
                if (configurationSize == availableSize) {
                    createConfiguration();
                    configurationPieces.remove(consideredIndex);
                } else {
                    loopForSpaces(consideredIndex + 1);
                }
            }
        }

        if (configurationPieces.size() == (consideredIndex + 1)) {
            configurationPieces.remove(consideredIndex);
        }
    }

    private boolean[][] calculateAvailableSpots() {
        boolean[][] availableSpots = new boolean[board.length][board[0].length];
        for (Piece piece : configurationPieces) {
            int row = piece.getRow();
            int column = piece.getColumn();
            availableSpots[row][column] = true;
            piece.calculateEatableSpots(row, column, availableSpots);
        }
        return availableSpots;
    }

    private Piece getPieceToAdd(Piece piece) {
        Piece pieceToAdd = null;
        // TODO
        if (piece instanceof King) {
            pieceToAdd = new King();
        }

        if (pieceToAdd != null) {
            pieceToAdd.setRow(piece.getRow());
            pieceToAdd.setColumn(piece.getColumn());
        }
        return pieceToAdd;
    }

    private void createConfiguration() {
        if (configurationAlreadyExists()) {
            return;
        }

        Configuration configuration = new Configuration(board);
        List<Piece> configurationPiecesList = new ArrayList<Piece>();
        configurationPiecesList.addAll(configurationPieces);
        configuration.setPieces(configurationPiecesList);
        configurations.add(configuration);
    }

    private boolean configurationAlreadyExists() {
        for (Configuration configuration : configurations) {
            int equalsItem = 0;
            int piecesSize = configuration.getPieces().size();
            for (int j = 0; j < piecesSize; j++) {
                Piece savedPiece = configuration.getPieces().get(j);
                for (Piece pieceToBeSaved : configurationPieces) {
                    if (savedPiece.equals(pieceToBeSaved)) {
                        equalsItem++;
                    }
                }
            }
            if (equalsItem == configurationPieces.size()) {
                return true;
            }
        }
        return false;
    }

    public static class Builder {
        private final int[][] board;
        private King[] kings;

        public Builder(int width, int height) {
            this.board = new int[width][height];
        }

        public Builder withKings(int quantity) {
            this.kings = new King[quantity];
            for (int i = 0; i < kings.length; i++) {
                kings[i] = new King();
            }
            return this;
        }

        public Analyser build() {
            return new Analyser(this);
        }
    }
}

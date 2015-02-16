import models.Piece;
import utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alessandro.balocco
 * This class is in change of analyzing pieces and find all possible configurations with the given input
 */
public class Analyser {

    /* The inputs provided by the user */
    private int[][] board;
    private int kings;
    private int rooks;
    private int bishops;
    private int knights;

    /**
     * The list of available configurations to be returned
     */
    private List<Configuration> configurations;
    /**
     * The list that contains all the pieces related to a specific configuration
     */
    private List<Piece> configurationPieces;
    /**
     * The list of pieces created depending on user inputs
     */
    private List<Piece> availablePieces;

    /**
     * Private constructor created by the Builder
     *
     * @param builder the builder provided by the user
     */
    private Analyser(Builder builder) {
        board = builder.board;
        kings = builder.kings;
        rooks = builder.rooks;
        bishops = builder.bishops;
        knights = builder.knights;
        checkValidInputs();
    }

    /**
     * Check if the input give by the user is valid and if it is not throws and Exception with message
     *
     * @throws java.lang.RuntimeException when no pieces have been inserted
     */
    private void checkValidInputs() {
        // TODO add control
        int totalCount = kings + rooks + bishops + knights;
        if (totalCount == 0) {
            throw new RuntimeException("At least one piece should be added");
        }
    }

    /**
     * This method calculate all the possible configurations it starts from the top left
     * corner and starts searching for configurations
     *
     * @return a list with all the configurations or empty when nothing was found
     */
    public List<Configuration> calculateConfigurations() {
        configurations = new ArrayList<Configuration>();
        availablePieces = PieceUtils.initializePiecesListFromInputs(kings, rooks, bishops, knights);
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

    /**
     * Every time this method is called it means that the first item has changed its position.
     * It also means that we can consider the first item as first item of the configuration.
     * After that it starts looping looking for possible combinations
     *
     * @param row    the index of the row of the first item
     * @param column the index of the column of the first item
     */
    private void initConfigurationResearch(int row, int column) {
        Piece piece = availablePieces.get(0);
        piece.setRow(row);
        piece.setColumn(column);

        Piece pieceToAdd = getPieceToAdd(piece);
        configurationPieces.add(pieceToAdd);

        int size = availablePieces.size();
        if (size > 1) {
            int indexToStartSearchingFor = 1;
            searchAvailableConfigurations(indexToStartSearchingFor);
        } else {
            createConfiguration();
        }
    }

    /**
     * This method continues looping recursively searching for a valid configuration. Every time a valid
     * piece find a valid position it advances to next piece in the list to check for valid position and
     * so on. At the same time it is also controlling the state of the configuration and updating it.
     *
     * @param currentIndex this represent the index to check in the pieces list
     */
    private void searchAvailableConfigurations(int currentIndex) {
        int rowsLength = board.length;
        for (int i = 0; i < rowsLength; i++) {
            int columnsLength = board[i].length;
            for (int j = 0; j < columnsLength; j++) {
                cleanTmpPiecesListIfNecessary(currentIndex);

                Piece piece = availablePieces.get(currentIndex);
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
                    configurationPieces.remove(currentIndex);
                } else {
                    searchAvailableConfigurations(currentIndex + 1);
                }
            }
        }

        cleanTmpPiecesListIfNecessary(currentIndex);
    }

    /**
     * This method is in charge of keeping the list of pieces clean and up to date with the configuration
     *
     * @param currentIndex the index that is been currently analyzed
     */
    private void cleanTmpPiecesListIfNecessary(int currentIndex) {
        if (configurationPieces.size() == (currentIndex + 1)) {
            configurationPieces.remove(currentIndex);
        }
    }

    /**
     * This method calculates the available spots in order to understand where a piece can be placed.
     * It considers both the position of the other pieces and their safe zones
     *
     * @return a map of boolean to indicate wheter the cell is free or not
     */
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

    /**
     * This method create a new piece based on the one considered in the main list.
     *
     * @param piece the placed piece contained in the main list
     * @return the piece to be added in the configuration
     */
    private Piece getPieceToAdd(Piece piece) {
        Piece pieceToAdd = PieceUtils.getPieceOfType(piece);
        if (pieceToAdd != null) {
            pieceToAdd.setRow(piece.getRow());
            pieceToAdd.setColumn(piece.getColumn());
        }
        return pieceToAdd;
    }

    /**
     * Create a valid configuration and add it to the list of all possible configurations.
     * Before adding it also checks if the same configuration is already in the list.
     * In that case no configuration is added
     */
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

    /**
     * This method checks if a configuration which has the pieces in the same position already exists
     *
     * @return true whether the same configuration already exists
     */
    private boolean configurationAlreadyExists() {
        for (Configuration configuration : configurations) {
            boolean alreadyContainConfig = configuration.isContainingSamePieces(configurationPieces);
            if (alreadyContainConfig) {
                return true;
            }
        }
        return false;
    }

    /**
     * Builder class for the Analyzer
     */
    public static class Builder {

        // TODO add method
        private final int[][] board;
        private int kings;
        private int rooks;
        private int knights;
        private int bishops;

        /**
         * Initialize the builder with the size of the board
         *
         * @param width  of the board
         * @param height of the board
         */
        public Builder(int width, int height) {
            throwExceptionWithNegativeSizes(width, height);
            this.board = new int[width][height];
        }

        /**
         * The selected number of kings
         *
         * @param kings the quantity of this type
         * @return the builder
         */
        public Builder withKings(int kings) {
            throwExceptionWithNegativeQuantities(kings);
            this.kings = kings;
            return this;
        }

        /**
         * The selected number of rooks
         *
         * @param rooks the quantity of this type
         * @return the builder
         */
        public Builder withRooks(int rooks) {
            throwExceptionWithNegativeQuantities(rooks);
            this.rooks = rooks;
            return this;
        }

        /**
         * The selected number of bishops
         *
         * @param bishops the quantity of this type
         * @return the builder
         */
        public Builder withBishops(int bishops) {
            throwExceptionWithNegativeQuantities(bishops);
            this.bishops = bishops;
            return this;
        }

        /**
         * The selected number of knights
         *
         * @param knights the quantity of this type
         * @return the builder
         */
        public Builder withKnights(int knights) {
            throwExceptionWithNegativeQuantities(knights);
            this.knights = knights;
            return this;
        }

        /**
         * Builds the Analyzer
         *
         * @return an instance of the Analyzer class
         */
        public Analyser build() {
            return new Analyser(this);
        }

        /**
         * This method checks if user inserted a valid value for the board
         *
         * @param width  the width of the board
         * @param height the height of the board
         */
        private void throwExceptionWithNegativeSizes(int width, int height) {
            if (width < 0 || height < 0) {
                throw new RuntimeException("Board cannot have negative dimension");
            }
        }

        /**
         * This method checks if user inserted a negative quantity
         *
         * @param quantity the quantity of this type
         * @throws java.lang.RuntimeException in case the user insert negative values
         */
        private void throwExceptionWithNegativeQuantities(int quantity) {
            if (quantity < 0) {
                throw new RuntimeException("Cannot insert negative quantities");
            }
        }
    }
}

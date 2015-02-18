import models.Piece;
import utils.PieceUtils;

import java.util.*;

/**
 * Created by alessandro.balocco
 * This class is in change of analyzing pieces and find all possible configurations with the given input
 */
public class Analyser {

    /* The inputs provided by the user */
    private int[][] board;
    private boolean[][] availableSpots;
    private int kings;
    private int rooks;
    private int queens;
    private int bishops;
    private int knights;

    private Map<String, List<Integer>> configurationCodes;

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
        queens = builder.queens;
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
        int totalCount = kings + rooks + queens + bishops + knights;
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
        availablePieces = PieceUtils.initializePiecesListFromInputs(kings, rooks, queens, bishops, knights);
        availableSpots = new boolean[board.length][board[0].length];
        configurationCodes = new HashMap<String, List<Integer>>();
        configurationPieces = new ArrayList<Piece>();
        searchAvailableConfigurations(0);

        int count = 0;
        for (Map.Entry<String, List<Integer>> entry : configurationCodes.entrySet()) {
            count += entry.getValue().size();
        }
        System.out.println("Total configurations " + count);
        return configurations;
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
                // TODO: Remove it, it's just for test purposes
                if (currentIndex == 0) {
                    System.out.println("row: " + i + " - column: " + j);
                }
                cleanTmpPiecesListIfNecessary(currentIndex);

                Piece piece = availablePieces.get(currentIndex);
                boolean spotTaken = availableSpots[i][j];
                if (spotTaken) {
                    continue;
                }

                boolean canPieceTakeSpot = piece.canPieceTakeSpot(i, j, availableSpots, configurationPieces);
                if (!canPieceTakeSpot) {
                    continue;
                }

                piece.setRow(i);
                piece.setColumn(j);
                Piece pieceToAdd = getPieceToAdd(piece);
                addPieceToConfiguration(pieceToAdd);

                int configurationSize = configurationPieces.size();
                int availableSize = availablePieces.size();
                if (configurationSize == availableSize) {
                    createConfiguration();
                    removePieceFromConfiguration(currentIndex);
                } else {
                    searchAvailableConfigurations(currentIndex + 1);
                }
            }
        }

        cleanTmpPiecesListIfNecessary(currentIndex);
    }

    private void addPieceToConfiguration(Piece piece) {
        configurationPieces.add(piece);
        availableSpots = calculateAvailableSpots();
    }

    private void removePieceFromConfiguration(int index) {
        configurationPieces.remove(index);
        availableSpots = calculateAvailableSpots();
    }

    /**
     * This method is in charge of keeping the list of pieces clean and up to date with the configuration
     *
     * @param currentIndex the index that is been currently analyzed
     */
    private void cleanTmpPiecesListIfNecessary(int currentIndex) {
        if (configurationPieces.size() == (currentIndex + 1)) {
            removePieceFromConfiguration(currentIndex);
        }
    }

    /**
     * This method calculates the available spots in order to understand where a piece can be placed.
     * It considers both the position of the other pieces and their safe zones
     *
     * @return a map of boolean to indicate whether the cell is free or not
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
        List<Piece> freshConfiguration = new ArrayList<Piece>(configurationPieces);
        Collections.sort(freshConfiguration, Piece.PositionPieceComparator);
        String configString = generateStringFromConfig(freshConfiguration);
        String key = configString.substring(0, 16);
        List<Integer> listToCheck = configurationCodes.get(key);
        if (listToCheck != null) {
            if (!listToCheck.contains(configString.hashCode())) {
                listToCheck.add(configString.hashCode());
                configurationCodes.put(key, listToCheck);
                //addNewConfiguration(freshConfiguration);
            }
        } else {
            listToCheck = new ArrayList<Integer>();
            listToCheck.add(configString.hashCode());
            configurationCodes.put(key, listToCheck);
            //addNewConfiguration(freshConfiguration);
        }
    }

    private void addNewConfiguration(List<Piece> newConfiguration) {
        Configuration configuration = new Configuration();
        configuration.setPieces(newConfiguration);
        configurations.add(configuration);
    }

    private String generateStringFromConfig(List<Piece> configurationPiecesList) {
        String string = "";
        for (Piece piece : configurationPiecesList) {
            string += piece.getIdentifier() + piece.getRow() + piece.getColumn();
        }
        return string;
    }

    /**
     * Builder class for the Analyzer
     */
    public static class Builder {

        private final int[][] board;
        private int kings;
        private int rooks;
        private int queens;
        private int bishops;
        private int knights;

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
         * The selected number of queens
         *
         * @param queens the quantity of this type
         * @return the builder
         */
        public Builder withQueens(int queens) {
            throwExceptionWithNegativeQuantities(queens);
            this.queens = queens;
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

package main;

import main.models.Piece;
import main.utils.PieceUtils;

import java.util.*;

/**
 * Created by alessandro.balocco
 * This class is in change of analyzing pieces and find all possible configurations with the given input
 */
public class Analyser {

    /**
     * This constant is used to identify the string length of a piece (ex: Q 23)
     */
    private static final int PIECE_STRING_LENGTH = 5;

    /* The inputs provided by the user */
    private final int[][] board;
    private final int kings;
    private final int rooks;
    private final int queens;
    private final int bishops;
    private final int knights;
    private final boolean printConfigurations;

    /**
     * An instance of the printer and a configuration counter.
     * When user decide to have graphical representations
     */
    private Printer printer;
    private int configurationCounter;
    private int boardSize;
    /**
     * This variable identifies the max length for map keys depending on user input
     */
    private int limitForMapKey;
    /**
     * The matrix of available spots that gets updated. It is useful to search for configurations
     */
    private boolean[][] availableSpots;
    /**
     * The map of configurations. Keys are defined based on user inputs
     */
    private Map<String, List<Integer>> configurationsMap;
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
        printConfigurations = builder.printConfigurations;
        checkValidInputs();
        initializePrinterIfNeeded();
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
     * This method initialize the printer when needed based on user input
     */
    private void initializePrinterIfNeeded() {
        if (!printConfigurations) {
            return;
        }
        printer = new Printer();
        boardSize = board.length;
    }

    /**
     * This method calculate all the possible configurations. It starts from the top left
     * corner keeps going looping, looking for unique configurations.
     *
     * @return the number of unique configurations
     */
    public int getNumberOfConfigurations() {
        configurationCounter = 0;
        availablePieces = PieceUtils.initializePiecesListFromInputs(kings, rooks, queens, bishops, knights);
        limitForMapKey = getKeySequenceMaxLimit();
        availableSpots = new boolean[board.length][board[0].length];
        configurationsMap = new HashMap<String, List<Integer>>();
        configurationPieces = new ArrayList<Piece>();
        searchAvailableConfigurations(0);

        return getNumberOfStoredConfigurations();
    }

    /**
     * Calculate the result of the calculation by making a sum of the sizes of the stored lists
     *
     * @return the number of configurations
     */
    private int getNumberOfStoredConfigurations() {
        int count = 0;
        for (Map.Entry<String, List<Integer>> entry : configurationsMap.entrySet()) {
            count += entry.getValue().size();
        }
        return count;
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

    /**
     * This method adds a piece to a temporary configuration. After adding it it
     * also recalculate the available spots based on the new list of pieces
     *
     * @param piece the piece to add
     */
    private void addPieceToConfiguration(Piece piece) {
        configurationPieces.add(piece);
        availableSpots = calculateAvailableSpots();
    }

    /**
     * This method removes last piece from a temporary configuration. When is not possible
     * to complete the all configuration last item is removed and with the removal it is also
     * recalculated the matrix of available spots.
     *
     * @param index the index to be removed.
     */
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
        String configurationString = generateStringFromConfig(freshConfiguration);

        String key = configurationString.substring(0, limitForMapKey);
        List<Integer> value = configurationsMap.get(key);
        if (value == null) {
            value = new ArrayList<Integer>();
            addNewConfigurationAndPrint(key, value, configurationString, freshConfiguration);
            return;
        }

        if (value.contains(configurationString.hashCode())) {
            return;
        }
        addNewConfigurationAndPrint(key, value, configurationString, freshConfiguration);
    }

    /**
     * This method add a new configuration to the map
     *
     * @param key                 the key to add
     * @param value               the list of hashes
     * @param configurationString the string representing the sequence of pieces with coordinates
     * @param freshConfiguration  the list of pieces
     */
    private void addNewConfigurationAndPrint(String key, List<Integer> value, String configurationString,
                                             List<Piece> freshConfiguration) {
        value.add(configurationString.hashCode());
        configurationsMap.put(key, value);
        printConfigurationIfNeeded(freshConfiguration);
    }

    /**
     * This method determines the length of the keys in the map of possible configurations
     *
     * @return the max length of the keys
     */
    private int getKeySequenceMaxLimit() {
        int numberOfPieces = availablePieces.size();
        return numberOfPieces == 1 ? PIECE_STRING_LENGTH : ((numberOfPieces / 2) * PIECE_STRING_LENGTH);
    }

    /**
     * This method is in charge of printing the graphical representation of the configuration with an
     * incremental number
     *
     * @param configurationPieces the pieces that are composing the configuration
     */
    private void printConfigurationIfNeeded(List<Piece> configurationPieces) {
        if (!printConfigurations) {
            return;
        }
        configurationCounter++;
        Configuration configuration = new Configuration();
        configuration.setConfigurationNumber(configurationCounter);
        configuration.setBoardSize(boardSize);
        configuration.setPieces(configurationPieces);
        printer.printConfiguration(configuration);
    }

    /**
     * This method generate a string representing a specific configuration. It is used to compare
     * different configurations because algorithms only looks for unique configurations and compares them.
     *
     * @param configurationPiecesList the list of pieces the configuration is composed by
     * @return a string represented by a sequence of piece id and coordinates (ex: K 12 Q 13 N 24...)
     */
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
        private boolean printConfigurations;

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
         * Decide whether or not providing visual representation of the configurations
         *
         * @param printConfigurations if true print graphical representation of every configuration
         * @return the builder
         */
        public Builder printConfigurations(boolean printConfigurations) {
            this.printConfigurations = printConfigurations;
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

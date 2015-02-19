import models.Piece;

import java.util.List;

/**
 * Created by alessandro.balocco
 * This class is a utility class in charge of printing inputs and outputs
 */
public class Printer {

    public Printer() {
    }

    /**
     * Print the input of the test
     */
    public void printTime(long startTime, long endTime) {
        long gap = endTime - startTime;

        int hours = (int) ((gap / (1000 * 60 * 60)) % 24);
        int minutes = (int) ((gap / (1000 * 60)) % 60);
        int seconds = (int) (gap / 1000) % 60;

        StringBuilder timeString = new StringBuilder("Calculation time: ");
        if (hours > 0) {
            String hourString = hours > 1 ? hours + " Hours " : hours + " Hour ";
            timeString.append(hourString);
        }
        if (minutes > 0) {
            String minuteString = minutes > 1 ? minutes + " Minutes " : minutes + " Minute ";
            timeString.append(minuteString);
        }
        if (seconds > 0) {
            String secondString = seconds > 1 ? seconds + " Seconds " : seconds + " Second ";
            timeString.append(secondString);
        }

        if (hours == 0 && minutes == 0 && seconds == 0) {
            timeString = new StringBuilder("Running time: ");
            timeString.append("Less than a second.");
        }

        System.out.println(timeString.toString());
    }

    /**
     * Print the input of the test
     *
     * @param boardSize the dimension of the board
     * @param kings     the quantity of kings
     * @param rooks     the quantity of rooks
     * @param queens    the quantity of queens
     * @param bishops   the quantity of bishops
     * @param knights   the quantity of knights
     */
    public void printInput(int boardSize, int kings, int rooks, int queens, int bishops, int knights) {
        System.out.println("");
        System.out.println("SUMMARY:");
        StringBuilder builder = new StringBuilder();
        String firstPart = "Input: Board size: " + boardSize + "x" + boardSize + " - Content: ";
        builder.append(firstPart);
        if (kings > 0) {
            String kingNumber = kings > 1 ? kings + " Kings " : kings + " King ";
            builder.append(kingNumber);
        }
        if (rooks > 0) {
            String rooksNumber = rooks > 1 ? rooks + " Rooks " : rooks + " Rook ";
            builder.append(rooksNumber);
        }
        if (queens > 0) {
            String queensNumber = queens > 1 ? queens + " Queens " : queens + "Queen ";
            builder.append(queensNumber);
        }
        if (bishops > 0) {
            String bishopsNumber = bishops > 1 ? bishops + " Bishops " : bishops + "Bishop ";
            builder.append(bishopsNumber);
        }
        if (knights > 0) {
            String knightsNumber = knights > 1 ? knights + " Knights " : knights + " Knight ";
            builder.append(knightsNumber);
        }
        System.out.println(builder.toString());
    }

    /**
     * This method prints the output of the test
     *
     * @param numberOfConfigurations the number of configurations
     */
    public void printOutput(int numberOfConfigurations) {
        if (numberOfConfigurations == 0) {
            System.out.println("Output: Unfortunately no configurations were found for the input you entered");
            return;
        }

        System.out.println("Output: Total number of configurations: " + numberOfConfigurations);
    }

    /**
     * This method populate a board with the pieces of a single configuration
     * @param configuration the configuration to use to populate the board
     */
    public void printConfiguration(Configuration configuration) {
        System.out.println("# " + configuration.getConfigurationNumber());
        List<Piece> placedPieces = configuration.getPieces();
        int boardSize = configuration.getBoardSize();
        Piece[][] piecesBoard = new Piece[boardSize][boardSize];
        for (Piece piece : placedPieces) {
            piecesBoard[piece.getRow()][piece.getColumn()] = piece;
        }
        printBoard(piecesBoard);
        System.out.println("----------------------");
    }

    /**
     * Print the board indicating pieces initials
     *
     * @param piecesBoard the board to be printed
     */
    private void printBoard(Piece[][] piecesBoard) {
        int rowSize = piecesBoard.length + 1;
        int columnSize = piecesBoard[0].length + 1;

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                if (i == (rowSize - 1) && j == 0) {
                    System.out.print("   ");
                    continue;
                }

                if (i == (rowSize - 1)) {
                    System.out.print(" " + j + " ");
                    continue;
                }

                if (j == 0) {
                    System.out.print(" " + String.valueOf((rowSize - 1) - i) + " ");
                    continue;
                }

                Piece piece = piecesBoard[i][j - 1];
                String value = piece == null ? " - " : piece.getIdentifier();
                System.out.print(value);
            }
            System.out.println("");
        }
    }
}

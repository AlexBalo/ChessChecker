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
            timeString = new StringBuilder("Calculation time: ");
            timeString.append("less than a second.");
        }

        System.out.println("");
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
        System.out.println("INPUT:");
        StringBuilder builder = new StringBuilder();
        String firstPart = "Board with dimension: " + boardSize + "x" + boardSize + " containing ";
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
     * @param configurations the configurations that need to be printed
     */
    public void printOutput(List<Configuration> configurations) {
        int counter = 0;
        int configurationsSize = configurations.size();

        System.out.println("");
        System.out.println("OUTPUT:");
        if (configurationsSize == 0) {
            System.out.println("Unfortunately no configurations were found for the input you entered");
            return;
        }

        System.out.println("Total number of configurations: " + configurationsSize);
        /*
        for (Configuration configuration : configurations) {
            counter++;
            System.out.println("# " + counter);
            populateBoard(configuration);
            System.out.println("----------------------");
        }*/
    }

    /**
     * This method populate a board with the pieces of a single configuration
     *
     * @param configuration the configuration to use to populate the board
     */
    private void populateBoard(Configuration configuration) {
        int[][] board = configuration.getBoard();
        List<Piece> placedPieces = configuration.getPieces();
        Piece[][] piecesBoard = new Piece[board.length][board[0].length];
        for (Piece piece : placedPieces) {
            piecesBoard[piece.getRow()][piece.getColumn()] = piece;
        }
        printBoard(piecesBoard);
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

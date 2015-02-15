import models.Piece;

import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Printer {

    public Printer() {
    }

    public void printInput(int boardSize, int kings, int rooks, int queens, int bishops, int knights) {
        System.out.println("INPUT:");
        StringBuilder builder = new StringBuilder();
        String firstPart = "Board with dimension: " + boardSize + "x" + boardSize + " containing ";
        builder.append(firstPart);
        if (kings > 0) {
            String kingNumber = kings > 1 ? kings + " Kings " : kings + "King ";
            builder.append(kingNumber);
        }
        if (rooks > 0) {
            String rooksNumber = rooks > 1 ? rooks + " Rooks " : rooks + "Rook ";
            builder.append(rooksNumber);
        }
        if (queens > 0) {
            String queensNumber = queens > 1 ? queens + " Queens " : queens + "Queen ";
            builder.append(queensNumber);
        }
        if (bishops > 0) {
            String bishopsNumber = bishops > 1 ? bishops + " Bishops" : bishops + "Bishop ";
            builder.append(bishopsNumber);
        }
        if (knights > 0) {
            String knightsNumber = knights > 1 ? knights + " Knights" : knights + "Knight";
            builder.append(knightsNumber);
        }
        System.out.println(builder.toString());
    }

    public void printOutput(List<Configuration> configurations) {
        int counter = 0;
        System.out.println("");
        System.out.println("OUTPUT:");
        System.out.println("Total number of configuration: " + configurations.size());
        for (Configuration configuration : configurations) {
            counter++;
            System.out.println("# " + counter);
            populateBoard(configuration);
            System.out.println("----------------------");
        }
    }

    private void populateBoard(Configuration configuration) {
        int[][] board = configuration.getBoard();
        List<Piece> placedPieces = configuration.getPieces();
        Piece[][] piecesBoard = new Piece[board.length][board[0].length];
        for (Piece piece : placedPieces) {
            piecesBoard[piece.getRow()][piece.getColumn()] = piece;
        }
        printBoard(piecesBoard);
    }

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
                String value = piece == null ? " - " : piece.getInitial();
                System.out.print(value);
            }
            System.out.println("");
        }
    }
}

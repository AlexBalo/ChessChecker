import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Main {

    private static final int BOARD_SIZE = 3;
    private static final int KINGS = 2;
    private static final int ROOKS = 1;
    private static final int QUEENS = 0;
    private static final int BISHOPS = 0;
    private static final int KNIGHTS = 0;

    public static void main(String[] args) {
        Printer printer = new Printer();
        Analyser analyser = buildAnalyzer(BOARD_SIZE, KINGS, ROOKS, QUEENS, BISHOPS, KNIGHTS);
        List<Configuration> configurations = analyser.calculateConfigurations();
        printer.printInput(BOARD_SIZE, KINGS, ROOKS, QUEENS, BISHOPS, KNIGHTS);
        printer.printOutput(configurations);
    }

    private static Analyser buildAnalyzer(int boardSize, int numberOfKings, int numberOfRooks, int numberOfQueens,
                                          int numberOfBishops, int numberOfKnights) {
        return new Analyser.Builder(boardSize, boardSize)
                .withKings(numberOfKings)
                .withRooks(numberOfRooks)
                .withKnights(numberOfKnights)
                .build();
    }
}

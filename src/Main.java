import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Main {

    private static final int BOARD_SIZE = 7;
    private static final int KINGS = 2;
    private static final int ROOKS = 0;
    private static final int QUEENS = 2;
    private static final int BISHOPS = 2;
    private static final int KNIGHTS = 1;

    public static void main(String[] args) {
        Printer printer = new Printer();
        long startTime = System.currentTimeMillis();
        Analyser analyzer = new Analyser.Builder(BOARD_SIZE, BOARD_SIZE)
                .withKings(KINGS)
                .withRooks(ROOKS)
                .withQueens(QUEENS)
                .withBishops(BISHOPS)
                .withKnights(KNIGHTS)
                .build();

        List<Configuration> configurations = analyzer.calculateConfigurations();
        long endTime = System.currentTimeMillis();

        printer.printInput(BOARD_SIZE, KINGS, ROOKS, QUEENS, BISHOPS, KNIGHTS);
        printer.printTime(startTime, endTime);
        printer.printOutput(BOARD_SIZE, configurations);
    }
}

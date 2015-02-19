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
        long startTime = System.currentTimeMillis();
        Analyser analyser = new Analyser.Builder(BOARD_SIZE, BOARD_SIZE)
                .withKings(KINGS)
                .withRooks(ROOKS)
                .withQueens(QUEENS)
                .withBishops(BISHOPS)
                .withKnights(KNIGHTS)
                .printConfigurations(false)
                .build();
        int configurations = analyser.getNumberOfConfigurations();
        long endTime = System.currentTimeMillis();

        Printer printer = new Printer();
        printer.printInput(BOARD_SIZE, KINGS, ROOKS, QUEENS, BISHOPS, KNIGHTS);
        printer.printOutput(configurations);
        printer.printTime(startTime, endTime);
    }
}

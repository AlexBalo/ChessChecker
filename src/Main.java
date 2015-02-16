import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Main {

    private static final int BOARD_SIZE = 3;
    private static final int KINGS = 0;
    private static final int ROOKS = 0;
    private static final int QUEENS = 2;
    private static final int BISHOPS = 0;
    private static final int KNIGHTS = 0;

    public static void main(String[] args) {
        Printer printer = new Printer();
        Analyser analyser = new Analyser.Builder(BOARD_SIZE, BOARD_SIZE)
                .withKings(KINGS)
                .withRooks(ROOKS)
                .withQueens(QUEENS)
                .withBishops(BISHOPS)
                .withKnights(KNIGHTS)
                .build();

        List<Configuration> configurations = analyser.calculateConfigurations();
        printer.printInput(BOARD_SIZE, KINGS, ROOKS, QUEENS, BISHOPS, KNIGHTS);
        printer.printOutput(configurations);
    }
}

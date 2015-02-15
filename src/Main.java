import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Main {

    public static void main(String[] args) {
        Printer printer = new Printer();

        int boardSize = 3;
        int numberOfKings = 2;
        int numberOfRooks = 1;
        Analyser analyser = buildAnalyzer(
                boardSize,
                numberOfKings,
                numberOfRooks
        );

        List<Configuration> configurations = analyser.calculateConfigurations();
        printer.printInput(boardSize, numberOfKings, numberOfRooks, 0, 0, 0);
        printer.printOutput(configurations);
    }

    private static Analyser buildAnalyzer(int boardSize, int numberOfKings, int numberOfRooks) {
        return new Analyser.Builder(boardSize, boardSize)
                .withKings(numberOfKings)
                .withRooks(numberOfRooks)
                .build();
    }
}

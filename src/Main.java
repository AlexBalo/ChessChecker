import java.util.List;

/**
 * Created by alessandro.balocco
 */
public class Main {

    public static void main(String[] args) {
        Printer printer = new Printer();

        int boardSize = 3;
        int numberOfKings = 2;
        Analyser analyser = buildAnalyzer(boardSize, numberOfKings);
        List<Configuration> configurations = analyser.calculateConfigurations();
        printer.printInput(boardSize, numberOfKings, 0, 0, 0, 0);
        printer.printOutput(configurations);
    }

    private static Analyser buildAnalyzer(int boardSize, int numberOfKings) {
        return new Analyser.Builder(boardSize, boardSize)
                .withKings(numberOfKings)
                .build();
    }
}

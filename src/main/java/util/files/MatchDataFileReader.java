package util.files;

import domain.constants.MatchOutcome;
import domain.objects.Match;
import dto.MatchData;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MatchDataFileReader {

    public static List<Match> createMatchesFromFileInPath(Path path) {
        List<Match> matches = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                Match match = createMatchFromLine(line);
                matches.add(match);
            }
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return matches;
    }

    private static Match createMatchFromLine(String line) {
        String[] matchElements = line.split(",");
        UUID matchId = UUID.fromString(matchElements[0]);
        double aBetRate = Double.parseDouble(matchElements[1]);
        double bBetRate = Double.parseDouble(matchElements[2]);
        MatchOutcome matchOutcome = parseMatchOutcomeFromString(matchElements[3]);
        return new Match(matchId, new MatchData(aBetRate, bBetRate, matchOutcome));
    }

    private static MatchOutcome parseMatchOutcomeFromString(String matchOutcomeAsString) {
        return switch (matchOutcomeAsString) {
            case "A" -> MatchOutcome.A;
            case "B" -> MatchOutcome.B;
            case "DRAW" -> MatchOutcome.DRAW;
            default -> throw new IllegalStateException("Unexpected match outcome: " + matchOutcomeAsString);
        };
    }
}

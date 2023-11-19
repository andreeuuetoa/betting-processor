package util.inputdata;

import domain.constants.BettingSide;
import domain.constants.PlayerActionType;
import dto.PlayerAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerActionDataFileReader {
    public List<PlayerAction> createPlayerActionsFromFileInPath(Path path) {
        List<PlayerAction> playerActions = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                PlayerAction playerAction = createPlayerActionFromLine(line);
                playerActions.add(playerAction);
            }
        } catch (IOException e) {
            System.out.println("Error reading file:" + e.getMessage());
        }
        return playerActions;
    }

    private PlayerAction createPlayerActionFromLine(String line) {
        String[] playerActionElements = line.split(",");
        UUID playerId = UUID.fromString(playerActionElements[0]);
        PlayerActionType playerActionType = parsePlayerActionTypeFromString(playerActionElements[1]);
        UUID matchId = playerActionElements[2].isEmpty() ? null : UUID.fromString(playerActionElements[2]);
        int amount = Integer.parseInt(playerActionElements[3]);
        BettingSide bettingSide = playerActionElements.length == 5 ? parseBettingSideFromString(playerActionElements[4]) : null;
        return new PlayerAction(playerId, playerActionType, matchId, amount, bettingSide);
    }

    private PlayerActionType parsePlayerActionTypeFromString(String playerActionTypeAsString) {
        return switch (playerActionTypeAsString) {
            case "DEPOSIT" -> PlayerActionType.DEPOSIT;
            case "WITHDRAW" -> PlayerActionType.WITHDRAW;
            case "BET" -> PlayerActionType.BET;
            default -> throw new IllegalStateException("Unexpected match outcome: " + playerActionTypeAsString);
        };
    }

    private BettingSide parseBettingSideFromString(String bettingSideAsString) {
        return switch (bettingSideAsString) {
            case "A" -> BettingSide.A;
            case "B" -> BettingSide.B;
            default -> throw new IllegalStateException("Unexpected match outcome: " + bettingSideAsString);
        };
    }

	public List<String> getPlayerActionsAsStringsFromFileInPath(Path path) {
		List<String> playerActions = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				playerActions.add(line);
			}
		} catch (IOException e) {
			System.out.println("Error reading file:" + e.getMessage());
		}
		return playerActions;
	}
}
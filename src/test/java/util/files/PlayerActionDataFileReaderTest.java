package util.files;

import domain.constants.BettingSide;
import domain.constants.PlayerActionType;
import dto.PlayerAction;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerActionDataFileReaderTest {
    @Test
    public void testDepositPlayerActionDataFileExistsAndContainsData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_deposit_data.txt");
        String oneMatchData = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,DEPOSIT,,4000,";
        assertDoesNotThrow(() -> {Files.newBufferedReader(path);});
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            assertEquals(oneMatchData, line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWithdrawPlayerActionDataFileExistsAndContainsData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_withdraw_data.txt");
        String oneMatchData = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,200,";
        assertDoesNotThrow(() -> {Files.newBufferedReader(path);});
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            assertEquals(oneMatchData, line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBetPlayerActionDataFileExistsAndContainsData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_bet_data.txt");
        String oneMatchData = "163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,abae2255-4255-4304-8589-737cdff61640,500,A";
        assertDoesNotThrow(() -> {Files.newBufferedReader(path);});
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            assertEquals(oneMatchData, line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateDepositPlayerActionFromPlayerData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_deposit_data.txt");
        List<PlayerAction> playerActions = PlayerActionDataFileReader.createPlayerActionsFromFileInPath(path);
        assertEquals(1, playerActions.size());
        PlayerAction playerAction = playerActions.get(0);
        assertDepositPlayerActionWasCreatedCorrectly(playerAction);
    }

    private void assertDepositPlayerActionWasCreatedCorrectly(PlayerAction playerAction) {
        assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4", playerAction.getPlayerId().toString());
        assertEquals(PlayerActionType.DEPOSIT, playerAction.getType());
        assertNull(playerAction.getMatchId());
        assertEquals(4000, playerAction.getAmount());
        assertNull(playerAction.getBettingSide());
    }

    @Test
    public void testCreateWithdrawPlayerActionFromPlayerData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_withdraw_data.txt");
        List<PlayerAction> playerActions = PlayerActionDataFileReader.createPlayerActionsFromFileInPath(path);
        assertEquals(1, playerActions.size());
        PlayerAction playerAction = playerActions.get(0);
        assertWithdrawPlayerActionWasCreatedCorrectly(playerAction);
    }

    private void assertWithdrawPlayerActionWasCreatedCorrectly(PlayerAction playerAction) {
        assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4", playerAction.getPlayerId().toString());
        assertEquals(PlayerActionType.WITHDRAW, playerAction.getType());
        assertNull(playerAction.getMatchId());
        assertEquals(200, playerAction.getAmount());
        assertNull(playerAction.getBettingSide());
    }

    @Test
    public void testCreateBetPlayerActionFromPlayerData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "one_player_bet_data.txt");
        List<PlayerAction> playerActions = PlayerActionDataFileReader.createPlayerActionsFromFileInPath(path);
        assertEquals(1, playerActions.size());
        PlayerAction playerAction = playerActions.get(0);
        assertBetPlayerActionWasCreatedCorrectly(playerAction);
    }

    private void assertBetPlayerActionWasCreatedCorrectly(PlayerAction playerAction) {
        assertEquals("163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4", playerAction.getPlayerId().toString());
        assertEquals(PlayerActionType.BET, playerAction.getType());
        assertEquals("abae2255-4255-4304-8589-737cdff61640", playerAction.getMatchId().toString());
        assertEquals(500, playerAction.getAmount());
        assertEquals(BettingSide.A, playerAction.getBettingSide());
    }

    @Test
    public void testCreatePlayersFromGivenPlayerActionData() {
        Path path = Paths.get("src", "test", "resources", "playerdata", "player_data.txt");
        List<PlayerAction> playerActions = PlayerActionDataFileReader.createPlayerActionsFromFileInPath(path);
        assertEquals(19, playerActions.size());
    }
}

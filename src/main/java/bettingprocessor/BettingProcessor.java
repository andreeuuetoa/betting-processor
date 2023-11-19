package bettingprocessor;

import domain.objects.Casino;
import domain.objects.Match;
import domain.objects.Player;
import dto.MatchInfo;
import dto.PlayerAction;
import dto.ResultLegitimatePlayer;
import mapper.MatchMapper;
import mapper.ResultLegitimatePlayerMapper;
import util.inputdata.MatchDataFileExtractor;
import util.inputdata.PlayerActionDataFileExtractor;
import util.outputdata.ResultFileWriter;
import util.outputdata.ResultGenerator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BettingProcessor {
	private final Path matchDataPath;
	private final Path playerDataPath;
	private final Path resultPath;
	private final List<PlayerAction> illegalPlayerActions;
	private final Casino casino;

	public BettingProcessor(Path matchDataPath, Path playerDataPath, Path resultPath) {
		this.matchDataPath = matchDataPath;
		this.playerDataPath = playerDataPath;
		this.resultPath = resultPath;
		illegalPlayerActions = new ArrayList<>();
		casino = new Casino();
	}

	public void processBettingData() {
		analyzeInputAndSaveDataToCasino();

		casino.playMatches();

		saveOutputDataToFile();
	}

	private void analyzeInputAndSaveDataToCasino() {
		List<MatchInfo> matchesFromFile = new MatchDataFileExtractor().extractMatchInfoFromFileInPath(matchDataPath);
		List<Match> matchesWithCasino = new MatchMapper().getMatchesWithCasino(matchesFromFile, casino);
		List<PlayerAction> playerActionsFromFile = new PlayerActionDataFileExtractor().extractPlayerActionInfoFromFileInPath(playerDataPath);
		addMatchesToCasino(matchesWithCasino);
		for (PlayerAction playerAction : playerActionsFromFile) {
			analyzePlayerAction(playerAction);
		}
	}

	private void addMatchesToCasino(List<Match> matchesWithCasino) {
		for (Match match : matchesWithCasino) {
			casino.addMatch(match);
		}
	}

	private void analyzePlayerAction(PlayerAction playerAction) {
		UUID playerId = playerAction.getPlayerId();
		Player player = casino.getPlayerById(playerId);
		if (player == null) {
			player = createNewPlayerWithId(playerId);
		}
		Match match = getMatchByIdFromCasinoOrCreateANewOne(playerAction);
		try {
			player.act(playerAction.getType(), playerAction.getAmount(), match, playerAction.getBettingSide());
		} catch (Exception e) {
			if (hasPlayerWithIdNotMadeAnIllegalMoveBefore(playerId)) {
				illegalPlayerActions.add(playerAction);
			}
			if (!casino.getIllegitimatePlayers().contains(player)) {
				casino.addIllegitimatePlayer(player);
			}
		}
	}

	private Match getMatchByIdFromCasinoOrCreateANewOne(PlayerAction playerAction) {
		UUID matchId = playerAction.getMatchId();
		return matchId == null ? null : casino.getMatchById(matchId);
	}

	private Player createNewPlayerWithId(UUID playerId) {
		Player newPlayer = new Player(playerId, casino);
		casino.addPlayer(newPlayer);
		return newPlayer;
	}

	private boolean hasPlayerWithIdNotMadeAnIllegalMoveBefore(UUID playerId) {
		return illegalPlayerActions.stream().noneMatch(x -> x.getPlayerId().equals(playerId));
	}

	private void saveOutputDataToFile() {
		String generatedOutput = generateOutputData();
		new ResultFileWriter(resultPath).writeResultToFile(generatedOutput);
	}

	private String generateOutputData() {
		List<Player> legitimatePlayers = casino.getLegitimatePlayers();
		List<ResultLegitimatePlayer> resultLegitimatePlayers = new ResultLegitimatePlayerMapper().getResultLegitimatePlayers(legitimatePlayers);
		List<PlayerAction> illegitimatePlayersFirstActions = casino.getIllegitimatePlayersFirstActions();
		long casinoFinalBalance = casino.getBalance();
		return new ResultGenerator().generateCompleteOutputResult(resultLegitimatePlayers, illegitimatePlayersFirstActions, casinoFinalBalance);
	}
}

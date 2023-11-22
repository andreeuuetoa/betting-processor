package bettingprocessor;

import domain.objects.Casino;
import domain.objects.Match;
import domain.objects.Player;
import dto.MatchInfo;
import dto.PlayerAction;
import dto.ResultLegitimatePlayer;
import mapper.MatchMapper;
import mapper.ResultLegitimatePlayerMapper;
import util.inputdata.MatchDataExtractor;
import util.inputdata.MatchDataFileExtractor;
import util.inputdata.PlayerActionDataExtractor;
import util.inputdata.PlayerActionDataFileExtractor;
import util.outputdata.ResultFileWriter;
import util.outputdata.ResultGenerator;
import util.outputdata.ResultWriter;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class BettingProcessor {
	private final Casino casino;
	private final MatchDataExtractor matchDataExtractor;
	private final PlayerActionDataExtractor playerActionDataExtractor;
	private final ResultWriter resultWriter;

	public BettingProcessor(MatchDataExtractor matchDataExtractor,
							PlayerActionDataExtractor playerActionDataExtractor,
							ResultWriter resultWriter) {
		this.matchDataExtractor = matchDataExtractor;
		this.playerActionDataExtractor = playerActionDataExtractor;
		this.resultWriter = resultWriter;
		casino = new Casino();
	}

	public void processBettingData() {
		analyzeInputAndSaveDataToCasino();

		casino.playMatches();

		saveOutputDataToFile();
	}

	private void analyzeInputAndSaveDataToCasino() {
		List<MatchInfo> matchesFromFile = matchDataExtractor.getMatchInfo();
		List<Match> matchesWithCasino = new MatchMapper().getMatchesWithCasino(matchesFromFile, casino);
		List<PlayerAction> playerActionsFromFile = playerActionDataExtractor.getPlayerActions();
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
		Match match = getMatchByIdFromCasinoOrIsNotAMatch(playerAction.getMatchId());
		player.act(playerAction.getType(), playerAction.getAmount(), match, playerAction.getBettingSide());
	}

	private Match getMatchByIdFromCasinoOrIsNotAMatch(UUID matchId) {
		return matchId == null ? null : casino.getMatchById(matchId);
	}

	private Player createNewPlayerWithId(UUID playerId) {
		Player newPlayer = new Player(playerId, casino);
		casino.addPlayer(newPlayer);
		return newPlayer;
	}

	private void saveOutputDataToFile() {
		String generatedOutput = generateOutputData();
		resultWriter.writeResult(generatedOutput);
	}

	private String generateOutputData() {
		List<Player> legitimatePlayers = casino.getLegitimatePlayers();
		List<ResultLegitimatePlayer> resultLegitimatePlayers = new ResultLegitimatePlayerMapper().getResultLegitimatePlayers(legitimatePlayers);
		List<PlayerAction> illegitimatePlayersFirstActions = casino.getIllegitimatePlayersFirstActions();
		long casinoFinalBalance = casino.getBalance();
		return new ResultGenerator().generateCompleteOutputResult(resultLegitimatePlayers, illegitimatePlayersFirstActions, casinoFinalBalance);
	}
}

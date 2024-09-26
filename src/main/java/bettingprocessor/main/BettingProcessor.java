package bettingprocessor.main;

import bettingprocessor.domain.objects.Casino;
import bettingprocessor.domain.objects.Match;
import bettingprocessor.domain.objects.Player;
import bettingprocessor.dto.MatchInfo;
import bettingprocessor.dto.PlayerAction;
import bettingprocessor.dto.ResultLegitimatePlayer;
import bettingprocessor.mapper.MatchMapper;
import bettingprocessor.mapper.ResultLegitimatePlayerMapper;
import bettingprocessor.util.inputdata.MatchDataExtractor;
import bettingprocessor.util.inputdata.PlayerActionDataExtractor;
import bettingprocessor.util.outputdata.ResultGenerator;
import bettingprocessor.util.outputdata.ResultWriter;

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
		switch (playerAction.getType()) {
			case DEPOSIT -> player.deposit(playerAction.getAmount());
			case WITHDRAW -> player.withdraw(playerAction.getAmount());
			case BET -> player.betOnMatch(playerAction.getAmount(), match, playerAction.getBettingSide());
		}
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

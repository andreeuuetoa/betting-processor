package bettingprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MatchInfo {
	private UUID id;
	private MatchData matchData;
}

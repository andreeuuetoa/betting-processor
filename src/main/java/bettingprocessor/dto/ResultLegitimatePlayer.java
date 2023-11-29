package bettingprocessor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ResultLegitimatePlayer {
	private UUID playerId;
	private int finalBalance;
	private double bettingWinRate;
}

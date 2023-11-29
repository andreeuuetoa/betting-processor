package bettingprocessor.util.inputdata;

import bettingprocessor.dto.MatchInfo;

import java.util.List;

public interface MatchDataExtractor {
    List<MatchInfo> getMatchInfo();
}

package domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Casino {
    private long balance;
    private List<Match> matches;

    public Casino() {
        balance = 0;
        matches = new ArrayList<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

}

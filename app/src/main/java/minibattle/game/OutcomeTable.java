package minibattle.game;

import minibattle.creature.Creature;

import java.util.ArrayList;
import java.util.List;

import static minibattle.battle.Battle.YELLOW;
import static minibattle.battle.Battle.RESET;

public class OutcomeTable {
    private final List<RoundDetail> rounds;
    private static final int maxNameLength = 20;

    public OutcomeTable() {
        rounds = new ArrayList<>();
    }

    public void addRound(Creature creatureA, Creature creatureB, Creature winner) {
       RoundDetail round = new RoundDetail(creatureA, creatureB, winner);
       rounds.add(round);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("┌─┬──────────────────────┬──┬──────────────────────┐\n");
        for (int i = 0; i < rounds.size(); i++) {
            RoundDetail roundDetail = rounds.get(i);
            String creatureAString = null;
            String creatureBString = null;
            output.append("│").append(i + 1).append("│");
            if (roundDetail.winner() == roundDetail.creatureA()) {
                String winner = roundDetail.creatureA().getName();
                String loser = roundDetail.creatureB().getName();
                int winnerPadding = maxNameLength - winner.length();
                int loserPadding = maxNameLength - loser.length();
                creatureAString = YELLOW + winner + RESET + " ".repeat(winnerPadding);
                creatureBString = loser + " ".repeat(loserPadding);
            } else {
                String winner = roundDetail.creatureB().getName();
                String loser = roundDetail.creatureA().getName();
                int loserPadding = maxNameLength - loser.length();
                int winnerPadding = maxNameLength - winner.length();
                creatureAString = loser + " ".repeat(loserPadding);
                creatureBString = YELLOW + winner + RESET + " ".repeat(winnerPadding);
            }
            output.append(" ").append(creatureAString).append(" ");
            output.append("│VS│");
            output.append(" ").append(creatureBString).append(" ");
            output.append("│\n");
        }
        output.append("└─┴──────────────────────┴──┴──────────────────────┘\n");
        return output.toString();
    }
}

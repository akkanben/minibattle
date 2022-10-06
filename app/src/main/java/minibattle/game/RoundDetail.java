package minibattle.game;

import minibattle.creature.Creature;

public record RoundDetail(Creature creatureA, Creature creatureB, Creature winner) {

    @Override
    public String toString() {
        return "RoundDetail{" +
                "creatureA=" + creatureA.getName() +
                ", creatureB=" + creatureB.getName() +
                ", winner=" + winner.getName() +
                '}';
    }
}

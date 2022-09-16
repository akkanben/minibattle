package minibattle;

import minibattle.battle.Battle;
import minibattle.creature.Creature;

public class MiniBattle {

    public static void main(String[] args) {
        Creature a = new Creature("Potato Knight");
        Creature b = new Creature("Onion Knight");
        Battle battle = new Battle(a, b);
        battle.duel();
    }
}

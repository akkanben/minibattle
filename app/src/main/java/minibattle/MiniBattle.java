package minibattle;

import minibattle.battle.Battle;
import minibattle.creature.Creature;
import minibattle.weapon.Weapon;

public class MiniBattle {

    public static void main(String[] args) {
        Weapon weaponA = new Weapon(); // Random weapon
        Weapon weaponB = new Weapon(); // Random weapon
        Creature creatureA = new Creature("Potato Knight", weaponA);
        Creature creatureB = new Creature("Onion Knight", weaponB);
        Battle battle = new Battle(creatureA, creatureB);
        battle.duel();
    }
}

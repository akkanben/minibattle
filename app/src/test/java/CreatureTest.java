import minibattle.creature.Creature;
import minibattle.weapon.Weapon;
import minibattle.weapon.WeaponKind;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTest {

    @Test
    void lowStatCap() {
        Weapon weapon = new Weapon(WeaponKind.CLUB);
        Creature sut = new Creature("Thief", weapon, -50, -200, 0);
        assertEquals(sut.getName(), "Thief");
        assertEquals(sut.getStat(Creature.Stat.STR).getMax(), 3);
        assertEquals(sut.getStat(Creature.Stat.DEX).getMax(), 3);
        assertEquals(sut.getStat(Creature.Stat.MAG).getMax(), 3);
        assertEquals(sut.getStat(Creature.Stat.HP).getMax(), 6);
        assertEquals(sut.getStat(Creature.Stat.SP).getMax(), 6);
        assertEquals(sut.getTotalAttack(), 1);
    }

    @Test
    void highStatCap() {
        Weapon weapon = new Weapon(WeaponKind.AXE);
        Creature sut = new Creature("Barbarian", weapon, 100, 100, 100);
        assertEquals(sut.getName(), "Barbarian");
        assertEquals(sut.getStat(Creature.Stat.STR).getMax(), 15);
        assertEquals(sut.getStat(Creature.Stat.DEX).getMax(), 15);
        assertEquals(sut.getStat(Creature.Stat.MAG).getMax(), 15);
        assertEquals(sut.getStat(Creature.Stat.HP).getMax(), 30);
        assertEquals(sut.getStat(Creature.Stat.SP).getMax(), 30);
        assertEquals(sut.getTotalAttack(), 8);
    }

    @Test
    void standardCreature() {
        Weapon weapon = new Weapon(WeaponKind.WAND);
        Creature sut = new Creature("Mage", weapon, 8, 12, 14);
        assertEquals(sut.getName(), "Mage");
        assertEquals(sut.getStat(Creature.Stat.STR).getMax(), 8);
        assertEquals(sut.getStat(Creature.Stat.DEX).getMax(), 12);
        assertEquals(sut.getStat(Creature.Stat.MAG).getMax(), 14);
        assertEquals(sut.getStat(Creature.Stat.HP).getMax(), 16);
        assertEquals(sut.getStat(Creature.Stat.SP).getMax(), 24);
        assertEquals(sut.getTotalAttack(), 10);
    }

    @Test
    void longCreatureName() {
       Creature sut = new Creature("King of all Cosmos");
       assertEquals(sut.getName(), "King of all Cos");
    }

}

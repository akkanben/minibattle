import minibattle.creature.Creature;
import minibattle.creature.CreatureStat;
import minibattle.weapon.DamageType;
import minibattle.weapon.Weapon;
import minibattle.weapon.WeaponKind;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponTest {

    @Test
    void weaponAffinity() {
        Weapon sutAxe = new Weapon(WeaponKind.AXE);
        Weapon sutClub = new Weapon(WeaponKind.CLUB);
        Weapon sutDagger = new Weapon(WeaponKind.DAGGER);
        Weapon sutRapier = new Weapon(WeaponKind.RAPIER);
        Weapon sutSpear = new Weapon(WeaponKind.SPEAR);
        Weapon sutSword = new Weapon(WeaponKind.SWORD);
        Weapon sutImprovised = new Weapon(WeaponKind.IMPROVISED);
        Weapon sutUnarmed = new Weapon(WeaponKind.UNARMED);
        Weapon sutWand = new Weapon(WeaponKind.WAND);

        assertEquals(sutAxe.getAffinity(), Creature.Stat.DEX);
        assertEquals(sutClub.getAffinity(), Creature.Stat.STR);
        assertEquals(sutDagger.getAffinity(), Creature.Stat.DEX);
        assertEquals(sutRapier.getAffinity(), Creature.Stat.DEX);
        assertEquals(sutSpear.getAffinity(), Creature.Stat.DEX);
        assertEquals(sutSword.getAffinity(), Creature.Stat.DEX);
        assertEquals(sutImprovised.getAffinity(), Creature.Stat.STR);
        assertEquals(sutUnarmed.getAffinity(), Creature.Stat.STR);
        assertEquals(sutWand.getAffinity(), Creature.Stat.MAG);
    }

    @Test
    void weaponAffinityMultiplier() {
        Weapon sutAxe = new Weapon(WeaponKind.AXE);
        Weapon sutClub = new Weapon(WeaponKind.CLUB);
        Weapon sutDagger = new Weapon(WeaponKind.DAGGER);
        Weapon sutRapier = new Weapon(WeaponKind.RAPIER);
        Weapon sutSpear = new Weapon(WeaponKind.SPEAR);
        Weapon sutSword = new Weapon(WeaponKind.SWORD);
        Weapon sutImprovised = new Weapon(WeaponKind.IMPROVISED);
        Weapon sutUnarmed = new Weapon(WeaponKind.UNARMED);
        Weapon sutWand = new Weapon(WeaponKind.WAND);

        assertEquals(sutAxe.getAffinityMultiplier(), 0.2);
        assertEquals(sutClub.getAffinityMultiplier(), 0.3);
        assertEquals(sutDagger.getAffinityMultiplier(), 0.2);
        assertEquals(sutRapier.getAffinityMultiplier(), 0.2);
        assertEquals(sutSpear.getAffinityMultiplier(), 0.2);
        assertEquals(sutSword.getAffinityMultiplier(), 0.2);
        assertEquals(sutImprovised.getAffinityMultiplier(), 0.3);
        assertEquals(sutUnarmed.getAffinityMultiplier(), 0.3);
        assertEquals(sutWand.getAffinityMultiplier(), 0.5);
    }

    @Test
    void nonNullRandomWeapon() {
        Weapon sut = new Weapon();
        assertNotNull(sut);
        assertTrue(sut.getDamage() > 0);
        assertTrue(sut.getAffinityMultiplier() > 0);
        assertTrue(sut.getStaminaCost() > 0);
    }
}

package minibattle.weapon;

import minibattle.creature.Creature;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Weapon {

    private static final double BLUNT_STR_MULTIPLIER = 0.3;
    private static final double SLICING_DEX_MULTIPLIER = 0.2;
    private static final double PIERCING_DEX_MULTIPLIER = 0.2;
    private static final double MAGICAL_MAG_MULTIPLIER = 0.5;

    private static final Random random = new Random();
    private static final List<WeaponKind> KINDS = List.of(WeaponKind.values());

    private final WeaponKind weaponKind;
    private int baseDamage;
    private int staminaCost;
    private DamageType damageType;
    private Creature.Stat affinity;
    private double affinityMultiplier;

    public Weapon() {
        weaponKind = getRandomWeaponKind();
        initWeaponBase();
        initWeaponAffinity();
    }

    public Weapon(WeaponKind weaponKind){
        this.weaponKind = weaponKind;
        initWeaponBase();
        initWeaponAffinity();
    }

    private WeaponKind getRandomWeaponKind() {
        return KINDS.get(random.nextInt(KINDS.size()));
    }

    private void initWeaponAffinity() {
        switch (damageType) {
            case BLUNT -> {
                affinity = Creature.Stat.STR;
                affinityMultiplier = BLUNT_STR_MULTIPLIER;
            }
            case SLICING -> {
                affinity = Creature.Stat.DEX;
                affinityMultiplier = SLICING_DEX_MULTIPLIER;
            }
            case PIERCING -> {
                affinity = Creature.Stat.DEX;
                affinityMultiplier = PIERCING_DEX_MULTIPLIER;
            }
            case MAGICAL -> {
                affinity = Creature.Stat.MAG;
                affinityMultiplier = MAGICAL_MAG_MULTIPLIER;
            }
        }
    }

    private void initWeaponBase() {
        switch (weaponKind) {
            case DAGGER -> {
                baseDamage = 3;
                staminaCost = 4;
                damageType = DamageType.PIERCING;
            }
            case SWORD -> {
                baseDamage = 4;
                staminaCost = 7;
                damageType = DamageType.SLICING;
            }
            case UNARMED, IMPROVISED -> {
                baseDamage = 2;
                staminaCost = 6;
                damageType = DamageType.BLUNT;
            }
            case CLUB -> {
                baseDamage = 5;
                staminaCost = 12;
                damageType = DamageType.BLUNT;
            }
            case RAPIER -> {
                baseDamage = 4;
                staminaCost = 7;
                damageType = DamageType.PIERCING;
            }
            case AXE -> {
                baseDamage = 5;
                staminaCost = 11;
                damageType = DamageType.SLICING;
            }
            case SPEAR -> {
                baseDamage = 4;
                staminaCost = 8;
                damageType = DamageType.PIERCING;
            }
            case WAND -> {
                baseDamage = 3;
                staminaCost = 1;
                damageType = DamageType.MAGICAL;
            }
        }
    }


    public int getStaminaCost() {
        return staminaCost;
    }

    public Creature.Stat getAffinity() {
        return affinity;
    }

    public double getAffinityMultiplier() {
        return affinityMultiplier;
    }

    public int getDamage() {
        return baseDamage;
    }

    @Override
    public String toString() {
        return damageType.toString() + " " + weaponKind.toString();
    }
}

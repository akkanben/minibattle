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

    private static final int DAGGER_BASE_DAMAGE = 3;
    private static final int DAGGER_STAMINA_COST = 4;

    private static final int SWORD_BASE_DAMAGE = 4;
    private static final int SWORD_STAMINA_COST = 7;

    private static final int UNARMED_BASE_DAMAGE = 2;
    private static final int UNARMED_STAMINA_COST = 6;

    private static final int IMPROVISED_BASE_DAMAGE = 2;
    private static final int IMPROVISED_STAMINA_COST = 6;

    private static final int CLUB_BASE_DAMAGE = 5;
    private static final int CLUB_STAMINA_COST = 12;

    private static final int RAPIER_BASE_DAMAGE = 4;
    private static final int RAPIER_STAMINA_COST = 7;

    private static final int AXE_BASE_DAMAGE = 5;
    private static final int AXE_STAMINA_COST = 11;

    private static final int SPEAR_BASE_DAMAGE = 4;
    private static final int SPEAR_STAMINA_COST = 8;

    private static final int WAND_BASE_DAMAGE = 3;
    private static final int WAND_STAMINA_COST = 2;

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
                baseDamage = DAGGER_BASE_DAMAGE;
                staminaCost = DAGGER_STAMINA_COST;
                damageType = DamageType.PIERCING;
            }
            case SWORD -> {
                baseDamage = SWORD_BASE_DAMAGE;
                staminaCost = SWORD_STAMINA_COST;
                damageType = DamageType.SLICING;
            }
            case UNARMED -> {
                baseDamage = UNARMED_BASE_DAMAGE;
                staminaCost = UNARMED_STAMINA_COST;
                damageType = DamageType.BLUNT;
            }
            case IMPROVISED -> {
                baseDamage = IMPROVISED_BASE_DAMAGE;
                staminaCost = IMPROVISED_STAMINA_COST;
                damageType = DamageType.BLUNT;
            }
            case CLUB -> {
                baseDamage = CLUB_BASE_DAMAGE;
                staminaCost = CLUB_STAMINA_COST;
                damageType = DamageType.BLUNT;
            }
            case RAPIER -> {
                baseDamage = RAPIER_BASE_DAMAGE;
                staminaCost = RAPIER_STAMINA_COST;
                damageType = DamageType.PIERCING;
            }
            case AXE -> {
                baseDamage = AXE_BASE_DAMAGE;
                staminaCost = AXE_STAMINA_COST;
                damageType = DamageType.SLICING;
            }
            case SPEAR -> {
                baseDamage = SPEAR_BASE_DAMAGE;
                staminaCost = SPEAR_STAMINA_COST;
                damageType = DamageType.PIERCING;
            }
            case WAND -> {
                baseDamage = WAND_BASE_DAMAGE;
                staminaCost = WAND_STAMINA_COST;
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

package minibattle.creature;

import minibattle.weapon.Weapon;
import minibattle.weapon.WeaponKind;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Creature {

    private static final int STAT_MIN = 3;
    private static final int STAT_MAX = 15;
    private static final int DEFAULT_STAMINA_RECHARGE = 10;

    private final String name;
    private Map<Stat, CreatureStat> stats;
    private final Weapon weapon;
    private final int affinityBonus;
    private final int weaponPenalty;
    private final int totalAttack;

    public enum Stat {
        HP,
        SP,
        STR,
        DEX,
        MAG;
    }

    public Creature(String name) {
        this.name = validateNameLength(name);
        weapon = new Weapon(WeaponKind.UNARMED);
        initializeCreatureStats();
        affinityBonus = (int) (weapon.getAffinityMultiplier() * stats.get(weapon.getAffinity()).getMax());
        weaponPenalty = stats.get(Stat.SP).getMax() >= weapon.getStaminaCost() ? 0 :
                weapon.getStaminaCost() - stats.get(Stat.SP).getMax();
        totalAttack = generateTotalAttack();
    }

    public Creature(String name, @NotNull Weapon weapon) {
        this.name = validateNameLength(name);
        this.weapon = weapon;
        initializeCreatureStats();
        affinityBonus = (int) (weapon.getAffinityMultiplier() * stats.get(weapon.getAffinity()).getMax());
        weaponPenalty = stats.get(Stat.SP).getMax() >= weapon.getStaminaCost() ? 0 :
                weapon.getStaminaCost() - stats.get(Stat.SP).getMax();
        totalAttack = generateTotalAttack();
    }

    public Creature(String name, @NotNull Weapon weapon, int str, int dex, int mag) {
        this.name = validateNameLength(name);
        this.weapon = weapon;
        initializeCreatureStats(str, dex, mag);
        affinityBonus = (int) (weapon.getAffinityMultiplier() * stats.get(weapon.getAffinity()).getMax());
        weaponPenalty = stats.get(Stat.SP).getMax() >= weapon.getStaminaCost() ? 0 :
                weapon.getStaminaCost() - stats.get(Stat.SP).getMax();
        totalAttack = generateTotalAttack();
    }

    private void initializeCreatureStats() {
        stats = new HashMap<>();
        Random random = new Random();
        int str = random.nextInt((STAT_MAX - STAT_MIN) + 1) + STAT_MIN;
        int dex = random.nextInt((STAT_MAX - STAT_MIN) + 1) + STAT_MIN;
        int mag = random.nextInt((STAT_MAX - STAT_MIN) + 1) + STAT_MIN;
        initializeCreatureStats(str, dex, mag);
    }

    private void initializeCreatureStats(int str, int dex, int mag) {
        stats = new HashMap<>();
        stats.put(Stat.STR, new CreatureStat(validateStatRange(str)));
        stats.put(Stat.DEX, new CreatureStat(validateStatRange(dex)));
        stats.put(Stat.MAG, new CreatureStat(validateStatRange(mag)));
        stats.put(Stat.HP, new CreatureStat(stats.get(Stat.STR).getMax() * 2));
        stats.put(Stat.SP, new CreatureStat(stats.get(Stat.DEX).getMax() * 2));
    }

    private int generateTotalAttack() {
        int attack = weapon.getDamage() + affinityBonus - weaponPenalty;
        return Math.max(attack, 1);
    }

    private @NotNull String validateNameLength(@NotNull String str) {
        if (str.length() > 15)
            return str.substring(0, 15);
        else
            return str;
    }

    private int validateStatRange(int stat) {
        if (stat >= STAT_MIN && stat <= STAT_MAX)
            return stat;
        else if (stat < STAT_MIN)
            return STAT_MIN;
        else
            return STAT_MAX;
    }

    // Returns attack messages and makes HP/SP adjustments
    public String attack(Creature enemyCreature) {
        StringBuilder outputMessage = new StringBuilder();
        int currentSP = stats.get(Stat.SP).getCurrent();
        int maxSP = stats.get(Stat.SP).getMax();
        // We can afford the SP to attack
        if (currentSP >= weapon.getStaminaCost()) {
            appendStandardAttackMessage(outputMessage, enemyCreature.getName(), totalAttack);
            enemyCreature.getStat(Stat.HP).decrease(totalAttack);
            stats.get(Stat.SP).decrease(weapon.getStaminaCost());
        // We're full of stamina, but we can't afford the SP to attack
        } else if (maxSP < weapon.getStaminaCost() && currentSP == maxSP) {
            appendLowMaxStaminaAttackMessage(outputMessage, enemyCreature.getName(), weaponPenalty, totalAttack, maxSP);
            enemyCreature.getStat(Stat.HP).decrease(totalAttack);
            stats.get(Stat.SP).decrease(weapon.getStaminaCost());
        // We're not at full stamina, and we can't afford the SP to attack, so we recharge
        } else  {
            if (maxSP >= DEFAULT_STAMINA_RECHARGE) {
                appendStandardStaminaRecoveryMessage(outputMessage);
            } else {
                appendLowMaxStaminaRecoveryMessage(outputMessage, maxSP);
            }
        }
        return outputMessage.toString();
    }

    private void appendLowMaxStaminaRecoveryMessage(@NotNull StringBuilder outputMessage, int maxSp) {
        outputMessage.append(name).append(" recovers ").append(maxSp).append(" Stamina Points.");
        stats.get(Stat.SP).increase(maxSp);
    }

    private void appendStandardStaminaRecoveryMessage(@NotNull StringBuilder outputMessage) {
        outputMessage.append(name).append(" recovers ").append(DEFAULT_STAMINA_RECHARGE).append(" Stamina Points.");
        stats.get(Stat.SP).increase(DEFAULT_STAMINA_RECHARGE);
    }

    private void appendStandardAttackMessage(@NotNull StringBuilder outputMessage, String enemyName, int totalAttack) {
        outputMessage.append(name).append(" attacks ").append(enemyName);
        outputMessage.append(" with ").append(weapon);
        outputMessage.append(" for ").append(totalAttack).append(" damage.\n");
        outputMessage.append(name).append(" uses ").append(weapon.getStaminaCost()).append(" Stamina Points.\n");
    }

    private void appendLowMaxStaminaAttackMessage(@NotNull StringBuilder outputMessage, String enemyName, int weaponPenalty, int totalAttack, int maxSp) {
        outputMessage.append(name).append(" attacks ").append(enemyName);
        outputMessage.append( " at a ").append(-weaponPenalty).append(" stamina penalty");
        outputMessage.append(" with ").append(weapon);
        outputMessage.append(" for ").append(totalAttack).append(" damage.\n");
        outputMessage.append(name).append(" uses all ").append(maxSp).append(" Stamina Points.\n");
    }

    public String getName() {
        return name;
    }

    public CreatureStat getStat(Stat stat) {
        return stats.get(stat);
    }

    public int getTotalAttack() {
        return totalAttack;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public String toString() {
        return name
                + " { HP: " + stats.get(Stat.HP).getMax()
                + ", SP: " + stats.get(Stat.SP).getMax()
                + ", STR: " + stats.get(Stat.STR).getMax()
                + ", DEX: " + stats.get(Stat.DEX).getMax()
                + ", MAG: " + stats.get(Stat.MAG).getMax()
                + " }";
    }

}
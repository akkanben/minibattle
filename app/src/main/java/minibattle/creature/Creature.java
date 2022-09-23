package minibattle.creature;

import minibattle.weapon.Weapon;
import minibattle.weapon.WeaponKind;

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

    public enum Stat {
        HP,
        SP,
        STR,
        DEX,
        MAG;
    }

    public Creature(String name) {
        this.name = name;
        weapon = new Weapon(WeaponKind.UNARMED);
        initializeCreatureStats();
    }

    public Creature(String name, Weapon weapon) {
        this.name = name;
        this.weapon = weapon;
        initializeCreatureStats();
    }

    public String getName() {
        return name;
    }

    public CreatureStat getStat(Stat stat) {
        return stats.get(stat);
    }

    // returns attack messages
    public String attack(Creature creature) {
        int affinityBonus = (int) (weapon.getAffinityMultiplier() * stats.get(weapon.getAffinity()).getCurrent());
        StringBuilder outputMessage = new StringBuilder();
        int currentSP = stats.get(Stat.SP).getCurrent();
        int maxSP = stats.get(Stat.SP).getMax();
        int staminaCost = weapon.getStaminaCost();
        int weaponPenalty = maxSP >= staminaCost ? 0 : staminaCost - maxSP;
        int totalAttack = weapon.getDamage() + affinityBonus - weaponPenalty;

        // We can afford the SP to attack
        if (currentSP >= staminaCost) {
            outputMessage.append(name).append(" attacks ").append(creature.getName());
            outputMessage.append(" with ").append(weapon);
            outputMessage.append(" for ").append(totalAttack).append(" damage.\n");
            outputMessage.append(name).append(" uses ").append(weapon.getStaminaCost()).append(" Stamina Points.\n");
            creature.getStat(Stat.HP).decrease(totalAttack);
            stats.get(Stat.SP).decrease(weapon.getStaminaCost());
        // We're full of stamina, but we can't afford the SP to attack
        } else if (maxSP < staminaCost && currentSP == maxSP) {
            outputMessage.append(name).append(" attacks ").append(creature.getName());
            outputMessage.append( " at a ").append(-weaponPenalty).append(" stamina penalty");
            outputMessage.append(" with ").append(weapon);
            outputMessage.append(" for ").append(totalAttack).append(" damage.\n");
            outputMessage.append(name).append(" uses all ").append(maxSP).append(" Stamina Points.\n");
            creature.getStat(Stat.HP).decrease(totalAttack);
            stats.get(Stat.SP).decrease(weapon.getStaminaCost());
        // We're not at full stamina, and we can't afford the SP to attack, so we recharge
        } else  {
            if (maxSP >= DEFAULT_STAMINA_RECHARGE) {
                outputMessage.append(name).append(" recovers ").append(DEFAULT_STAMINA_RECHARGE).append(" Stamina Points.");
                stats.get(Stat.SP).increase(DEFAULT_STAMINA_RECHARGE);
            } else {
                outputMessage.append(name).append(" recovers ").append(maxSP).append(" Stamina Points.");
                stats.get(Stat.SP).increase(maxSP);
            }
        }
        return outputMessage.toString();
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

    private void initializeCreatureStats() {
        stats = new HashMap<>();
        Random random = new Random();
        stats.put(Stat.STR, new CreatureStat(getRandomStatValue(random)));
        stats.put(Stat.DEX, new CreatureStat(getRandomStatValue(random)));
        stats.put(Stat.MAG, new CreatureStat(getRandomStatValue(random)));
        stats.put(Stat.HP, new CreatureStat(stats.get(Stat.STR).getMax() * 2));
        stats.put(Stat.SP, new CreatureStat(stats.get(Stat.DEX).getMax() * 2));
    }

    private int getRandomStatValue(Random random) {
        return random.nextInt((STAT_MAX - STAT_MIN) + 1) + STAT_MIN;
    }

}
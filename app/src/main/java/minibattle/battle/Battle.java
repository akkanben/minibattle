package minibattle.battle;

import minibattle.creature.Creature;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Battle {

    private final Creature a;
    private final Creature b;
    private final int width;
    private Creature winner = null;

    private final String os = System.getProperty("os.name");

    private static final String RESET = "\u001B[0m";
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";

    public static String STAT_BLOCK_FULL = "\u25a0"; // ■
    public static String STAT_BLOCK_EMPTY = "\u25a1"; // □


    public Battle(Creature a, Creature b) {
        this.a = a;
        this.b = b;
        width = calculateWidth() + 1;
        validateStatBlockCharacters();
    }

    // Windows default code page for CMD/Powershell (437) may not render the default blocks correctly.
    // These alternatives are part of code page 437 should be good.
    private void validateStatBlockCharacters() {
        if (os.contains("Windows")) {
            STAT_BLOCK_EMPTY = "\u2591"; // ▓
            STAT_BLOCK_FULL = "\u2593"; // ░
        }
    }

    public String printStatus() {
        cls();
        String aName = a.getName();
        String aHP = STAT_BLOCK_FULL.repeat(a.getStat(Creature.Stat.HP).getCurrent());
        String aHPEmpty = STAT_BLOCK_EMPTY.repeat(a.getStat(Creature.Stat.HP).getMax() - aHP.length());
        String aSP = STAT_BLOCK_FULL.repeat(a.getStat(Creature.Stat.SP).getCurrent());
        String aSPEmpty = STAT_BLOCK_EMPTY.repeat(a.getStat(Creature.Stat.SP).getMax() - aSP.length());
        String aStaticStats = "STR: " + a.getStat(Creature.Stat.STR).getCurrent()
                + " DEX: " + a.getStat(Creature.Stat.DEX).getCurrent()
                + " MAG: " + a.getStat(Creature.Stat.MAG).getCurrent();

        String bName = b.getName();
        String bHP = STAT_BLOCK_FULL.repeat(b.getStat(Creature.Stat.HP).getCurrent());
        String bHPEmpty = STAT_BLOCK_EMPTY.repeat(b.getStat(Creature.Stat.HP).getMax() - bHP.length());
        String bSP = STAT_BLOCK_FULL.repeat(b.getStat(Creature.Stat.SP).getCurrent());
        String bSPEmpty = STAT_BLOCK_EMPTY.repeat(b.getStat(Creature.Stat.SP).getMax() - bSP.length());
        String bStaticStats = "STR: " + b.getStat(Creature.Stat.STR).getCurrent()
                + " DEX: " + b.getStat(Creature.Stat.DEX).getCurrent()
                + " MAG: " + b.getStat(Creature.Stat.MAG).getCurrent();

        int namePadding = width - aName.length() + "XX: ".length();
        int hpPadding = width - (aHP.length() + aHPEmpty.length());
        int spPadding = width - (aSP.length() + aSPEmpty.length());
        int statsPadding = width - aStaticStats.length() + "XX: ".length();

        StringBuilder output = new StringBuilder();
        // Name line
        output.append(CYAN).append(aName);
        output.append(" ".repeat(namePadding));
        output.append(PURPLE).append(bName).append("\n\n");

        // Static stats line
        output.append(CYAN).append(aStaticStats);
        output.append(" ".repeat(statsPadding));
        output.append(PURPLE).append(bStaticStats).append("\n");

        // HP gauge line
        output.append(CYAN).append("HP: ").append(aHP).append(aHPEmpty);
        output.append(" ".repeat(hpPadding));
        output.append(PURPLE).append("HP: ").append(bHP).append(bHPEmpty).append("\n");

        // SP gauge line
        output.append(CYAN).append("SP: ").append(aSP).append(aSPEmpty);
        output.append(" ".repeat(spPadding));
        output.append(PURPLE).append("SP: ").append(bSP).append(bSPEmpty).append("\n");

        // reset color
        output.append(RESET);
        return output.toString();
    }

    public void duel() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String continueMessage = "\nPRESS ENTER TO CONTINUE";
        System.out.println(printStatus());
        System.out.println(continueMessage);
        scanner.nextLine();

        while (winner == null) {
            String msg = "";
            if (random.nextBoolean()) {
                msg = a.attack(b);
            } else {
                msg = b.attack(a);
            }
            if (a.getStat(Creature.Stat.HP).getCurrent() <= 0) {
                winner = b;
            }
            if (b.getStat(Creature.Stat.HP).getCurrent() <= 0) {
                winner = a;
            }
            System.out.println(printStatus());
            System.out.println(msg);
            System.out.println(continueMessage);
            scanner.nextLine();
        }
        scanner.close();
        System.out.println(printStatus());
        System.out.println(winner.getName() + " was victorious.");
    }

    private int calculateWidth() {
        return IntStream.of(
                        a.getName().length(),
                        a.getStat(Creature.Stat.HP).getMax(),
                        a.getStat(Creature.Stat.SP).getMax(),
                        "STR: XX DEX: XX MAG: XX".length())
                .max()
                .getAsInt();
    }

    private void cls() {
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

}
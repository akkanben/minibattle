package minibattle.battle;

import minibattle.creature.Creature;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

import static minibattle.MiniBattle.inputScanner;
import static minibattle.creature.Creature.Stat.*;

public class Battle {

    private static final int WIDTH_PADDING = 2;

    private final Creature a;
    private final Creature b;
    private SelectionSide selectionSide;
    private final int leftWidth; // Char count for creature "a" -- left side creature
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

    public enum SelectionSide {
        LEFT,
        RIGHT
    }

    public Battle(Creature a, Creature b) {
        this.a = a;
        this.b = b;
        selectionSide = null;
        leftWidth = calculateWidth();
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

    public void setSelectionSide(SelectionSide side) {
        selectionSide = side;
    }

    public String printStatus() {
        cls();
        String aName = a.getName();
        String aHP = STAT_BLOCK_FULL.repeat(a.getStat(HP).getCurrent());
        String aHPEmpty = STAT_BLOCK_EMPTY.repeat(a.getStat(HP).getMax() - aHP.length());
        String aSP = STAT_BLOCK_FULL.repeat(a.getStat(Creature.Stat.SP).getCurrent());
        String aSPEmpty = STAT_BLOCK_EMPTY.repeat(a.getStat(Creature.Stat.SP).getMax() - aSP.length());
        String aStaticStats = "STR: " + a.getStat(STR).getCurrent()
                + " DEX: " + a.getStat(DEX).getCurrent()
                + " MAG: " + a.getStat(Creature.Stat.MAG).getCurrent();
        String aWeaponInfo = a.getWeapon() + ": " + a.getTotalAttack();

        String bName = b.getName();
        String bHP = STAT_BLOCK_FULL.repeat(b.getStat(HP).getCurrent());
        String bHPEmpty = STAT_BLOCK_EMPTY.repeat(b.getStat(HP).getMax() - bHP.length());
        String bSP = STAT_BLOCK_FULL.repeat(b.getStat(Creature.Stat.SP).getCurrent());
        String bSPEmpty = STAT_BLOCK_EMPTY.repeat(b.getStat(Creature.Stat.SP).getMax() - bSP.length());
        String bStaticStats = "STR: " + b.getStat(STR).getCurrent()
                + " DEX: " + b.getStat(DEX).getCurrent()
                + " MAG: " + b.getStat(Creature.Stat.MAG).getCurrent();
        String bWeaponInfo = b.getWeapon() + ": " + b.getTotalAttack();

        int namePadding = leftWidth - aName.length();
        int hpPadding = leftWidth - (aHP.length() + aHPEmpty.length() + "HP: ".length());
        int spPadding = leftWidth - (aSP.length() + aSPEmpty.length() + "HP: ".length());
        int statsPadding = leftWidth - aStaticStats.length();
        int weaponPadding = leftWidth - aWeaponInfo.length();

        StringBuilder output = new StringBuilder();
        // Name line
        output.append(CYAN).append(aName);
        output.append(" ".repeat(namePadding));
        output.append(PURPLE).append(bName).append("\n");

        // Creature Selector
        if (selectionSide == SelectionSide.LEFT) {
            output.append(CYAN).append("═".repeat(aName.length())).append("\n\n");
        } else if (selectionSide == SelectionSide.RIGHT) {
            output.append(PURPLE).append(" ".repeat(leftWidth)).append("═".repeat(bName.length())).append("\n\n");
        } else {
            output.append("\n\n");
        }

        // Static stats line
        output.append(CYAN).append(aStaticStats);
        output.append(" ".repeat(statsPadding));
        output.append(PURPLE).append(bStaticStats).append("\n");

        // Weapon line
        output.append(CYAN).append(aWeaponInfo);
        output.append(" ".repeat(weaponPadding));
        output.append(PURPLE).append(bWeaponInfo).append("\n");

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

    public Creature duel() {
        //Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        String continueMessage = "\nPRESS ENTER TO CONTINUE";
        System.out.println(printStatus());
        System.out.println("┌─────────────────────┐");
        System.out.println("│ Prepare For A Dual! │");
        System.out.println("└─────────────────────┘");
        System.out.println(continueMessage);
        inputScanner().nextLine();

        while (winner == null) {
            String msg;
            if (random.nextBoolean()) {
                msg = a.attack(b);
            } else {
                msg = b.attack(a);
            }
            if (a.getStat(HP).getCurrent() <= 0) {
                winner = b;
            }
            if (b.getStat(HP).getCurrent() <= 0) {
                winner = a;
            }
            System.out.println(printStatus());
            System.out.println(msg);
            System.out.println(continueMessage);
            inputScanner().nextLine();
        }
        System.out.println(printStatus());
        System.out.println(winner.getName() + " was victorious.");
        return winner;
    }

    public SelectionSide getSelectionSide() {
        return selectionSide;
    }

    // This returns
    private int calculateWidth() {
        // Stats and attack will be either a length of 1 digit or 2 digits
        int strLength = a.getStat(STR).getMax() > 9 ? 2 : 1;
        int dexLength = a.getStat(DEX).getMax() > 9 ? 2 : 1;
        int magLength = a.getStat(MAG).getMax() > 9 ? 2 : 1;
        int attackLength = a.getTotalAttack() > 9 ? 2 : 1;
        // Return the largest line length (name|stats|weapon|hp|sp) plus WIDTH_PADDING
        return IntStream.of(
                        a.getName().length(),
                        "STR: ".length() + strLength + " DEX: ".length() + dexLength + " MAG: ".length() + magLength,
                        a.getWeapon().toString().length() + " ATK: ".length() + attackLength,
                        a.getStat(HP).getMax() + "XX: ".length(),
                        a.getStat(SP).getMax() + "XX: ".length())
                .max()
                .getAsInt() + WIDTH_PADDING;
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
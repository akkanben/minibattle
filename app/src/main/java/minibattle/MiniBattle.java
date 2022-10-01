package minibattle;

import minibattle.creature.Creature;
import minibattle.creature.name.NameGetter;
import minibattle.game.SingleElimination;

import java.util.Random;
import java.util.Scanner;

public class MiniBattle {

    private static final Random random = new Random();
    private static final NameGetter nameGetter = new NameGetter();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SingleElimination game = new SingleElimination(3);
        game.start();
    }

    public static Random random() {
        return random;
    }

    public static String getCreatureName(Creature.Stat affinity) {
       return nameGetter.getName(affinity);
    }

    public static Scanner inputScanner() {
        return scanner;
    }
}

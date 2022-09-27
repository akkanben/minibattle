package minibattle;

import minibattle.battle.Battle;
import minibattle.creature.Creature;
import minibattle.weapon.Weapon;

import java.util.Scanner;

public class MiniBattle {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Creature creatureA = new Creature("Potato Knight", new Weapon());
        Creature creatureB = new Creature("Onion Knight", new Weapon());
        Battle battle;
        String[] mobs = {"MobA", "MobB", "MobC"};
        Creature selectedCreature;
        int roundCount = 3;
        for(int i = 0 ; i < roundCount; i++) {
            battle = new Battle(creatureA, creatureB);
            System.out.println(battle.printStatus());
            int selection;
            do {
                System.out.println("Please select your creature.");
                System.out.print("Enter 1 for " + creatureA.getName() + " or 2 for " + creatureB.getName() + ":");
                selection = scanner.nextInt();
                scanner.nextLine(); // to consume \n
            } while (selection != 1 && selection != 2);
            if (selection == 1) {
                System.out.println("\nYou selected " + creatureA.getName());
                selectedCreature = creatureA;
                creatureB = new Creature(mobs[i]);
            } else {
                System.out.println("\nYou selected " + creatureB.getName());
                selectedCreature = creatureB;
                creatureA = new Creature(mobs[i]);
            }
            System.out.println("\nPress Enter to Continue");
            scanner.nextLine();
            Creature result = battle.duel(scanner);
            if (result.equals(selectedCreature)) {
                System.out.println("You selected the winner.");
                System.out.println("Moving on to the next round");
            } else {
                System.out.println("You failed to select the winner. Game Over.");
                System.exit(0);
            }
            System.out.println("\nPress Enter to Continue");
            scanner.nextLine();
        }
        System.out.println("\nCongratulations!!! You made it to the end.");
        scanner.close();
    }
}

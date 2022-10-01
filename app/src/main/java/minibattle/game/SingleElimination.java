package minibattle.game;

import minibattle.battle.Battle;
import minibattle.creature.Creature;

import static minibattle.MiniBattle.inputScanner;

public class SingleElimination {

    private final int rounds;
    private Creature creatureA;
    private Creature creatureB;
    private Battle battle;

    public SingleElimination(int rounds) {
        this.rounds = rounds;
    }

    public void start() {
        creatureA = new Creature();
        creatureB = new Creature();
        for(int i = 0 ; i < rounds; i++) {
            battle = new Battle(creatureA, creatureB);
            System.out.println(battle.printStatus());
            Creature selectedCreature = getSelectedCreature();
            Creature result = battle.duel();
            if (result.equals(selectedCreature)) {
                System.out.println("You selected the winner.");
                System.out.println("Moving on to the next round");
                // TODO add outcome to list
                createNewContender(selectedCreature);
            } else {
                System.out.println("You failed to select the winner. Game Over.");
                // TODO display outcome list for progress
                // TODO display game over art
                System.exit(0);
            }
            System.out.println("\nPress Enter to Continue");
            inputScanner().nextLine();
        }
        System.out.println("\nCongratulations!!! You made it to the end.");
        // TODO display outcome list
        // TODO display congratulations art
        inputScanner().close();
    }

    private void createNewContender(Creature winner) {
       if (creatureA.equals(winner)) {
           creatureB = new Creature();
       } else {
           creatureA = new Creature();
       }
    }

    private Creature getSelectedCreature() {
        String selection;
        System.out.println("Please select your creature.");
        Creature selectedCreature = null;
        do {
            System.out.print("Enter 1 for " + creatureA.getName() + " or 2 for " + creatureB.getName() + ": ");
            selection = inputScanner().nextLine();
            if (selection.equals("1")) {
                battle.setSelectionSide(Battle.SelectionSide.LEFT);
                System.out.println("\nYou selected " + creatureA.getName());
                System.out.println("\nPress Enter to Continue");
                inputScanner().nextLine();
                selectedCreature = creatureA;
            } else if (selection.equals("2")){
                battle.setSelectionSide(Battle.SelectionSide.RIGHT);
                System.out.println("\nYou selected " + creatureB.getName());
                System.out.println("\nPress Enter to Continue");
                inputScanner().nextLine();
                selectedCreature = creatureB;
            }
        } while (!selection.equals("1") && !selection.equals("2"));
        return selectedCreature;
    }

}

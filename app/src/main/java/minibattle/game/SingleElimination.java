package minibattle.game;

import minibattle.battle.Battle;
import minibattle.creature.Creature;

import static minibattle.MiniBattle.inputScanner;

public class SingleElimination {

    private final int rounds;
    private Creature creatureA;
    private Creature creatureB;
    private Battle battle;
    private final OutcomeTable outcomeTable;

    public SingleElimination(int rounds) {
        this.rounds = rounds;
        outcomeTable = new OutcomeTable();
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
                System.out.println(getMovingOnMessage());
                outcomeTable.addRound(creatureA, creatureB, result);
                createNewContender(selectedCreature);
            } else {
                outcomeTable.addRound(creatureA, creatureB, result);
                System.out.println(battle.printStatus());
                System.out.println(getGameOverMessage());
                System.out.println(outcomeTable.toString());
                System.exit(0);
            }
            System.out.println("\nPress Enter to Continue");
            inputScanner().nextLine();
        }
        System.out.println(battle.printStatus());
        System.out.println(getCongratulationsMessage());
        System.out.println(outcomeTable.toString());
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

    private String getMovingOnMessage() {
        return """
                ┌──────────────────────────────┐
                │   You Selected The Winner!   │
                │ Moving On To The Next Round! │
                └──────────────────────────────┘
                """;
    }

    private String getCongratulationsMessage() {
        return """
                         ,     \\    /      ,
                        / \\    )\\__/(     / \\
                       /   \\  (_\\  /_)   /   \\
                ┌─────/─────\\──\\@  @/───/─────\\──────┐
                │              |\\../|                │
                │               \\VV/                 │
                │                                    │
                │          CONGRATULATIONS           │
                │       You Made It To The End       │
                └────────────────────────────────────┘
                  |    /\\ /      \\\\       \\ /\\    |
                  |  /   V        ))       V   \\  |
                  |/     `       //        '     \\|
                  `              V                '
                """;
    }

    private String getGameOverMessage() {
        return """
                  _____          __  __ ______    ______      ________ _____
                 / ____|   /\\   |  \\/  |  ____|  / __ \\ \\    / /  ____|  __ \\
                | |  __   /  \\  | \\  / | |__    | |  | \\ \\  / /| |__  | |__) |
                | | |_ | / /\\ \\ | |\\/| |  __|   | |  | |\\ \\/ / |  __| |  _  /
                | |__| |/ ____ \\| |  | | |____  | |__| | \\  /  | |____| | \\ \\
                 \\_____/_/    \\_\\_|  |_|______|  \\____/   \\/   |______|_|  \\_\\

                              You Failed To Select The Winner
                """;
    }

}

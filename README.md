# Mini Battle (Simulator)

Mini Battle simulator is a command line application that simulates battle between creatures. In the current version 0.3.0 a series of duals between randomly generated creatures unfolds. The player selects one of the two creatures over a series of three rounds. If the selected creature wins the duel the player moves on to the next round. The stats and weapons are randomly generated each time a new creature is created.

## Example

![Example simulation](./images/example_04.gif)

## Releases

- Version 0.1.0
  - New creatures have randomly generated STR, DEX, and MAG in the range of 5-15.
  - Hit points are calculated as 2x STR and stamina points are 2x DEX. MAG is currently unused.
  - Each round of battle the creatures have a 50/50 chance to be the creature that attacks.
    - An attack costs 10 stamina points and does a flat 5 damage to the opponents hit points.
    - If a creature can not attack because of too few stamina points then it rests and regains 10 points.

- Version 0.2.0
  - New creatures have a random weapon.
    - Current weapon kinds:  dagger, sword, unarmed, club, rapier, axe, spear, wand, improvised.
    - Weapon kinds have one of 4 assigned damage types: blunt, slicing, piercing, magical.
  - Weapons have an affinity based off their damage type.
    - An affinity multiplier increases the attack of the weapon based on the creature's matching stat. 

- Version 0.3.0
  - Three-round single elimination game mode added.
    - User selects their creature each round.
    - The winning creature's status carries over to the next round.
  - Random name chosen for each creature from a pool of names.
    - Names match the weapon's affinity.
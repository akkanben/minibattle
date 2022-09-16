# Mini Battle (Simulator)

Mini Battle simulator is a command line application that simulates battle between creatures. In the current version 0.1.0 a dual between an "Onion Knight" and a "Potato Knight" unfolds. The stats are randomly generated on each run.



## Example

![Example simulation](./images/example_01.gif)


## Releases

- Version 0.1.0
  - New creatures have randomly geneated STR, DEX, and MAG in the range of 5-15.
  - Hit points are calcualted as 2x STR and stamina points are 2x DEX. MAG is currently unused.
  - Each round of battle the creatures have a 50/50 chance to be the creature that attacks.
    - An attack costs 10 stamina points and does a flat 5 damage to the opponents hit points.
    - If a creature can not attack beacuse of too few stamina points then it rests and regains 10 points.



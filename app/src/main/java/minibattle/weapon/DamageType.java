package minibattle.weapon;

import minibattle.creature.Creature;

public enum DamageType {

    BLUNT {
        @Override
        public String toString() {
            return "Blunt";
        }
    },
    SLICING {
        @Override
        public String toString() {
            return "Slicing";
        }
    },
    PIERCING {
        @Override
        public String toString() {
            return "Piercing";
        }
    },
    MAGICAL {
        @Override
        public String toString() {
            return "Magical";
        }
    }

}

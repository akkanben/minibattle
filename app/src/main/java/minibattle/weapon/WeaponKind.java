package minibattle.weapon;

public enum WeaponKind {
    DAGGER {
        @Override
        public String toString() {
            return "Dagger";
        }
    },
    SWORD {
        @Override
        public String toString() {
            return "Sword";
        }
    },
    UNARMED {
        @Override
        public String toString() {
            return "Unarmed";
        }
    },
    CLUB {
        @Override
        public String toString() {
            return "Club";
        }
    },
    RAPIER {
        @Override
        public String toString() {
            return "Rapier";
        }
    },
    AXE {
        @Override
        public String toString() {
            return "Axe";
        }
    },
    SPEAR {
        @Override
        public String toString() {
            return "Spear";
        }
    },
    WAND {
        @Override
        public String toString() {
            return "Wand";
        }
    },
    IMPROVISED {
        @Override
        public String toString() {
            return "Improvised";
        }
    }

}


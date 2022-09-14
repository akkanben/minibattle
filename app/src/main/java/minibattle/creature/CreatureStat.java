package minibattle.creature;

public class CreatureStat {

    private int current;
    private int max;

    public CreatureStat(int value) {
        this.current = value;
        this.max = value;
    }

    public void decrease(int amount) {
        current -= amount;
    }

    public void increase(int amount) {
        current += amount;
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

}

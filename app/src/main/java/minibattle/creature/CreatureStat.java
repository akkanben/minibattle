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
        if (current < 0)
            current = 0;
    }

    public void increase(int amount) {
        current += amount;
        if (current > max)
            current = max;
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

}

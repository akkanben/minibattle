import minibattle.creature.CreatureStat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureStatTest {

    @Test
    void decrease() {
        CreatureStat sut = new CreatureStat(10);
        sut.decrease(1);
        assertEquals(9, sut.getCurrent());
        sut.decrease(9);
        assertEquals(0, sut.getCurrent());
        sut.decrease(1);
        assertEquals(0, sut.getCurrent());
        sut.decrease(100);
        assertEquals(0, sut.getCurrent());
    }

    @Test
    void decreaseNegative() {
        CreatureStat sut = new CreatureStat(10);
        assertThrows(IllegalArgumentException.class, () -> sut.decrease(-1));
    }

    @Test
    void increase() {
        CreatureStat sut = new CreatureStat(10);
        sut.increase(1);
        assertEquals(10, sut.getCurrent());
        sut.decrease(10);
        assertEquals(0, sut.getCurrent());
        sut.increase(50);
        assertEquals(10, sut.getCurrent());
    }

    @Test
    void increaseNegative() {
        CreatureStat sut = new CreatureStat(10);
        assertThrows(IllegalArgumentException.class, () -> sut.increase(-1));
    }

}

package Logic;

/**
 * Created by cheun on 27/1/2018.
 */

public enum Wind {
    EAST(1), SOUTH(2), WEST(3), NORTH(4);

    private int num;

    Wind(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}

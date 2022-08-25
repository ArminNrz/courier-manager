package ir.ronad.courierManager.domain.enumartion;

public enum SendShift {

    NONE(-1), MORNING(1), EVENING(2);

    private final int value;

    SendShift(final int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }
}
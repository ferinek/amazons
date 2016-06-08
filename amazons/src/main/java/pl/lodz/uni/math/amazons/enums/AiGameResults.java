package pl.lodz.uni.math.amazons.enums;

public enum AiGameResults {
    WIN(1),
    LOSS(0),
    DANGEROUS_LOSS(-1);

    private int value;

    private AiGameResults(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

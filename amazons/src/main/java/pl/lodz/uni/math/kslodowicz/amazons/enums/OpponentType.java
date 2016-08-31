package pl.lodz.uni.math.kslodowicz.amazons.enums;

public enum OpponentType {
    TYPE_CHOOSE(-1, "Please, select Board type first."),
    TYPE_HUMAN(0, "Human Player"),
    TYPE_AI_WEAK(1, "Easy AI"),
    TYPE_AI_STRONG(2, "Hard AI");

    private int value;
    private String name;

    private OpponentType(int value, String name) {
        this.value = value;
        this.name = name;

    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

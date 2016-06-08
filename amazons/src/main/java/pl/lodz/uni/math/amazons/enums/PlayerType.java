package pl.lodz.uni.math.amazons.enums;

public enum PlayerType {
    WHITE_PLAYER("White Player"),
    BLACK_PLAYER("Black Player");

    private String name;

    private PlayerType(String name) {
        this.name = name;
    }

    public static String getNameByNumber(int number) {
        return PlayerType.values()[number-1].getName();
    }

    private String getName() {
        return name;
    }
}

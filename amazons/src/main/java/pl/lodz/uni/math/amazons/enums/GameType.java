package pl.lodz.uni.math.amazons.enums;

public enum GameType {
    TYPE_2X2("2I1,0I0,1", "2x2"),
    TYPE_3X3("3I1,0I1,2", "3x3"),
    TYPE_4X4("4I1,0I1,3", "4x4"),
    TYPE_5X5("5I2,0I2,4", "5x5"),
    TYPE_7X7("7I3,0I3,6", "7x7"),
    TYPE_10X10("10I3,0;6,0;0,3;9,3I0,6;3,9;6,9;9,6", "10x10");

    private String description;
    private String name;

    private GameType(String description, String name) {
        this.description = description;
        this.name = name;

    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString()
    {
        return name;    
    }
}

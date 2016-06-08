package pl.lodz.uni.math.amazons.utils;

public class ArrayUtils {

    private ArrayUtils() {

    }

    public static int[][] copyTable(int[][] c) {

        int[][] result = new int[c.length][c.length];
        for (int i = 0; i < c.length; i++)
            for (int j = 0; j < c.length; j++)
                result[i][j] = c[i][j];
        return result;
    }

}

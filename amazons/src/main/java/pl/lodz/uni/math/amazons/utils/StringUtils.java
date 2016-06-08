package pl.lodz.uni.math.amazons.utils;

import pl.lodz.uni.math.amazons.dto.TileDTO;

public class StringUtils {

    private static final String PLACEHOLDER = "[?]";

    private StringUtils() {

    }

    public static String getChar(int number) {
        return Character.toString((char) (number + 65));
    }

    public static String tileDtoToString(TileDTO dto) {
        if (dto != null) {
            return dto.getX() + ";" + dto.getY();
        } else {
            return "null";
        }
    }

    public static TileDTO stringToStileDTO(String string) {
        if (!"null".equals(string)) {
            return new TileDTO(Integer.parseInt(string.split(";")[0]), Integer.parseInt(string.split(";")[1]));
        } else {
            return null;
        }
    }

    public static String printf(String c, Object... args) {
        String result = c;
        for (Object arg : args) {
            result = result.replaceFirst(PLACEHOLDER, arg.toString());
        }
        return result;

    }
}

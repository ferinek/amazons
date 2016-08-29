package pl.lodz.uni.math.kslodowicz.amazons.utils;

import pl.lodz.uni.math.kslodowicz.amazons.dto.TileDTO;

public class StringUtils {

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

}

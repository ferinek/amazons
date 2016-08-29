package pl.lodz.uni.math.kslodowicz.amazons.helper;

import javafx.scene.image.ImageView;

public class OrderedImageView extends ImageView {

	private int xPosition;
	private int yPosition;

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

}

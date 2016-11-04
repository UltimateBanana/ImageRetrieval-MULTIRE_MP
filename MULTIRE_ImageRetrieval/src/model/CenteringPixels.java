package model;

public class CenteringPixels {
	
	int x, y;		// x and y of the pixel
	int LUVColor;	// the LUV value of the pixel
//	float histogram;	// histogram of the pixel
	boolean isCenter;
	
	public CenteringPixels(int x, int y, int LUVColor)
	{
		this.x = x;
		this.y = y;
		this.LUVColor = LUVColor;
		isCenter = false;
	}
	
	public CenteringPixels(int x, int y, int LUVColor, boolean isCenter)
	{
		this.x = x;
		this.y = y;
		this.LUVColor = LUVColor;
		this.isCenter = isCenter;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLUVColor() {
		return LUVColor;
	}

	public boolean isCenter() {
		return isCenter;
	}

	public void setCenter(boolean isCenter) {
		this.isCenter = isCenter;
	}
	
	
}

package cn.com.qytx.cbb.compoundImage.service;

public class BitmapEntity {
	public float x;
	public float y;
	public float width;
	public float height;
	public static int devide = 10;
	int index = -1;

	@Override
	public String toString() {
		return "MyBitmap [x=" + x + ", y=" + y + ", width=" + width
				+ ", height=" + height + ", devide=" + devide + ", index="
				+ index + "]";
	}
}

package org.download.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class Centralizer {
	public static Point getCenterPosition(int width, int height) {
		Point p = new Point();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		Dimension screenSize = toolkit.getScreenSize();
		
		p.x = (((int)screenSize.getWidth()) / 2) - (width / 2);
		
		p.y = (((int)screenSize.getHeight()) / 2) - (height / 2);
		
		return p;
	}
}

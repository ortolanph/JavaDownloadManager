package org.jdm.ui;

import java.awt.*;

public class Centralizer {
    public static Point getCenterPosition(int width, int height) {
        Point p = new Point();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();

        p.x = (((int) screenSize.getWidth()) / 2) - (width / 2);

        p.y = (((int) screenSize.getHeight()) / 2) - (height / 2);

        return p;
    }
}

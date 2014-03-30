package se.jtiden.ml.core.impl;

import se.jtiden.ml.core.api.*;
import se.jtiden.ml.core.api.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class MonaLisa {
    private static final int DOWN_SCALE = 3;
    private JTImage monaLisa;

    public MonaLisa() {
        //monaLisa = getImage("monalisa3.jpg");
        monaLisa = getImage("japan.jpg");
    }

    public static JTImage getImage(final String pathAndFileName) {
        return FastJTImage.fromImage(getRealImage(pathAndFileName), DOWN_SCALE);
    }

    private static BufferedImage getRealImage(final String pathAndFileName) {
        try {
            final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
            return ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JTImage getImage() {
        return monaLisa;
    }

    public JTColor getColorAt(int x, int y) {
        return monaLisa.getColorAt(x, y);

    }

    public int getWidth() {
        return monaLisa.getWidth();
    }

    public int getHeight() {
        return monaLisa.getHeight();
    }

    public JTColor getColorAt(Point p) {
        return getColorAt(p.xInt(), p.yInt());
    }
}

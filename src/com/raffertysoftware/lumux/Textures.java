package com.raffertysoftware.lumux;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Textures
{
    public static Image background;

    public Textures() {
        loadTextures();
    }

    public static void loadTextures() {
        Textures.background = loadTexture("bg.jpg");
    }

    public static void loadIcons() {
        /*Textures.iconMaximize = loadTexture("theme/" + (ThemeColors.LIGHT_MODE ? "light" : "dark") + "/icon_maximize.png");
        Textures.iconMinimize = loadTexture("theme/" + (ThemeColors.LIGHT_MODE ? "light" : "dark") + "/icon_minimize.png");
        Textures.iconNetwork = loadTexture("theme/" + (ThemeColors.LIGHT_MODE ? "light" : "dark") + "/icon_network.png");
        Textures.iconVolume0 = loadTexture("theme/" + (ThemeColors.LIGHT_MODE ? "light" : "dark") + "/icon_volume0.png");
        Textures.iconVolume1 = loadTexture("theme/" + (ThemeColors.LIGHT_MODE ? "light" : "dark") + "/icon_volume1.png");
        Textures.iconVolume2 = loadTexture("theme/" + (ThemeColors.LIGHT_MODE ? "light" : "dark") + "/icon_volume2.png");*/
    }

    public static Image loadGIF(final String fn) {
        return new ImageIcon(Textures.class.getClassLoader().getResource(fn)).getImage();
    }

    public static BufferedImage loadTexture(final String str) {
        try {
            return toCompatibleImage(ImageIO.read(Textures.class.getClassLoader().getResourceAsStream(str)));
        }
        catch (Exception e) {
            try {
                return toCompatibleImage(ImageIO.read(new File(str)));
            } catch (IOException e1) {
                return null;
            }
        }
    }

    private static BufferedImage toCompatibleImage(final BufferedImage image) {
        if(image == null) return null;

        final GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if (image.getColorModel().equals(gfxConfig.getColorModel())) {
            return image;
        }
        final BufferedImage newImage = gfxConfig.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
        final Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return newImage;
    }
}

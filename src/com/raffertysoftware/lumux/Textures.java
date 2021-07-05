package com.raffertysoftware.lumux;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Textures
{
    public static BufferedImage background;

    public static BufferedImage iconClose, iconMaximize, iconMinimize;

    public Textures() {
        loadTextures();
    }

    public static void loadTextures() {
        Textures.background = loadTexture("bg.jpg");
        Textures.iconClose = loadTexture("icon_close.png");
        Textures.iconMaximize = loadTexture("theme/dark/icon_minimize.png");
        Textures.iconMinimize = loadTexture("theme/dark/icon_maximize.png");
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
        return new ImageIcon(LumuxOS.class.getClassLoader().getResource(fn)).getImage();
    }

    public static BufferedImage loadTexture(final String str) {
        try {
            return toCompatibleImage(ImageIO.read(LumuxOS.class.getClassLoader().getResourceAsStream(str)));
        }
        catch (Exception e) {
            e.printStackTrace();
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

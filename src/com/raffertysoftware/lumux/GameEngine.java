package com.raffertysoftware.lumux;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;

public class GameEngine {

    private LumuxOS os;
    private Graphics2D g;
    public static Font font;

    public ArrayList<WindowFrame> windows = new ArrayList<WindowFrame>();

    private int currentCursor = Cursor.DEFAULT_CURSOR;

    private boolean gfxInitialized = false;
    public boolean alreadyDragging = false;
    public boolean alreadyResizing = false;
    public double renderDelta;
    public GameEngine(LumuxOS os) {
        this.os = os;
    }

    public void onInitializeGFX() {
        ((Graphics2D) this.getLumux().getCanvas().getGraphics()).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        ((Graphics2D) this.getLumux().getCanvas().getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        ((Graphics2D) this.getLumux().getCanvas().getGraphics()).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        InputStream is = LumuxOS.class.getClassLoader().getResourceAsStream("ARLRDBD.TTF");
        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gfxInitialized = true;
    }

    public void onInitializeOS() {
        this.windows.add(new WindowFrame(this, 20, 20, 420, 275));
    }

    //Called when the game window is resized.
    public void onResize(int preWidth, int preHeight, int newWidth, int newHeight) {
    }

    public void tick(double dt) {
        for (int i = this.windows.size() - 1; i >= 0; --i) {
            final WindowFrame w = this.windows.get(i);
            w.tick();
        }
        this.getLumux().getInput().update();
    }

    public void render() {
        if(!gfxInitialized) return;
        currentCursor = Cursor.DEFAULT_CURSOR;

        if (!this.getLumux().getInput().isButton(1)) {
            this.alreadyDragging = false;
            this.alreadyResizing = false;
        }

        g.clearRect(0, 0, getLumux().width, getLumux().height);
        g.setFont(GameEngine.font.deriveFont(20.0f));

        g.drawImage(Textures.background, 0, 0, getLumux().width, getLumux().height, null);

        g.setStroke(new BasicStroke(2.0f));
        for (int i = this.windows.size() - 1; i >= 0; --i) {
            WindowFrame w = this.windows.get(i);
            w.setIndex(i);
            w.render();
        }

        //DEBUG GUI
        g.setColor(Color.WHITE);
        g.drawString("Frame Rate: " + getLumux().fps, 22, 62);
        this.getLumux().getGameEngine().setCursor(currentCursor);
    }


    public boolean mouseInWindow(final int index) {
        final WindowFrame w = this.windows.get(index);
        return this.mouseInArea(w.x, w.y, w.w, w.h);
    }

    public boolean mouseInAnyWindow() {
        for (int index = 0; index < this.windows.size(); ++index) {
            final WindowFrame w = this.windows.get(index);
            if (this.mouseInArea(w.x, w.y, w.w, w.h)) {
                return true;
            }
        }
        return false;
    }

    public boolean mouseInWindow(WindowFrame w) {
        return this.mouseInArea(w.x, w.y, w.w, w.h);
    }
    public boolean mouseInWindowContent(WindowFrame w) {
        return this.mouseInArea(w.x + w.getAPI().getOffX(), w.y + w.getAPI().getOffY(), w.w - w.getAPI().getOffW(), w.h - w.getAPI().getOffH());
    }


    public boolean mouseOnlyInWindow(final WindowFrame selW) {
        boolean fBool = false;
        boolean inAnyWindow = false;
        final ArrayList<WindowFrame> allBut = new ArrayList<WindowFrame>();
        allBut.addAll(this.windows);
        allBut.remove(selW);
        for (int i = allBut.size() - 1; i >= 0; --i) {
            final WindowFrame w = allBut.get(i);
            if (this.mouseInWindow(w) && w.index < selW.index) {
                inAnyWindow = true;
            }
        }
        if (inAnyWindow && this.mouseInWindow(selW) && this.windows.get(0) != selW) {
            fBool = true;
        }
        return !fBool;
    }

    public boolean mouseInArea(int x, int y, int w, int h) {
        int mouseX = this.getLumux().getInput().getMouseX();
        int mouseY = this.getLumux().getInput().getMouseY();
        return mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + h;
    }
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }
    public void keyTyped(KeyEvent e) {

    }


    public LumuxOS getLumux() {
        return os;
    }

    public Graphics2D getGraphics() {
        return g;
    }
    public void setGraphicsObject(Graphics2D drawGraphics) {
        this.g = drawGraphics;
    }


    public double getRenderDelta() {
        return renderDelta;
    }

    public void setCursor(int c) {
        currentCursor = c;
    }
    public void setColor(final int bevelColor) {
        this.g.setColor(new Color(bevelColor));
    }

    public void setColor(final int r, final int g2, final int b, int a) {
        if (a < 0) {
            a = 0;
        }
        if (a > 255) {
            a = 255;
        }
        this.g.setColor(new Color(r, g2, b, a));
    }

    public void setColor(final Color c, final int alpha) {
        final Color newColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
        this.g.setColor(newColor);
    }

    public void setColor(final Color c) {
        this.g.setColor(c);
    }

}

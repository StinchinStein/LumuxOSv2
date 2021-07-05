package com.raffertysoftware.lumux;

import sun.audio.AudioStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Array;
import java.util.ArrayList;

public class GameEngine {

    public int tickCount = 0;
    private LumuxOS os;
    private Graphics2D g;
    public static Font font;

    public ArrayList<WindowFrame> windows = new ArrayList<WindowFrame>();

    private int currentCursor = Cursor.DEFAULT_CURSOR;

    private boolean gfxInitialized = false;
    public boolean alreadyDragging = false;
    public boolean alreadyResizing = false;
    public double renderDelta;

    //Sounds
    public static Clip MOUSE_CLICK_ON, MOUSE_CLICK_OFF;

    public GameEngine(LumuxOS os) {
        this.os = os;
    }

    public void onInitializeGFX() {
        MOUSE_CLICK_ON = loadSound("mouse_click_on.wav");
        MOUSE_CLICK_OFF = loadSound("mouse_click_off.wav");

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
        this.windows.add(new WindowFrame(this, this.getLumux().width - 450, 20, 420, 275));
    }

    //Called when the game window is resized.
    public void onResize(int preWidth, int preHeight, int newWidth, int newHeight) {
    }

    public void tick(double dt) {
        tickCount++;
        for (int i = this.windows.size() - 1; i >= 0; --i) {
            final WindowFrame w = this.windows.get(i);
            w.update();
        }
        this.getLumux().getInput().update();
    }

    public void render() {
        if(!gfxInitialized) return;

        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        currentCursor = Cursor.DEFAULT_CURSOR;

        if (this.getLumux().getInput().isButtonDown(1) || this.getLumux().getInput().isButtonDown(3)) {
            playSound(GameEngine.MOUSE_CLICK_ON, 0.2f);
        }
        if (this.getLumux().getInput().isButtonUp(1) || this.getLumux().getInput().isButtonUp(3)) {
            playSound(GameEngine.MOUSE_CLICK_OFF, 0.2f);
        }

        if (!this.getLumux().getInput().isButton(1)) {
            this.alreadyDragging = false;
            this.alreadyResizing = false;
        }

        g.setFont(GameEngine.font.deriveFont(20.0f));

        g.drawImage(Textures.background, 0, 0, getLumux().width, getLumux().height, null);

        g.setStroke(new BasicStroke(2.0f));
        //select window
        for (int i = 0; i < this.windows.size(); ++i) {
            WindowFrame w = this.windows.get(i);
            if (this.getLumux().getInput().isButtonDown(1)/* && !w.minimized*/ && this.mouseInWindow(i) && !this.mouseInWindow(0)) {
                WindowFrame tmpW = w;
                this.windows.remove(i);
                this.windows.add(0, w);
            }
        }

        //render window
        for (int i = this.windows.size() - 1; i >= 0; --i) {
            WindowFrame w = this.windows.get(i);
            w.setIndex(i);
            w.render();
        }

        //DEBUG GUI
        g.setColor(Color.WHITE);
        g.drawString("Frame Rate: " + getLumux().getCanvas().getFPS(), 5, 22);

        this.getLumux().getCanvas().setCursor(new Cursor(currentCursor));
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

    public void keyPressed(final KeyEvent e) {
        if (this.windows.size() > 0) {
            this.windows.get(0).keyPressed(e);
        }
    }

    public void keyReleased(final KeyEvent e) {
        if (this.windows.size() > 0) {
            this.windows.get(0).keyReleased(e);
        }
    }

    public void keyTyped(final KeyEvent e) {
        if (this.windows.size() > 0) {
            this.windows.get(0).keyTyped(e);
        }
    }

    //SOUNDS
    public Clip loadSound(String snd) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(LumuxOS.class.getClassLoader().getResourceAsStream(snd));
            Clip c = AudioSystem.getClip();
            c.open(audioStream);
            return c;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void playSound(Clip sndClip, float vol) {
        if(sndClip == null) {
            System.out.println("Sound null");
            return;
        }
        try {
            sndClip.setFramePosition(0);
            final FloatControl gainControl = (FloatControl)sndClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20.0f * (float)Math.log10(vol));
            sndClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSound(Clip sndClip) {
        playSound(sndClip, 1.0f);
    }

    public void cText(final String t, final int x, final int y) {
        this.cText(this.g, t, x, y);
    }

    public void cText(final Graphics2D g, final String t, final int x, final int y) {
        g.drawString(t, x - g.getFontMetrics().stringWidth(t) / 2, y);
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

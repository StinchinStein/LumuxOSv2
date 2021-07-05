package com.raffertysoftware.lumux;

import com.raffertysoftware.lumux.gui.GUISnap;
import com.raffertysoftware.lumux.gui.WindowRenderer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class WinButton {
    public String text;

    public int x, y, w, h;
    private int size;
    public boolean enabled = true;
    private WindowFrame win;
    private OnClickEvent clickEvent;
    private BufferedImage image;
    public WinButton(WindowFrame win, String text, int x, int y, int w, int h, int size) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.size = size;
        this.win = win;
    }
    public WinButton(WindowFrame win, BufferedImage img, int x, int y, int w, int h, int size) {
        this.image = img;
        this.text = "";
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.size = size;
        this.win = win;
    }

    public void onClick(OnClickEvent evt) {
        this.clickEvent = evt;
    }
    public void render() {
        Input input = this.win.ge.getLumux().getInput();
        Graphics2D g = this.win.ge.getGraphics();
        g.setColor(Color.BLACK);
        if(this.image == null) g.drawString(this.text, this.x, this.y);

        final AffineTransform t = g.getTransform();
        g.setColor(new Color(0, 0, 0, 50));
        if (this.win.mouseInArea(this.x, this.y, this.w, this.h) && this.win.ge.mouseOnlyInWindow(this.win) && this.enabled) {
            g.fillRect(x, y, w, h);
            g.setColor(new Color(0, 0, 0, 100));
        }
        g.drawRect(x, y, w, h);
        if(image != null) g.drawImage(image, x + w / 2 - size / 2, y + h / 2 - size / 2 + 1, size, size, null);
        g.setTransform(t);

        if(this.win.mouseInArea(this.x, this.y, this.w, this.h) && this.win.ge.mouseOnlyInWindow(this.win)) {
            this.win.isOverButton = true;
            if(input.isButtonUp(1)) {
                if(this.clickEvent != null) {
                    clickEvent.onClick(input.getButton(), input.getMouseX(), input.getMouseY());
                }
            }
        }
    }
}

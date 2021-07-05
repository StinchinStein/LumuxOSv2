// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.gui;

import com.raffertysoftware.lumux.LumuxOS;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GUIInputBox extends GUIComponent
{
    public String text;
    public String placeholder;
    public boolean isFocused;
    
    public GUIInputBox(WindowRenderer renderer, int x, int y, int w, int h) {
        super(renderer, GUISnap.TOPLEFT, x, y, w, h);
        this.text = "";
        this.placeholder = "";
        this.isFocused = false;
        this.placeholder = "Username...";
    }
    
    @Override
    public void render() {
        final Graphics2D g = this.renderer.parent.ge.getGraphics();
        g.setColor(this.foregroundClr);
        final FontMetrics m = this.renderer.parent.ge.getGraphics().getFontMetrics();
        if (this.text.length() == 0) {
            this.renderer.parent.ge.setColor(120, 120, 120, 255);
            g.drawString(this.placeholder, (float)(this.getX() + 4), this.getY() + m.getAscent() + 1.0f);
        }
        else {
            if (this.isFocused) {
                this.renderer.parent.ge.setColor(0, 0, 0, 255);
            }
            else {
                this.renderer.parent.ge.setColor(160, 160, 160, 200);
            }
            g.drawString(String.valueOf(this.text) + (this.isFocused ? ((this.renderer.parent.ge.tickCount % 30 > 15) ? "|" : "") : ""), (float)(this.getX() + 4), this.getY() + m.getAscent() + 1.0f);
        }
        if (this.isFocused) {
            this.renderer.parent.ge.setColor(60, 60, 60, 255);
        }
        else {
            this.renderer.parent.ge.setColor(120, 120, 120, 255);
        }
        g.drawRect(this.getX(), (int)this.getY(), this.w, this.h);

        if(this.renderer.parent.mouseInArea(this.getX(), (int)this.getY(), this.w, this.h)) {
        	this.renderer.parent.ge.setCursor(Cursor.TEXT_CURSOR);
        }
        if (this.renderer.parent.ge.getLumux().getInput().isButtonDown(1) && this.renderer.parent.mouseInArea(this.getX(), (int)this.getY(), this.w, this.h)) {
            this.isFocused = true;
        }
        else if (this.renderer.parent.ge.getLumux().getInput().isButtonDown(1)) {
            this.isFocused = false;
        }
    }
    
    public void setText(final String txt) {
        this.text = txt;
        this.w = this.renderer.parent.ge.getGraphics().getFontMetrics().stringWidth(this.text);
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        if (this.isFocused) {
            if (e.getKeyCode() == 10) {
                this.text = "";
            }
            else if (e.getKeyCode() == 8) {
                if (this.text.length() > 0) {
                    this.text = this.text.substring(0, this.text.length() - 1);
                }
            }
            else if (e.getKeyCode() == 32) {
                this.text = String.valueOf(this.text) + " ";
            }
            else if (KeyEvent.getKeyText(e.getKeyCode()).length() == 1 && !e.isControlDown()) {
                this.text = String.valueOf(this.text) + e.getKeyChar();
            }
        }
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
    }
}

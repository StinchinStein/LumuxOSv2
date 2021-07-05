// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.gui;

import com.raffertysoftware.lumux.WindowFrame;

import java.awt.*;

public class GUISlider extends GUIComponent
{
    private float sliderVal;
    private boolean draggingSlider;
    private String title;
    
    public GUISlider(WindowRenderer renderer, GUISnap snap, int x, int y, int w, int h) {
        super(renderer, snap, x, y, w, h);
        this.sliderVal = 0.0f;
        this.draggingSlider = false;
        this.title = "";
    }
    
    public GUISlider(WindowRenderer renderer, int x, int y, int w, int h) {
        super(renderer, GUISnap.TOPLEFT, x, y, w, h);
        this.sliderVal = 0.0f;
        this.draggingSlider = false;
        this.title = "";
    }
    
    @Override
    public void render() {
        Graphics2D g = this.renderer.parent.ge.getGraphics();
        WindowFrame w2 = this.renderer.parent;
        w2.ge.setColor(120, 120, 190, 255);
        g.fillRect(this.x, this.y, this.w, this.h);
        w2.ge.setColor(70, 70, 80, 255);
        if (w2.mouseInArea(this.x, this.y, this.w, this.h)) {
            w2.ge.setColor(90, 90, 90, 255);
            if (w2.ge.getLumux().getInput().isButton(1)) {
                this.draggingSlider = true;
            }
        }
        if (this.draggingSlider) {
            this.sliderVal = (w2.getAPI().getMouseY() - (float)this.y) / this.h;
            if (this.sliderVal > 1.0f) {
                this.sliderVal = 1.0f;
            }
            if (this.sliderVal < 0.0f) {
                this.sliderVal = 0.0f;
            }
            this.listener.sliderChange(this.sliderVal);
            if (w2.ge.getLumux().getInput().isButtonUp(1)) {
                this.listener.sliderComplete(this.sliderVal);
                this.draggingSlider = false;
            }
        }
        g.fillRect(this.x, this.y, this.w, (int)(this.sliderVal * this.h));
        w2.ge.setColor(90, 90, 210, 255);
        g.fillRect(this.x - 4, this.y + (int)(this.sliderVal * this.h) - 2, this.w + 8, 5);
        w2.ge.setColor(0, 0, 0, 255);
        g.setFont(g.getFont().deriveFont(10.0f));
        if (this.title.length() > 6) {
            w2.ge.cText(String.valueOf(this.title.substring(0, 6)) + "...", this.x + this.w / 2, this.y - 20);
        }
        else {
            w2.ge.cText(this.title, this.x + this.w / 2, this.y - 20);
        }
        w2.ge.cText(String.valueOf(Math.round((1.0f - this.getValue()) * 100.0f)) + "%", this.x + this.w / 2, this.y - 7);
    }

    public void setTitle(final String title) {
        this.title = title;
    }
    
    public float getValue() {
        return this.sliderVal;
    }
    
    public void setValue(final float val) {
        this.sliderVal = val;
    }
}

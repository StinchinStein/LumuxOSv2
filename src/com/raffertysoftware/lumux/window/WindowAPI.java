// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.window;

import com.raffertysoftware.lumux.WindowFrame;

import java.awt.*;

public class WindowAPI
{
    public WindowFrame window;
    private Graphics2D g;
    private int offX;
    private int offY;
    private int offW;
    private int offH;
    private int defOffX;
    private int defOffY;
    private int defOffW;
    private int defOffH;
    
    public WindowAPI(WindowFrame window) {
        this.window = window;
        this.setInitialGraphicsBounds(this.offX = 7, this.offY = 27, this.offW = 14, this.offH = 34);
    }
    
    public void initialization() {
        this.g = this.window.ge.getGraphics();
    }
    
    public void onClose() {
    }
    
    public void update() {
    }
    
    public void draw() {
        if (this.g != null) {
            this.window.ge.setColor(255);
            this.g.drawRect(this.getMouseX() - 20, this.getMouseY() - 20, 40, 40);
        }
    }
    
    public int getMouseX() {
        return this.window.ge.getLumux().getInput().getMouseX() - this.window.x - this.offX;
    }
    
    public int getMouseY() {
        return this.window.ge.getLumux().getInput().getMouseY() - this.window.y - this.offY;
    }
    
    public void setGraphicsBounds(final int offX, final int offY, final int offW, final int offH) {
        this.offX = offX;
        this.offY = offY;
        this.offW = offW;
        this.offH = offH;
    }
    
    public void setInitialGraphicsBounds(final int offX, final int offY, final int offW, final int offH) {
        this.setGraphicsBounds(this.defOffX = offX, this.defOffY = offY, this.defOffW = offW, this.defOffH = offH);
    }
    
    public int getOffX() {
        return this.offX;
    }
    
    public int getOffY() {
        return this.offY;
    }
    
    public int getOffW() {
        return this.offW;
    }
    
    public int getOffH() {
        return this.offH;
    }
    
    public Graphics2D getGraphics() {
        return this.g;
    }
    
    public int getWidth() {
        return this.window.w - this.getOffW() + 2;
    }
    
    public int getHeight() {
        return this.window.h - this.getOffH() + 2;
    }
    
    public void resetGraphicsBounds() {
        this.setGraphicsBounds(this.defOffX, this.defOffY, this.defOffW, this.defOffH);
    }
}

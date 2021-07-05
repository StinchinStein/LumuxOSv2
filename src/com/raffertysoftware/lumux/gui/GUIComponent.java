// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GUIComponent {
    public GUIComponent parent;
    public int x;
    public int y;
    public int w;
    public int h;
    public GUISnap snap;
    public boolean isVisible;
    public Color backgroundClr;
    public Color foregroundClr;
    public ArrayList<GUIComponent> COMPONENTS;
    public WindowRenderer renderer;
    public OnEventListener listener;
    
    public GUIComponent(WindowRenderer renderer, GUISnap snap, int x, int y, int w, int h) {
        this.isVisible = true;
        this.backgroundClr = Color.WHITE;
        this.foregroundClr = Color.BLACK;
        this.COMPONENTS = new ArrayList<GUIComponent>();
        this.renderer = renderer;
        this.snap = snap;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public GUIComponent(WindowRenderer renderer, int x, int y, int w, int h) {
        this.isVisible = true;
        this.backgroundClr = Color.WHITE;
        this.foregroundClr = Color.BLACK;
        this.COMPONENTS = new ArrayList<GUIComponent>();
        this.snap = GUISnap.CENTER;
        this.renderer = renderer;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public GUIComponent(int x, int y, int w, int h) {
        this.isVisible = true;
        this.backgroundClr = Color.WHITE;
        this.foregroundClr = Color.BLACK;
        this.COMPONENTS = new ArrayList<GUIComponent>();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void hardRender() {
        if (this.isVisible) {
            this.render();
            for (final GUIComponent c : this.COMPONENTS) {
                c.render();
            }
        }
    }
    
    public void render() {
    }
    
    public void setListener(OnEventListener listener) {
        this.listener = listener;
    }
    
    public int getX() {
        float tX = 0.0f;
        if(this.renderer == null) return this.x;

        if (this.parent == null) {
            switch (this.snap) {
                case CENTER: {
                    tX = (float)(this.renderer.w / 2 - this.w / 2);
                    break;
                }
                case LEFT:
                case TOPLEFT:
                case BOTTOMLEFT: {
                    tX = (float)this.x;
                    break;
                }
                case RIGHT:
                case TOPRIGHT:
                case BOTTOMRIGHT: {
                    tX = (float)(this.renderer.w - this.x - this.w);
                    break;
                }
                case TOP: {
                    tX = (float)(this.renderer.w / 2 - this.x - this.w / 2);
                    break;
                }
                case BOTTOM: {
                    tX = (float)(this.renderer.w / 2 - this.x - this.w / 2);
                    break;
                }
            }
        }
        else {
            switch (this.snap) {
                case CENTER:
                case TOP:
                case BOTTOM: {
                    tX = (float)(this.parent.getX() + this.parent.w / 2 - this.w / 2);
                    break;
                }
                case LEFT:
                case TOPLEFT:
                case BOTTOMLEFT: {
                    tX = (float)(this.parent.getX() + this.x);
                    break;
                }
                case RIGHT:
                case TOPRIGHT:
                case BOTTOMRIGHT: {
                    tX = (float)(this.parent.getX() + this.parent.w - this.x - this.w);
                    break;
                }
            }
        }
        return (int)tX;
    }
    
    public float getY() {
        float tY = 0.0f;
        if(this.renderer == null) return this.y;

        if (this.parent == null) {
            switch (this.snap) {
                case CENTER:
                case LEFT:
                case RIGHT: {
                    tY = (float)(this.renderer.h / 2 - this.h / 2);
                    break;
                }
                case BOTTOM:
                case BOTTOMLEFT:
                case BOTTOMRIGHT: {
                    tY = (float)(this.renderer.h - this.y - this.h);
                    break;
                }
                case TOP:
                case TOPLEFT:
                case TOPRIGHT: {
                    tY = (float)this.y;
                    break;
                }
            }
        }
        else {
            switch (this.snap) {
                case CENTER:
                case LEFT:
                case RIGHT: {
                    tY = this.parent.getY() + this.parent.h / 2 - this.h / 2;
                    break;
                }
                case BOTTOM:
                case BOTTOMLEFT:
                case BOTTOMRIGHT: {
                    tY = this.parent.getY() + this.parent.h - this.y - this.h;
                    break;
                }
                case TOP:
                case TOPLEFT:
                case TOPRIGHT: {
                    tY = this.parent.getY() + this.y;
                    break;
                }
            }
        }
        return (float)(int)tY;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public void setWidth(final int w) {
        this.w = w;
    }
    
    public void setHeight(final int h) {
        this.h = h;
    }
    
    public void setSnap(GUISnap s) {
        this.snap = s;
    }
    
    public void setParent(final GUIComponent parent) {
        this.parent = parent;
    }
    
    public void setVisible(final boolean v) {
        this.isVisible = v;
    }
    
    public void setForeground(final Color c) {
        this.foregroundClr = c;
    }
    
    public void setBackground(final Color c) {
        this.backgroundClr = c;
    }
    
    public void add(final GUIComponent c) {
        c.setParent(this);
        this.COMPONENTS.add(c);
    }
    
    public void remove(final GUIComponent c) {
        this.COMPONENTS.remove(c);
    }
    
    public void keyPressed(final KeyEvent e) {
    }
    
    public void keyReleased(final KeyEvent e) {
    }
    
    public void keyTyped(final KeyEvent e) {
    }
}

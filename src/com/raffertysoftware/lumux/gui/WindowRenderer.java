// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.gui;

import com.raffertysoftware.lumux.WindowFrame;

import java.awt.*;
import java.util.ArrayList;

public class WindowRenderer {
    WindowFrame parent;
    public int x;
    public int y;
    public int w;
    public int h;
    public ArrayList<GUIComponent> COMPONENTS;
    
    public WindowRenderer(final WindowFrame parent) {
        this.COMPONENTS = new ArrayList<GUIComponent>();
        this.parent = parent;
        this.x = 0;
        this.y = 0;
        this.w = parent.getAPI().getWidth();
        this.h = parent.getAPI().getHeight();
    }
    
    public void draw() {
        final Graphics2D g = this.parent.ge.getGraphics();
        this.w = this.parent.getAPI().getWidth();
        this.h = this.parent.getAPI().getHeight();
        for (int i = 0; i < this.COMPONENTS.size(); ++i) {
            this.COMPONENTS.get(i).hardRender();
        }
    }
    
    public void update() {
    }
    
    public void add(GUIComponent c) {
        this.COMPONENTS.add(c);
    }
    
    public void add(ArrayList<GUIComponent> messages) {
        this.COMPONENTS.addAll(messages);
    }
}

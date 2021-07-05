// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.gui;

import java.awt.*;

public class GUIButton extends GUIComponent {
    public String text;
    
    public GUIButton(WindowRenderer renderer, String text, GUISnap snap, int x, int y, int w, int h) {
        super(renderer, snap, x, y, w, h);
        this.text = "";
        this.text = text;
    }

    public GUIButton(WindowRenderer renderer, String text, int x, int y, int w, int h) {
        super(renderer, GUISnap.TOPLEFT, x, y, w, h);
        this.text = "";
        this.text = text;
    }
    
    @Override
    public void render() {
        Graphics2D g = this.renderer.parent.ge.getGraphics();
        g.setColor(Color.BLACK);
        g.drawString(this.text, this.x, this.y);
    }
}

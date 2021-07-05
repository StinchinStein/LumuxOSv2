// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.gui;

import java.awt.*;

public class GUIPanel extends GUIComponent
{
    public GUIPanel(WindowRenderer renderer, GUISnap snap, int x, int y, int w, int h) {
        super(renderer, snap, x, y, w, h);
    }
    
    public GUIPanel(WindowRenderer renderer, int x, int y, int w, int h) {
        super(renderer, GUISnap.TOPLEFT, x, y, w, h);
    }
    
    @Override
    public void render() {
        final Graphics2D g = this.renderer.parent.ge.getGraphics();
        g.setColor(this.backgroundClr);
        g.fillRect(this.x, this.y, this.w, this.h);
    }
}

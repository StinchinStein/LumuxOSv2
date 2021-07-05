// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux.gui;

import java.awt.*;

public class GUIText extends GUIComponent
{
    public String text;
    
    public GUIText(WindowRenderer renderer, String text, int x, int y) {
        super(renderer, GUISnap.TOPLEFT, x, y, renderer.parent.ge.getGraphics().getFontMetrics().stringWidth(text), renderer.parent.ge.getGraphics().getFontMetrics().getAscent());
        this.text = "";
        this.text = text;
    }
    
    @Override
    public void render() {
        final Graphics2D g = this.renderer.parent.ge.getGraphics();
        g.setColor(this.foregroundClr);
        final FontMetrics m = this.renderer.parent.ge.getGraphics().getFontMetrics();
        g.drawString(this.text, (float)this.getX(), this.getY() + m.getAscent());
    }
    
    public void setText(final String txt) {
        this.text = txt;
        this.w = this.renderer.parent.ge.getGraphics().getFontMetrics().stringWidth(this.text);
    }
}

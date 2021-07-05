package com.raffertysoftware.lumux.gui;


public interface OnEventListener {
    public void sliderChange(final float val);
    public void sliderComplete(final float val);
    public void actionPerformed();
}

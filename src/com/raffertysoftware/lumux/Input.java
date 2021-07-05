// 
// Decompiled by Procyon v0.5.36
// 

package com.raffertysoftware.lumux;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener  {
    private LumuxOS client;
    private final int NUM_KEYS = 256;
    private boolean[] keys = new boolean[65535];
    private boolean[] keysLast = new boolean[65535];
    private final int NUM_BUTTONS = 256;
    private boolean[] buttons = new boolean[256];
    private boolean[] buttonsLast = new boolean[256];
    private int mouseX = 0;
    private int mouseY = 0;
    private int clickMouseX;
    private int clickMouseY;
    private int pmouseX;
    private int pmouseY;
    private int scroll = 0;
    private boolean mouseDragged = false;
    private int dblTimer = 0;
    private int dblClick = 0;
    public Input(LumuxOS client) {
        System.out.println("[Input] Loading...");
        this.client = client;
        client.addKeyListener(this);
        client.getCanvas().addMouseMotionListener(this);
        client.getCanvas().addMouseListener(this);
        client.getCanvas().addMouseWheelListener(this);
        System.out.println("[Input] Loaded!");
    }
    
    public void update() {
        for (int i = 0; i < 256; ++i) {
            this.keysLast[i] = this.keys[i];
        }
    }
    
    public void updateMouse() {
        this.scroll = 0;
        if (!this.isButton(1) && !this.isButton(3)) {
            this.mouseDragged = false;
        }
        for (int i = 0; i < 256; ++i) {
            this.buttonsLast[i] = this.buttons[i];
        }
        this.pmouseX = this.mouseX;
        this.pmouseY = this.mouseY;
        
        if(dblClick > 0) {
        	dblTimer++;
        	if(dblTimer > 500 / client.getGameEngine().getRenderDelta()) {
        		dblClick = 0;
        		dblTimer = 0;
        	}
        } else {
        	dblTimer = 0;
        }
    }
    
    public boolean isKey(final int keyCode) {
        return this.keys[keyCode];
    }
    
    public boolean isKeyUp(final int keyCode) {
        return !this.keys[keyCode] && this.keysLast[keyCode];
    }
    
    public boolean isKeyDown(final int keyCode) {
        return this.keys[keyCode] && !this.keysLast[keyCode];
    }
    
    public boolean isButton(final int button) {
        return this.buttons[button];
    }
    
    public boolean isButtonUp(final int button) {
        return !this.buttons[button] && this.buttonsLast[button];
    }
    
    public boolean isButtonDown(final int button) {
        return this.buttons[button] && !this.buttonsLast[button];
    }
    
    @Override
    public void mouseWheelMoved(final MouseWheelEvent e) {
        this.scroll = e.getWheelRotation();
    }
    
    @Override
    public void mouseDragged(final MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
        this.mouseDragged = true;
    }
    
    @Override
    public void mouseMoved(final MouseEvent e) {
        this.mouseX = e.getX();
        this.mouseY = e.getY();
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
        this.clickMouseX = e.getX();
        this.clickMouseY = e.getY();
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
        this.buttons[e.getButton()] = true;
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
        this.buttons[e.getButton()] = false;
    }
    
    @Override
    public void keyPressed(final KeyEvent e) {
        this.keys[e.getKeyCode()] = true;
        this.client.getGameEngine().keyPressed(e);
    }
    
    @Override
    public void keyReleased(final KeyEvent e) {
        this.keys[e.getKeyCode()] = false;
        this.client.getGameEngine().keyReleased(e);
    }
    
    @Override
    public void keyTyped(final KeyEvent e) {
        this.client.getGameEngine().keyTyped(e);
    }
    
    public int getMouseX() {
        return this.mouseX;
    }
    
    public int getMouseY() {
        return this.mouseY;
    }
    
    public int getPMouseX() {
        return this.pmouseX;
    }
    
    public int getPMouseY() {
        return this.pmouseY;
    }
    
    public int getScroll() {
        return this.scroll;
    }
    
    public boolean isMouseDragged() {
        return this.mouseDragged;
    }

	public void simulateMouse(int i, boolean b) {
		this.updateMouse();
		this.buttons[i] = b;
	}

	
	public boolean isDoubleClick(int i) {
		if(isButtonDown(i)) {
			dblClick++;
		}
		if(dblClick >= 2) {
			dblClick = 0;
			dblTimer = 0;
			return true;
		}
		return false;
	}

	public void forceMousePosition(int x, int y) {
		this.mouseX = x;
		this.mouseY = y;
	}

	public void forceButtonUp(int i) {
		this.buttons[i] = false;
		//this.buttonsLast[i] = false;
		
	}

    public int getButton() {
        int selButton = -1;
        for(int i = 0; i < this.buttons.length; i++) {
            if(this.buttons[i]) {
                selButton = i;
            }
        }

        return selButton;
    }
}

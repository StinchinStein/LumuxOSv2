package com.raffertysoftware.lumux;

import com.raffertysoftware.lumux.gui.GUIButton;
import com.raffertysoftware.lumux.gui.WindowRenderer;
import com.raffertysoftware.lumux.window.WindowAPI;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class WindowFrame {

    public int x, y, w, h;
    public int minW = 205;
    public int minH = 60;
    public int resX, resY; //extended x/y when window is dragged too small/large
    public int index;

    public GameEngine ge;
    public WindowAPI api;
    public WindowRenderer renderer;

    private WinButton btnClose, btnMaximize, btnMinimize;
    public boolean dragging = false;
    public boolean resizeX;
    public boolean resizeY;
    public boolean resizeW;
    public boolean resizeH;
    public boolean drawWindowTitle = true; //does nothing?
    public boolean isOverButton = false;

    public WindowFrame(GameEngine ge, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ge = ge;
        this.api = new WindowAPI(this);
        this.renderer = new WindowRenderer(this);
        this.index = ge.windows.size();

        this.btnClose = new WinButton(this, Textures.iconClose, this.w - 35, 5, 30, 15, 12);
        this.btnClose.onClick((btn, mouseX, mouseY) -> {
            this.ge.windows.remove(this);
        });
        this.initialize();
    }

    public void initialize() {

    }

    public void onResize() {
        btnClose.x = this.w - 35; //set x when resizing
    }
    protected void update() {

    }
    public void tick() {
        this.renderer.update();
        this.update();
    }

    protected void render() {
        Graphics2D g = this.ge.getGraphics();
        Input input = this.ge.getLumux().getInput();
        isOverButton = false;

        if (input.isButtonUp(1)) {
            if (this.y < 0) {
                this.y = 0;
            }
            this.dragging = false;
            this.resizeX = false;
            this.resizeY = false;
            this.resizeW = false;
            this.resizeH = false;
            this.resX = 0;
            this.resY = 0;
        }

        if(this.resizeW && this.resizeH) {
            this.ge.setCursor(Cursor.SE_RESIZE_CURSOR);
        } else if(this.resizeW || this.resizeX) {
            this.ge.setCursor(Cursor.E_RESIZE_CURSOR);
        } else if(this.resizeH || this.resizeY) {
            this.ge.setCursor(Cursor.S_RESIZE_CURSOR);
        }
        //Shadow
        /*this.ge.setColor(0, 0, 0, 20);
        g.fillRect(this.x + this.w, this.y, 4, this.h);
        g.fillRect(this.x - 4, this.y, 4, this.h);
        g.fillRect(this.x - 4, this.y + this.h, this.w + 8, 4);
        g.fillRect(this.x - 4, this.y - 4, this.w + 8, 4);*/

        //Border Frame
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(this.x, this.y, this.w, this.h, 10, 10);
        RoundRectangle2D roundedRectangle2 = new RoundRectangle2D.Float(this.x+1, this.y+1, this.w-2, this.h-2, 10, 10);
        g.setColor(new Color(0, 0, 0, 60));
        if(this.index == 0) g.setColor(new Color(0, 0, 0, 120));
        g.fill(roundedRectangle);
        this.ge.setColor(0, 0, 0, 50);
        g.draw(roundedRectangle2);

        AffineTransform titleTransform = g.getTransform();
        g.translate(this.x, this.y);
        this.btnClose.render();
        g.setTransform(titleTransform);

        //Content Render
        this.ge.setColor(255, 255, 255, 255);
        g.fillRect(this.x + this.getAPI().getOffX() - 2, this.y + this.getAPI().getOffY() - 2, this.w - this.getAPI().getOffW() + 4, this.h - this.getAPI().getOffH() + 4);
        //bevel
        this.ge.setColor(new Color(200, 200, 200));
        g.drawRect(this.x + this.getAPI().getOffX() - 2, this.y + this.getAPI().getOffY() - 2, this.w - this.getAPI().getOffW() + 4, this.h - this.getAPI().getOffH() + 4);

        //RENDER CONTENT
        final Shape c3 = g.getClip();
        final AffineTransform t3 = g.getTransform();
        g.clipRect(this.x + this.getAPI().getOffX() - 1, this.y + this.getAPI().getOffY() - 1, this.getAPI().getWidth(), this.getAPI().getHeight());
        g.translate(this.x + this.getAPI().getOffX() - 1, this.y + this.getAPI().getOffY() - 1);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getAPI().getWidth(), this.getAPI().getHeight());
        this.renderer.draw();
        this.draw();
        g.setTransform(t3);
        g.setClip(c3);

        //END CONTENT

        //this.ge.setColor(0, 0, 0, 120);
        if(this.ge.mouseOnlyInWindow(this) && !isOverButton) {
            //dragging
            if (this.drawWindowTitle && this.ge.getLumux().getInput().isButtonDown(1) && this.ge.mouseInArea(this.x, this.y, this.w, this.getAPI().getOffY() - 2) && !this.ge.alreadyDragging && !this.ge.alreadyResizing) {
                this.dragging = true;
                this.ge.alreadyDragging = true;
            }

            //resizing
            if (this.ge.mouseInArea(this.x + this.w - 4, this.y + this.h - 4, 4, 4) && !this.ge.alreadyResizing && !this.ge.alreadyDragging) {
                //g.fillRect(this.x + this.w, this.y + this.h, 4, 4);
                //g.fillRect(this.x + this.w, this.y, 4, this.h);
                //g.fillRect(this.x, this.y + this.h, this.w, 4);
                this.ge.setCursor(Cursor.SE_RESIZE_CURSOR);
                if (this.ge.getLumux().getInput().isButtonDown(1)) {
                    this.resizeW = true;
                    this.resizeH = true;
                    this.ge.alreadyResizing = true;
                }
            } else if (this.ge.mouseInArea(this.x + this.w - 6, this.y, 6, this.h-4) && !this.ge.alreadyResizing && !this.ge.alreadyDragging) {
                //g.fillRect(this.x + this.w, this.y, 4, this.h);
                this.ge.setCursor(Cursor.E_RESIZE_CURSOR);
                if (this.ge.getLumux().getInput().isButtonDown(1)) {
                    this.resizeW = true;
                    this.ge.alreadyResizing = true;
                }
            } else if (this.ge.mouseInArea(this.x, this.y + this.h - 6, this.w-4, 6) && !this.ge.alreadyResizing && !this.ge.alreadyDragging) {
                //g.fillRect(this.x, this.y + this.h, this.w, 4);
                this.ge.setCursor(Cursor.S_RESIZE_CURSOR);
                if (this.ge.getLumux().getInput().isButtonDown(1)) {
                    this.resizeH = true;
                    this.ge.alreadyResizing = true;
                }
            }
        }

        if (this.w <= this.minW) {
            this.resX = this.x + this.w - input.getMouseX();
        }
        if (this.h <= this.minH) {
            this.resY = this.y + this.h - input.getMouseY();
        }
        if (this.resizeW && this.resX <= 0) {
            this.w += input.getMouseX() - input.getPMouseX();
        }
        if (this.resizeX && this.resY >= 0) {
            if (this.w > this.minW) {
                this.x += input.getMouseX() - input.getPMouseX();
            }
            this.w -= input.getMouseX() - input.getPMouseX();
        }
        if (this.resizeH && this.resY <= 0) {
            this.h += input.getMouseY() - input.getPMouseY();
        }
        if (this.w < this.minW) {
            this.w = this.minW;
        }
        if (this.h < this.minH) {
            this.h = this.minH;
        }
        if (this.resizeW || this.resizeH) {
            this.onResize();
        }

        if (this.dragging) {
            this.x += input.getMouseX() - input.getPMouseX();
            this.y += input.getMouseY() - input.getPMouseY();
        }
    }

    public void draw() {}

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(final KeyEvent e) {
    }

    public void keyTyped(final KeyEvent e) {
    }

    public boolean mouseInContentPane() {
        return this.mouseInContentArea(0, 0, this.w - this.getAPI().getOffW(), this.h - this.getAPI().getOffH());
    }

    public boolean mouseInContentArea(final int x, final int y, final int w, final int h) {
        final int mouseX = this.getAPI().getMouseX();
        final int mouseY = this.getAPI().getMouseY();
        return mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + h;
    }
    public boolean mouseInArea(final int x, final int y, final int w, final int h) {
        final int mouseX = this.getMouseX();
        final int mouseY = this.getMouseY();
        return mouseX >= x && mouseY >= y && mouseX <= x + w && mouseY <= y + h;
    }

    public WindowAPI getAPI() {
        return this.api;
    }

    //relative to the window
    public int getMouseX() {
        return this.ge.getLumux().getInput().getMouseX() - this.x;
    }

    public int getMouseY() {
        return this.ge.getLumux().getInput().getMouseY() - this.y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getW() {
        return w;
    }
    public int getH() {
        return h;
    }

    public void setIndex(int i) {
        this.index = i;
    }
}

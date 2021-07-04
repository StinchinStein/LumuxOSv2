package com.raffertysoftware.lumux;

import com.raffertysoftware.lumux.window.WindowAPI;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class WindowFrame {

    public int x, y, w, h;
    public int index;

    public GameEngine ge;
    public WindowAPI api;

    public boolean dragging = false;
    public boolean drawWindowTitle = true; //does nothing?

    public WindowFrame(GameEngine ge, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ge = ge;
        this.api = new WindowAPI(this);
        this.index = ge.windows.size();
    }

    public void tick() {

    }

    public void render() {
        Graphics2D g = this.ge.getGraphics();
        Input input = this.ge.getLumux().getInput();

        if (input.isButtonUp(1)) {
            if (this.y < 0) {
                this.y = 0;
            }
            this.dragging = false;
            /*this.resizeX = false;
            this.resizeY = false;
            this.resizeW = false;
            this.resizeH = false;
            this.resX = 0;
            this.resY = 0;*/
        }

        //Shadow
        /*this.ge.setColor(0, 0, 0, 20);
        g.fillRect(this.x + this.w, this.y, 4, this.h);
        g.fillRect(this.x - 4, this.y, 4, this.h);
        g.fillRect(this.x - 4, this.y + this.h, this.w + 8, 4);
        g.fillRect(this.x - 4, this.y - 4, this.w + 8, 4);*/

        //Border Frame
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(this.x, this.y, this.w, this.h, 10, 10);
        g.setColor(new Color(0, 0, 0, 60));
        g.fill(roundedRectangle);
        this.ge.setColor(0, 0, 0, 50);
        g.draw(roundedRectangle);

        //Content Render
        this.ge.setColor(255, 255, 255, 255);
        g.fillRect(this.x + this.getAPI().getOffX() - 2, this.y + this.getAPI().getOffY() - 2, this.w - this.getAPI().getOffW() + 4, this.h - this.getAPI().getOffH() + 4);
        //bevel
        this.ge.setColor(new Color(50, 50, 50));
        g.drawRect(this.x + this.getAPI().getOffX() - 2, this.y + this.getAPI().getOffY() - 2, this.w - this.getAPI().getOffW() + 4, this.h - this.getAPI().getOffH() + 4);


        //resize func
        if (this.drawWindowTitle && this.ge.getLumux().getInput().isButtonDown(1) && this.ge.mouseInArea(this.x, this.y, this.w, this.getAPI().getOffY() - 2) && !this.ge.alreadyDragging && !this.ge.alreadyResizing) {
            this.dragging = true;
            this.ge.alreadyDragging = true;
        }


        if (this.dragging) {
            this.x += input.getMouseX() - input.getPMouseX();
            this.y += input.getMouseY() - input.getPMouseY();
        }
    }


    public WindowAPI getAPI() {
        return this.api;
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

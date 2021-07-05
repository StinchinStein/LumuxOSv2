package com.raffertysoftware.lumux;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class LumuxOS extends JFrame implements Runnable {

    private boolean drawCanvas = true;
    private boolean running = false;
    public double rDelta;
    public int gameLoopTimer = 0;

    public int width, height, pWidth, pHeight;

    public GameEngine ge;
    public Input input;

    private boolean initRender = false;
    private GraphicsEngine gfxCanvas;

    private Thread thread;
    public LumuxOS() {
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("sun.java2d.dpiaware", "false");
        System.out.println("OpenGL Enabled: " + System.getProperty("sun.java2d.opengl"));
        this.width = 960;
        this.height = 540;

        new Textures();
        this.ge = new GameEngine(this);

        //Initialize JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setMinimumSize(new Dimension(this.width, this.height));
        gfxCanvas = new GraphicsEngine(this);
        gfxCanvas.setBackground(Color.BLACK);
        this.add(gfxCanvas);
        this.setSize(this.width, this.height);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setTitle("LumuxOS v2");
        this.setVisible(true);

        this.input = new Input(this);

        //Call after all the other initializations
        this.ge.onInitializeOS();

        thread = new Thread(this);
        thread.start();
        this.running = true;
    }

    public void tick(double dt) {
        this.drawCanvas = true;
        this.ge.tick(dt);
        this.getInput().update();
    }

    public void render() {
        if(this.ge.getGraphics() != null) {
            this.ge.render();
            this.getInput().updateMouse();
        }
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0.0;
        final double ns = 50000000.0;
        long rLastTime = System.nanoTime();

        System.out.println("Running LumuxOS...");
        //recheckHz();
        while (this.running) {
            double nsTick = 1000000000.0 / 20.0; //tickrate

            final long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1.0) {
                ge.renderDelta = delta;
                this.tick(delta);
                this.gameLoopTimer++;
                delta--;
            }
            this.rDelta = (now - rLastTime) / 1000000.0;
            rLastTime = now;

            this.width = this.getWidth();
            this.height = this.getHeight();

            if (this.pWidth != this.width || this.pHeight != this.height) {
                this.drawCanvas = false;
                this.ge.onResize(this.pWidth, this.pHeight, this.width, this.height);
                //recheckHz();
                this.pWidth = this.width;
                this.pHeight = this.height;
            }
            if(this.drawCanvas) {
                if(!initRender) {
                    this.ge.onInitializeGFX();
                    initRender = true;

                }
            }
            gfxCanvas.repaint();
        }
    }


    public static void main(String[] args) {
        new LumuxOS();
    }

    public GraphicsEngine getCanvas() {
        return gfxCanvas;
    }

    public GameEngine getGameEngine() {
        return ge;
    }

    public Input getInput() {
        return input;
    }
}

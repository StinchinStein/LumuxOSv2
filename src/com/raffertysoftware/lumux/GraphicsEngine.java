package com.raffertysoftware.lumux;

import javax.swing.*;
import java.awt.*;

public class GraphicsEngine extends JPanel {

    private int fps;
    private int frames;
    private long fpsTimer = System.currentTimeMillis();

    private LumuxOS os;
    public GraphicsEngine(LumuxOS os) {
        this.os = os;
    }
    public void paintComponent(Graphics g2) {
        super.paintComponent(g2);
        this.os.getGameEngine().setGraphicsObject((Graphics2D) g2);
        this.os.render();

        this.frames++;
        if (System.currentTimeMillis() - this.fpsTimer >= 1000L) {
            this.fps = this.frames;
            this.frames = 0;
            this.fpsTimer += 1000L;
        }

    }

    public int getFPS() {
        return fps;
    }
}

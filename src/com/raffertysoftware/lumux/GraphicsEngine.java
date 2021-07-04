package com.raffertysoftware.lumux;

import javax.swing.*;
import java.awt.*;

public class GraphicsEngine extends JPanel {

    private LumuxOS os;
    public GraphicsEngine(LumuxOS os) {
        this.os = os;
    }
    public void paint(Graphics g2) {
        super.paint(g2);
        this.os.getGameEngine().setGraphicsObject((Graphics2D) g2);
        this.os.render();
    }
}

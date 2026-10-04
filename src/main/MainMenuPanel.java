package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MainMenuPanel extends JPanel
{
    public final int screenWidth = 1280;
    public final int screenHeight = 720;
    private BufferedImage backgroundImage;

    public MainMenuPanel(String image) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(Main.class.getResource(image)));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading background image: " + e.getMessage());
            this.setBackground(Color.BLACK);
            backgroundImage = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

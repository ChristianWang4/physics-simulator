package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class EnergySimulationPanel_Spring extends JPanel implements Runnable
{
    // SCREEN SETTINGS
    public final int screenWidth = 1280;
    public final int screenHeight = 720;
    public final int FPS = 60;
    public BufferedImage backgroundImage;

    // MISCELLANEOUS
    public Thread gameThread = null;
    public volatile boolean isThreadRunning = false;
    public boolean isRunning = false;

    // SPRING PHYSICS
    double mass = 10;
    double acceleration = 0;
    double velocity = 0;
    double springConstant = 0.01;
    double friction = 0.999;

    double startingMass = 10;
    double startingSpringConstant = 0.01;
    double startingFriction = 1;
    double startingPosition = 500;

    final double equilibriumPosition = 300;
    double currentPosition = 500;
    double deltax = equilibriumPosition - currentPosition;

    final int blockWidth = 80;
    final int blockHeight = 80;
    double springWidth = startingPosition;
    final double springHeight = 200;
    double midY = springHeight / 2;
    final int numOfWaves = 6;

    // ENERGY TRACKING
    double springPotentialEnergy = 0;
    double kineticEnergy = 0;
    double totalEnergy = 0;
    double startingTotalEnergy = 0;
    double work = 0;
    final double barHeight = 245;
    boolean hasStoredStartingEnergy = false;
    double heightScale = barHeight / (0.5 * springConstant * Math.pow(deltax, 2));


    public EnergySimulationPanel_Spring(String image)
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // 1280x720
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

        isThreadRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null)
        {
            final double drawInterval = 1000000000.0/FPS; // 0.016666 seconds per frame
            double nextDrawTime = System.nanoTime() + drawInterval;
            while (isThreadRunning) {
                // 1. UPDATE: update information such as character positions
                update();

                // 2. DRAW: draw the screen w/ updated information
                repaint();
                try {
                    double remainingTime = nextDrawTime - System.nanoTime();

                    if (remainingTime < 0)
                        remainingTime = 0;

                    Thread.sleep((long) remainingTime / 1000000);
                    nextDrawTime += drawInterval;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }

    public void setSpringConstant(double springConstant)
    {
        this.springConstant = springConstant;
        startingSpringConstant = springConstant;
        springPotentialEnergy = 0.5 * springConstant * Math.pow(deltax, 2);
        heightScale = barHeight / springPotentialEnergy;

    }
    public void setMass(double mass)
    {
        this.mass = mass;
        startingMass = mass;
    }
    public void setFriction(double friction)
    {
        friction = 1 - (friction * 0.004);
        this.friction = friction;
        startingFriction = friction;
    }

    public void restart()
    {
        this.mass = startingMass;
        this.springConstant = startingSpringConstant;
        this.friction = startingFriction;
        acceleration = 0;
        velocity = 0;
        currentPosition = startingPosition;
        deltax = equilibriumPosition - currentPosition;
        springWidth = startingPosition;

        kineticEnergy = 0;
        springPotentialEnergy = 0.5 * springConstant * Math.pow(deltax, 2);
        heightScale = barHeight / springPotentialEnergy;
        hasStoredStartingEnergy = false;
        work = 0;
        startingTotalEnergy = 0;
    }


    public void update()
    {
        if (isRunning)
        {
            deltax = currentPosition - equilibriumPosition;
            acceleration = -(springConstant / mass) * deltax;

            velocity += acceleration;
            velocity *= friction;
            currentPosition += velocity;

            springWidth = currentPosition;

            // Energy Tracking
            springPotentialEnergy = 0.5 * springConstant * Math.pow(deltax, 2);
            kineticEnergy = 0.5 * mass * Math.pow(velocity, 2);
            totalEnergy = springPotentialEnergy + kineticEnergy;

            // Store Starting Total Energy
            if (!hasStoredStartingEnergy) {
                startingTotalEnergy = totalEnergy;
                hasStoredStartingEnergy = true;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        Graphics2D g2 = (Graphics2D) g;


        // Draw Spring
        g2.setColor(Color.lightGray);
        g2.setStroke(new BasicStroke(8));

        int startX = 30;
        int endX = (int) springWidth;

        double totalLength = springWidth - startX;

        double amplitude = 30;

        for (int x = startX; x < endX; x++)
        {
            // normalize position along spring: 0 → 1
            double t = (x - startX) / totalLength;

            // convert to wave space
            double sineInput = t * numOfWaves * 2 * Math.PI;

            int y1 = (int) (midY + amplitude * Math.sin(sineInput));
            int y2 = (int) (midY + amplitude * Math.sin(sineInput + 1));

            g2.drawLine(x, y1 + 260, x + 1, y2 + 260);
        }

        // Draw Block
        g2.setColor(Color.red);
        g2.fillRect((int)currentPosition, 320, blockWidth, blockHeight);


        // Draw Spring Wall
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 210, 30, 200);

        // Draw Floor
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 400, 580, 35);

        //BAR CONSTANTS//
        int startingHeight = 297;

        // Draw Spring Potential Energy Graph
        g2.setColor(Color.BLUE);
        int speHeight = (int) (springPotentialEnergy * heightScale);
        int speY = startingHeight - speHeight;
        g2.fillRect(739, speY, 108, speHeight);

        // Draw Kinetic Energy Graph
        g2.setColor(Color.RED);
        int keHeight = (int) (kineticEnergy * heightScale);
        int keY = startingHeight - keHeight;
        g2.fillRect(897, keY, 108, keHeight);

        // Draw Work Graph
        g2.setColor(Color.green);
        if (startingTotalEnergy - totalEnergy >= work)
            work = startingTotalEnergy - totalEnergy;
        int workHeight = (int) (work * heightScale);
        if (friction == 1) // if no friction, no work, to negate errors via pixels
        {
            workHeight = 0;
            work = 0;
        }
        int workY = startingHeight - workHeight;
        g2.fillRect(1075, workY, 108, workHeight);

        // Draw Buttons
        g2.setFont(new Font("Georgia", Font.BOLD, 30));
        g2.setColor(Color.BLACK);
        g2.drawString("Set Mass", 800, 450);

        // Draw Info
        g2.setFont(new Font("Georgia", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);
        g2.drawString("Velocity: " + new DecimalFormat("0.0").format(velocity) + " m/s", 1000, 470);
        g2.drawString("Spring Potential Energy: " + new DecimalFormat("0").format(springPotentialEnergy) + "J", 1000, 520);
        g2.drawString("Kinetic Energy: " + new DecimalFormat("0").format(kineticEnergy) + "J", 1000, 570);
        g2.drawString("Delta X: " + new DecimalFormat("0").format(deltax) + " m", 1000, 620);
    }
}

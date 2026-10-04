package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class EnergySimulationPanel_Pendulum extends JPanel implements Runnable
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

    // PENDULUM PHYSICS
    double length = 350;
    double angle = Math.PI / 4;
    double angularVelocity = 0;
    double angularAcceleration = 0;
    double startingAngle = Math.PI / 4;

    double gravity = 0.5;
    double airResistance = 1;

    // ENERGY TRACKING
    double mass = 500 / (gravity * length * (1 - Math.cos(angle)));
    double kineticEnergy = 0;
    double potentialEnergy = mass * gravity * length * (1 - Math.cos(angle));
    double totalEnergy = 0;
    double startingTotalEnergy = 0;
    double work = 0;
    double heightScale = 0;
    boolean hasStoredStartingEnergy = false;

    public EnergySimulationPanel_Pendulum(String image)
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
    public void run()
    {
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

    public void setGravity(double gravity)
    {
        this.gravity = gravity;
        potentialEnergy = gravity * mass * length * (1-Math.cos(angle));
        heightScale = 245 / (gravity * mass * length * (1-Math.cos(startingAngle)));
    }
    public void setAR(double airResistance)
    {
        // passed a value through 0-5: 0 means no AR, 5 means maximum AR (0.98)
        airResistance = 1 - (airResistance * 0.004);
        this.airResistance = airResistance;
    }
    public void setAngle(double angle)
    {
        angle = angle * Math.PI / 180;
        this.angle = angle;
        startingAngle = angle;
        potentialEnergy = gravity * mass * length * (1-Math.cos(angle));
        heightScale = 245 / (gravity * mass * length * (1-Math.cos(startingAngle)));
    }
    public void restart()
    {
        angle = startingAngle;
        kineticEnergy = 0;
        potentialEnergy = mass * gravity * length * (1 - Math.cos(angle));
        work = 0;
        heightScale = 245 / potentialEnergy;
        angularVelocity = 0;
        angularAcceleration = 0;
        hasStoredStartingEnergy = false;
        startingTotalEnergy = 0;
    }

    private void update()
    {
        if (isRunning)
        {
            // Angular Acceleration Equation for SHM: a = -(g/L) * sin(θ)
            angularAcceleration = -(gravity / length) * Math.sin(angle);

            angularVelocity += angularAcceleration; // velocity + change in velocity
            angularVelocity *= airResistance;
            angle += angularVelocity; // angle + change in angle

            // Energy calculations
            double height = length * (1 - Math.cos(angle)); // relative height

            potentialEnergy = mass * gravity * height;
            kineticEnergy = 0.5 * mass * Math.pow(angularVelocity * length, 2);
            totalEnergy = potentialEnergy + kineticEnergy;

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

        int originX = 340;
        int originY = 150;

        // Pendulum position
        int x = (int)(originX + length * Math.sin(angle)); // ORIGIN + OPPOSITE
        int y = (int)(originY + length * Math.cos(angle)); // ORIGIN + ADJACENT

        // Draw rod
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(8));
        g2.drawLine(originX, originY, x, y); // Line from origin point to bob point

        // Draw bob
        int bobLength = 60;
        int bobWidth = 60;

        g2.setColor(Color.RED);
        g2.fillOval(x - bobWidth/2, y - bobLength/2, bobWidth, bobLength);

        // Draw hanger
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(originX - 300/2, 120, 300, 50);

        //BAR CONSTANTS//
        int startingHeight = 297;

        // Draw PE Graph
        g2.setColor(Color.BLUE);
        int peHeight = (int) (potentialEnergy * heightScale);
        int peY = startingHeight - peHeight;
        g2.fillRect(739, peY, 108, peHeight);

        // Draw KE Graph
        g2.setColor(Color.RED);
        int keHeight = (int) (kineticEnergy * heightScale);
        int keY = startingHeight - keHeight;
        g2.fillRect(897, keY, 108, keHeight);


        // Draw Work Graph
        g2.setColor(Color.green);
        double work = startingTotalEnergy - totalEnergy;
        int workHeight = (int) (work * heightScale);
        if (airResistance == 1) // if no air resistance, no work, to negate errors via pixels
        {
            workHeight = 0;
            work = 0;
        }
        int workY = startingHeight - workHeight;
        g2.fillRect(1075, workY, 108, workHeight);

        // Draw Information
        g2.setFont(new Font("Georgia", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);
        g2.drawString("Angle: " + new DecimalFormat("0.0").format(angle * 180 / Math.PI) + "°", 1020, 470);
        g2.drawString("Potential Energy: " + new DecimalFormat("0").format(potentialEnergy) + "J", 1020, 520);
        g2.drawString("Kinetic Energy: " + new DecimalFormat("0").format(kineticEnergy) + "J", 1020, 570);
        g2.drawString("Work: " + new DecimalFormat("0").format(work) + "J", 1020, 620);
    }
}

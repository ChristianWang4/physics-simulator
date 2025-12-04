package entity;

import main.SimulationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Block extends Entity
{
    SimulationPanel simulationPanel;
    String imageName;
    final int kSize = 1600;
    final int horizontalDistanceFromSides = 150;
    boolean isLeft;

    private double oVelocity, oX, oY;

    public Block(SimulationPanel sP, double velocity, double mass, String imageName, boolean isLeft)
    {
        simulationPanel = sP;
        this.isLeft = isLeft;
        this.velocity = velocity; this.oVelocity = velocity;
        this.mass = mass;
        if (mass * kSize <= 160000)
            this.area = mass * kSize;
        else
            this.area = 160000;
        this.width = (int) Math.sqrt(area);
        this.height  = (int) Math.sqrt(area);

        if (isLeft) {
            x = horizontalDistanceFromSides;
            oX = x;
        }
        else {
            x = simulationPanel.screenWidth - this.width - horizontalDistanceFromSides;
            oX = x;
        }

        this.y = simulationPanel.screenHeight - simulationPanel.floor.h - this.height;
        this.oY = this.y;
        hitbox = new Rectangle( (int) x, (int) y, width, height);
        this.imageName = imageName;
        getImage(imageName);
    }

    public void getImage(String imageName)
    {
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(imageName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateSize()
    {
        if (mass * kSize <= 160000)
            this.area = mass * kSize;
        else
            this.area = 160000;
        this.width = (int) Math.sqrt(area);
        this.height  = (int) Math.sqrt(area);
        this.y = simulationPanel.screenHeight - simulationPanel.floor.h - this.height;
        oY = y;
        if (isLeft) {
            x = horizontalDistanceFromSides;
            oX = x;
        }
        else {
            x = simulationPanel.screenWidth - this.width - horizontalDistanceFromSides;
            oX = x;
        }
    }

    public void setMass(double mass)
    {
        this.mass = mass;
    }

    public void setVelocity(double velocity)
    {
        this.velocity = velocity;
        oVelocity = velocity;
    }

    public void resetValues()
    {
        velocity = oVelocity;
        x = oX;
        y = oY;
    }

    public void update()
    {
        x += velocity;
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage theImage = image;
        g2.drawImage(theImage, (int) Math.round(x), (int) Math.round(y), width, height, null);
    }
}

package background;

import entity.Entity;
import main.SimulationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Floor extends BackgroundObject{
    SimulationPanel simulationPanel;
    public boolean collision = false;

    public Floor(SimulationPanel sP, boolean collision)
    {
        simulationPanel = sP;
        this.collision = collision;

        getImage();
        setDefaultValues();
    }

    public void setDefaultValues()
    {
        x = 0;
        y = 620;
        w = 1280;
        h = 100;
    }

    public void getImage()
    {
        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/floor/floor.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage theImage = image;
        g2.drawImage(theImage, x, y, w, h, null);
    }
}

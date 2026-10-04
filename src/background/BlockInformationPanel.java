package background;

import entity.Entity;
import main.MomentumSimulationPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

public class BlockInformationPanel extends BackgroundObject
{
    MomentumSimulationPanel sp;
    Entity entity;
    BufferedImage image;

    public BlockInformationPanel(MomentumSimulationPanel sp, Entity entity, int x, int y)
    {
        this.sp = sp;
        this.entity = entity;
        this.x = x; this.y = y;
        getImage("/panels/number_panel.png");
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

    public void draw(Graphics2D g2)
    {
        g2.setFont(new Font("Georgia", Font.PLAIN, 18));
        g2.setColor(Color.WHITE);

        BufferedImage theImage = image;
        g2.drawImage(theImage, x, y, 400, 200, null);
        g2.drawString("Mass: " + entity.mass +" kg", x + 20, y + 50);
        g2.drawString("Velocity: " + new DecimalFormat("0.00").format(entity.velocity) + " m/s", x + 20, y + 85);
        g2.drawString("Momentum: " + new DecimalFormat("0.00").format(entity.mass * entity.velocity) + " kgm/s", x + 20, y + 150);
        g2.drawString("# Of Collisions: " + sp.numOfCollisions, x + 20, y + 180);

    }

}

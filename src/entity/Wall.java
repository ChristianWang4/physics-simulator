package entity;

import java.awt.*;

public class Wall extends Entity
{
    public Wall(boolean isLeftWall)
    {
        this.width = Integer.MAX_VALUE;
        this.height = 620;
        this.y = 0;
        if (isLeftWall)
            this.x = -this.width;
        else
            this.x = 1280;
    }


    public void draw(Graphics2D g2)
    {
        g2.drawImage(image, (int) this.x, (int) this.y, this.width, this.height, null);
    }
}

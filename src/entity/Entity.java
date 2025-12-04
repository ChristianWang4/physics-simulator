package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public double x, y;
    public int width, height;
    public double velocity;
    public double mass;
    public double area;

    public BufferedImage image;

    public Rectangle hitbox;
}

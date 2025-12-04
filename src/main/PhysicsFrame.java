package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class PhysicsFrame extends JFrame
{
    public PhysicsFrame(String iconDirectory)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Physics Simulator");
        try {
            this.setIconImage(ImageIO.read(Objects.requireNonNull(Main.class.getResource(iconDirectory))));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

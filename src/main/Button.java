package main;

import javax.swing.*;

public class Button extends JButton
{
    public Button(ImageIcon icon, int x, int y, int w, int h)
    {
        super(icon);
        this.setBounds(x, y, w, h);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
    }

}

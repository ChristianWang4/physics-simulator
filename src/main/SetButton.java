package main;

import javax.swing.*;

public class SetButton extends Button
{
    ImageIcon availableIcon, unavailableIcon;
    boolean available;
    public SetButton(ImageIcon availableIcon, int x, int y, int w, int h, ImageIcon unavailableIcon)
    {
        super(availableIcon, x, y, w, h);
        this.availableIcon = availableIcon;
        this.unavailableIcon = unavailableIcon;
        this.available = true;
    }

    public static void setUnavailable(SetButton[] setButtons)
    {
        for (SetButton setButton : setButtons)
        {
            setButton.setIcon(setButton.unavailableIcon);
            setButton.available = false;
        }
    }

    public static void setAvailable(SetButton[] setButtons)
    {
        for (SetButton setButton : setButtons)
        {
            setButton.setIcon(setButton.availableIcon);
            setButton.available = true;
        }
    }
}

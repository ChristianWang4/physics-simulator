package main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

public class PanelSwitcher
{
    private final JFrame window;
    private final ArrayList<JPanel> panelHistory = new ArrayList<>();
    public Button returnButton;

    public PanelSwitcher(JFrame window, JPanel startingPanel)
    {
        this.window = window;
        panelHistory.add(startingPanel);
        returnButton = new Button(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/return_button.png"))), 18, 18, 40, 40);
        returnButton.addActionListener(e -> {
            backSwitch();
        });
    }

    public void switchPanel(JPanel panelFrom, JPanel panelTo)
    {
        window.remove(panelFrom);
        panelTo.add(returnButton);
        window.add(panelTo);
        panelHistory.add(panelTo);

        window.revalidate();
        window.repaint();
    }

    public void backSwitch()
    {
        JPanel currentPanel = panelHistory.getLast();
        panelHistory.removeLast();
        JPanel formerPanel = panelHistory.getLast();

        window.remove(currentPanel);
        if (panelHistory.size() > 1)
            formerPanel.add(returnButton);
        window.add(formerPanel);

        window.revalidate();
        window.repaint();
    }

    public void switchPanel(JPanel panelFrom, JPanel panelTo, boolean isElastic)
    {
        window.remove(panelFrom);
        panelTo.add(returnButton);
        window.add(panelTo);
        panelHistory.add(panelTo);

        window.revalidate();
        window.repaint();

        if (panelTo instanceof MomentumSimulationPanel momentumSimulationPanel)
        {
            ((MomentumSimulationPanel) panelTo).startSimulationThread();
            ((MomentumSimulationPanel) panelTo).setElasticity(isElastic);
        }
    }
}

package main;

import javax.swing.*;

public class PanelSwitcher
{
    private final JFrame window;

    public PanelSwitcher(JFrame window)
    {
        this.window = window;
    }

    public void switchPanel(JPanel panelFrom, JPanel panelTo)
    {
        window.remove(panelFrom);
        window.add(panelTo);
        window.revalidate();
        window.repaint();
    }

    public void switchPanel(JPanel panelFrom, JPanel panelTo, boolean isElastic)
    {
        window.remove(panelFrom);
        window.add(panelTo);
        window.revalidate();
        window.repaint();

        if (panelTo instanceof SimulationPanel simulationPanel)
        {
            ((SimulationPanel) panelTo).startSimulationThread();
            ((SimulationPanel) panelTo).setElasticity(isElastic);
        }
    }
}

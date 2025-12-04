package main;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Main
{
    public static void main(String[] args) throws IOException {
        // Frame
        PhysicsFrame window = new PhysicsFrame("/block/simulation_icon.png");

        // PanelSwitcher
        PanelSwitcher panelSwitcher = new PanelSwitcher(window);

        // Simulation Panel
        SimulationPanel simulationPanel = new SimulationPanel();

        // Menu Panel
        MenuPanel menuPanel = new MenuPanel();

        // Buttons
        ImageIcon startIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/start_button.png")));
        ImageIcon pauseIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/pause_button.png")));
        ImageIcon restartIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/restart_button.png")));
        ImageIcon returnIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/return_button.png")));
        ImageIcon elasticOption = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/elasticOption.png")));
        ImageIcon inelasticOption = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/inelasticOption.png")));

        SetButton setMass1 = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_green.png"))), 342, 95, 120, 40, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_gray.png"))));
        setMass1.addActionListener(event -> {
            if (setMass1.available) {
                String mass1 = JOptionPane.showInputDialog("Enter Mass (1-500):");
                try {
                    double num = Double.parseDouble(mass1);
                    if (num > 0 && num <= 500) {
                        simulationPanel.block1.setMass(num);
                        simulationPanel.block1.updateSize();
                        JOptionPane.showMessageDialog(simulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        SetButton setVelocity1 = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_green.png"))), 342, 140, 120, 40, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_gray.png"))));
        setVelocity1.addActionListener(event -> {
            if (setVelocity1.available) {
                String velocity1 = JOptionPane.showInputDialog("Enter Velocity (-40 -> 40):");
                try {
                    double num = Double.parseDouble(velocity1);
                    if (num >= -40 && num <= 40) {
                        simulationPanel.block1.setVelocity(num);
                        JOptionPane.showMessageDialog(simulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        SetButton setMass2 = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_green.png"))), 1021, 95, 120, 40, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_gray.png"))));
        setMass2.addActionListener(event -> {
            if (setMass2.available)
            {
            String mass2 = JOptionPane.showInputDialog("Enter Mass (1-500):");
            try {
                double num = Double.parseDouble(mass2);
                if (num > 0 && num <= 500)
                {
                    simulationPanel.block2.setMass(num);
                    simulationPanel.block2.updateSize();
                    JOptionPane.showMessageDialog(simulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            }

        });


        SetButton setVelocity2 = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_green.png"))), 1021, 140, 120, 40, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_gray.png"))));
        setVelocity2.addActionListener(event -> {
            if (setVelocity2.available) {
                String velocity2 = JOptionPane.showInputDialog("Enter Velocity (-40 -> 40):");
                try {
                    double num = Double.parseDouble(velocity2);
                    if (num >= -40 && num <= 40) {
                        simulationPanel.block2.setVelocity(num);
                        JOptionPane.showMessageDialog(simulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(simulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        SetButton[] setButtons = new SetButton[]{setMass1, setVelocity1, setMass2, setVelocity2};

        Button startPauseButton = new Button(startIcon, 1090, 18, 40, 40);
        startPauseButton.addActionListener(e -> {
            simulationPanel.isRunning = !simulationPanel.isRunning;
            if (simulationPanel.isRunning) {
                startPauseButton.setIcon(pauseIcon);
                SetButton.setUnavailable(setButtons);
            }
            else {
                startPauseButton.setIcon(startIcon);
            }
        });

        Button restartButton = new Button(restartIcon, 1140, 18, 40, 40);
        restartButton.addActionListener(e -> {
            simulationPanel.isRestarted = true;
            if (!simulationPanel.isRunning)
                SetButton.setAvailable(setButtons);
        });

        Button returnButton = new Button(returnIcon, 100, 18, 40, 40);
        returnButton.addActionListener(e -> {
            simulationPanel.isRunning = false;
            SetButton.setAvailable(setButtons);
            startPauseButton.setIcon(startIcon);
            simulationPanel.isRestarted = true;
            simulationPanel.stopSimulationThread();
            panelSwitcher.switchPanel(simulationPanel, menuPanel);
        });

        Button elasticOptionButton = new Button(elasticOption, 290, 320, 700, 100);
        elasticOptionButton.addActionListener(e -> {
            panelSwitcher.switchPanel(menuPanel, simulationPanel, true);

        });

        Button inelasticOptionButton = new Button(inelasticOption, 290, 470, 700, 100);
        inelasticOptionButton.addActionListener(e -> {
            panelSwitcher.switchPanel(menuPanel, simulationPanel, false);
        });



        // Adding Components
        menuPanel.add(elasticOptionButton);
        menuPanel.add(inelasticOptionButton);
        simulationPanel.add(startPauseButton);
        simulationPanel.add(restartButton);
        simulationPanel.add(returnButton);
        simulationPanel.add(setMass1);
        simulationPanel.add(setVelocity1);
        simulationPanel.add(setMass2);
        simulationPanel.add(setVelocity2);
        window.add(menuPanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

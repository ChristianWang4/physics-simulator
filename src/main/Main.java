package main;
import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

public class Main
{
    public static void main(String[] args) throws IOException {

        // Frame
        PhysicsFrame window = new PhysicsFrame("/block/simulation_icon.png");

        // Main Menu Panel
        MainMenuPanel mainMenuPanel = new MainMenuPanel("/panels/Shawsics_MainPage.png");

        // PanelSwitcher
        PanelSwitcher panelSwitcher = new PanelSwitcher(window, mainMenuPanel);

        // Panels - MOMENTUM
        MomentumMenuPanel momentumMenuPanel = new MomentumMenuPanel();
        MomentumSimulationPanel momentumSimulationPanel = new MomentumSimulationPanel();

        // Panels - ENERGY
        EnergyMenuPanel energyMenuPanel = new EnergyMenuPanel();
        EnergySimulationPanel_Pendulum energySimulationPanelPendulum = new EnergySimulationPanel_Pendulum("/panels/energy_simulation_background_pendulum.png");
        EnergySimulationPanel_Spring energySimulationPanelSpring = new EnergySimulationPanel_Spring("/panels/energy_simulation_background_spring.png");

        // Buttons
        ImageIcon momentumButton = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/MomentumOption.png")));
        ImageIcon energyButton = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/EnergyOption.png")));
        ImageIcon kinematicsButton = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/KinematicsOption.png")));

        ImageIcon startIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/start_button.png")));
        ImageIcon pauseIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/pause_button.png")));
        ImageIcon restartIcon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/restart_button.png")));
        ImageIcon elasticOption = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/elasticOption.png")));
        ImageIcon inelasticOption = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/inelasticOption.png")));
        ImageIcon pendulumOption = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/pendulum_option.png")));
        ImageIcon springOption = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/spring_option.png")));




        SetButton setMass1 = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_green.png"))), 342, 95, 120, 40, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_button_gray.png"))));
        setMass1.addActionListener(event -> {
            if (setMass1.available) {
                String mass1 = JOptionPane.showInputDialog("Enter Mass (1-500):");
                try {
                    double num = Double.parseDouble(mass1);
                    if (num > 0 && num <= 500) {
                        momentumSimulationPanel.block1.setMass(num);
                        momentumSimulationPanel.block1.updateSize();
                        JOptionPane.showMessageDialog(momentumSimulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
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
                        momentumSimulationPanel.block1.setVelocity(num);
                        JOptionPane.showMessageDialog(momentumSimulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    momentumSimulationPanel.block2.setMass(num);
                    momentumSimulationPanel.block2.updateSize();
                    JOptionPane.showMessageDialog(momentumSimulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
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
                        momentumSimulationPanel.block2.setVelocity(num);
                        JOptionPane.showMessageDialog(momentumSimulationPanel, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(momentumSimulationPanel, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        SetButton[] setButtonsMomentum = new SetButton[]{setMass1, setVelocity1, setMass2, setVelocity2};

        Button startPauseButtonMomentum = new Button(startIcon, 1090, 18, 40, 40);
        startPauseButtonMomentum.addActionListener(e -> {
            momentumSimulationPanel.isRunning = !momentumSimulationPanel.isRunning;
            if (momentumSimulationPanel.isRunning) {
                startPauseButtonMomentum.setIcon(pauseIcon);
                SetButton.setUnavailable(setButtonsMomentum);
            }
            else {
                startPauseButtonMomentum.setIcon(startIcon);
            }
        });

        Button restartButtonMomentum = new Button(restartIcon, 1140, 18, 40, 40);
        restartButtonMomentum.addActionListener(e -> {
            momentumSimulationPanel.isRestarted = true;
            if (!momentumSimulationPanel.isRunning)
                SetButton.setAvailable(setButtonsMomentum);
        });



        SetButton setGravityButton = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_gravity_green.png"))), 720, 415, 271, 74, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_gravity_gray.png"))));
        setGravityButton.addActionListener(event -> {
            if (setGravityButton.available) {
                String gravity = JOptionPane.showInputDialog("Enter Gravity (0.1 -> 10.0):");
                try {
                    double num = Double.parseDouble(gravity);
                    if (num >= 0.1 && num <= 10) {
                        energySimulationPanelPendulum.setGravity(num);
                        JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }});

        SetButton setAirResistanceButton = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_ar_green.png"))), 720, 500, 271, 74, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_ar_gray.png"))));
        setAirResistanceButton.addActionListener(event -> {
            if (setAirResistanceButton.available) {
                String airResistance = JOptionPane.showInputDialog("Enter Air Resistance (0 -> 5):");
                try {
                    double num = Double.parseDouble(airResistance);
                    if (num >= 0 && num <= 5) {
                        energySimulationPanelPendulum.setAR(num);
                        JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }});

        SetButton setAngleButton = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_angle_green.png"))), 720, 585, 271, 74, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/set_angle_gray.png"))));
        setAngleButton.addActionListener(event -> {
            if (setAngleButton.available) {
                String angle = JOptionPane.showInputDialog("Enter Angle in Degrees (0 -> 360):");
                try {
                    double num = Double.parseDouble(angle);
                    if (num >= 0 && num <= 360) {
                        energySimulationPanelPendulum.setAngle(num);
                        JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(energySimulationPanelPendulum, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }});

        SetButton[] setButtonsEnergyPendulum = new SetButton[]{setGravityButton, setAirResistanceButton, setAngleButton};

        SetButton setMassButtonSpring = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/green_button_energy.png"))), 720, 415, 271, 74, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/gray_button_energy.png"))));
        setMassButtonSpring.addActionListener(event -> {
            if (setMassButtonSpring.available) {
                String mass = JOptionPane.showInputDialog("Enter Mass (5.0 -> 50.0):");
                try {
                    double num = Double.parseDouble(mass);
                    if (num >= 5 && num <= 50) {
                        energySimulationPanelSpring.setMass(num);
                        JOptionPane.showMessageDialog(energySimulationPanelSpring, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(energySimulationPanelSpring, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(energySimulationPanelSpring, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }});

        SetButton setSpringConstantButton = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/green_button_energy.png"))), 720, 500, 271, 74, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/gray_button_energy.png"))));
        setSpringConstantButton.addActionListener(event -> {
            if (setSpringConstantButton.available) {
                String springConstant = JOptionPane.showInputDialog("Enter Spring Constant (0.01 -> 1.00):");
                try {
                    double num = Double.parseDouble(springConstant);
                    if (num >= 0 && num <= 1) {
                        energySimulationPanelSpring.setSpringConstant(num);
                        JOptionPane.showMessageDialog(energySimulationPanelSpring, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(energySimulationPanelSpring, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(energySimulationPanelSpring, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }});

        SetButton setFrictionButtonSpring = new SetButton(new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/green_button_energy.png"))), 720, 585, 271, 74, new ImageIcon(Objects.requireNonNull(Main.class.getResource("/buttons/gray_button_energy.png"))));
        setFrictionButtonSpring.addActionListener(event -> {
            if (setFrictionButtonSpring.available) {
                String friction = JOptionPane.showInputDialog("Enter Friction (0 -> 5):");
                try {
                    double num = Double.parseDouble(friction);
                    if (num >= 0 && num <= 5) {
                        energySimulationPanelSpring.setFriction(num);
                        JOptionPane.showMessageDialog(energySimulationPanelSpring, "Success!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(energySimulationPanelSpring, "Error! Invalid range.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(energySimulationPanelSpring, "Error! Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }});

        SetButton[] setButtonsEnergySpring = new SetButton[]{setMassButtonSpring, setSpringConstantButton, setFrictionButtonSpring};


        Button startPauseButtonEnergyPendulum = new Button(startIcon, 1172, 18, 40, 40);
        startPauseButtonEnergyPendulum.addActionListener(e -> {
            energySimulationPanelPendulum.isRunning = !energySimulationPanelPendulum.isRunning;
            if (energySimulationPanelPendulum.isRunning) {
                startPauseButtonEnergyPendulum.setIcon(pauseIcon);
                SetButton.setUnavailable(setButtonsEnergyPendulum);
            }
            else {
                startPauseButtonEnergyPendulum.setIcon(startIcon);
            }
        });

        Button restartButtonEnergyPendulum = new Button(restartIcon, 1222, 18, 40, 40);
        restartButtonEnergyPendulum.addActionListener(e -> {
            energySimulationPanelPendulum.restart();
            if (!energySimulationPanelPendulum.isRunning)
                SetButton.setAvailable(setButtonsEnergyPendulum);
        });

        Button startPauseButtonEnergySpring = new Button(startIcon, 1172, 18, 40, 40);
        startPauseButtonEnergySpring.addActionListener(e -> {
            energySimulationPanelSpring.isRunning = !energySimulationPanelSpring.isRunning;
            if (energySimulationPanelSpring.isRunning) {
                startPauseButtonEnergySpring.setIcon(pauseIcon);
                SetButton.setUnavailable(setButtonsEnergySpring);
            }
            else {
                startPauseButtonEnergySpring.setIcon(startIcon);
                SetButton.setAvailable(setButtonsEnergySpring);
            }
        });

        Button restartButtonEnergySpring = new Button(restartIcon, 1222, 18, 40, 40);
        restartButtonEnergySpring.addActionListener(e -> {
            energySimulationPanelSpring.restart();
            if (!energySimulationPanelSpring.isRunning)
                SetButton.setAvailable(setButtonsEnergySpring);
        });

        Button elasticOptionButton = new Button(elasticOption, 290, 320, 700, 100);
        elasticOptionButton.addActionListener(e -> {
            panelSwitcher.switchPanel(momentumMenuPanel, momentumSimulationPanel, true);

        });

        Button inelasticOptionButton = new Button(inelasticOption, 290, 470, 700, 100);
        inelasticOptionButton.addActionListener(e -> {
            panelSwitcher.switchPanel(momentumMenuPanel, momentumSimulationPanel, false);
        });

        Button pendulumOptionButton = new Button(pendulumOption, 193, 300, 350, 350);
        pendulumOptionButton.addActionListener(e -> {
            energySimulationPanelPendulum.restart();
            if (!energySimulationPanelPendulum.isRunning)
                SetButton.setAvailable(setButtonsEnergyPendulum);
            panelSwitcher.switchPanel(energyMenuPanel, energySimulationPanelPendulum);
        });

        Button springOptionButton = new Button(springOption, 736, 300, 350, 350);
        springOptionButton.addActionListener(e -> {
            energySimulationPanelSpring.restart();
            if (!energySimulationPanelSpring.isRunning)
                SetButton.setAvailable(setButtonsEnergySpring);
            panelSwitcher.switchPanel(energyMenuPanel, energySimulationPanelSpring);
        });

        Button MomentumOption = new Button(momentumButton, 75, 280, 315, 315);
        MomentumOption.addActionListener( e -> {
            panelSwitcher.switchPanel(mainMenuPanel, momentumMenuPanel);
        });

        Button EnergyOption = new Button(energyButton, 483, 280, 315, 315);
        EnergyOption.addActionListener( e -> {
            panelSwitcher.switchPanel(mainMenuPanel, energyMenuPanel);
        });

        Button KinematicsOption = new Button(kinematicsButton, 890, 280, 315, 315);
        KinematicsOption.addActionListener( e -> {
            panelSwitcher.switchPanel(mainMenuPanel, momentumMenuPanel);
        });

        // Adding Components

        mainMenuPanel.add(MomentumOption);
        mainMenuPanel.add(EnergyOption);
        mainMenuPanel.add(KinematicsOption);

        momentumMenuPanel.add(elasticOptionButton);
        momentumMenuPanel.add(inelasticOptionButton);

        energyMenuPanel.add(pendulumOptionButton);
        energyMenuPanel.add(springOptionButton);

        momentumSimulationPanel.add(startPauseButtonMomentum);
        momentumSimulationPanel.add(restartButtonMomentum);
        momentumSimulationPanel.add(setMass1);
        momentumSimulationPanel.add(setVelocity1);
        momentumSimulationPanel.add(setMass2);
        momentumSimulationPanel.add(setVelocity2);

        energySimulationPanelPendulum.add(setGravityButton);
        energySimulationPanelPendulum.add(setAirResistanceButton);
        energySimulationPanelPendulum.add(setAngleButton);
        energySimulationPanelPendulum.add(startPauseButtonEnergyPendulum);
        energySimulationPanelPendulum.add(restartButtonEnergyPendulum);

        energySimulationPanelSpring.add(setMassButtonSpring);
        energySimulationPanelSpring.add(setSpringConstantButton);
        energySimulationPanelSpring.add(setFrictionButtonSpring);
        energySimulationPanelSpring.add(startPauseButtonEnergySpring);
        energySimulationPanelSpring.add(restartButtonEnergySpring);


        window.add(mainMenuPanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}

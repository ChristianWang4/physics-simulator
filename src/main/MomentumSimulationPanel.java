package main;
import background.BlockInformationPanel;
import entity.Block;
import background.Floor;
import entity.Wall;

import javax.swing.*;
import java.awt.*;

public class MomentumSimulationPanel extends JPanel implements Runnable
{
    // SCREEN SETTINGS
    public final int screenWidth = 1280;
    public final int screenHeight = 720;
    public final int FPS = 60;
    public boolean isRunning = false;
    public boolean isRestarted = false;

    // MISCELLANEOUS
    public Thread gameThread = null;
    public volatile boolean isThreadRunning = false;

    public Floor floor = new Floor(this, true);
    Block block1 = new Block(this,  5, 10, "/block/block.png", true);
    Block block2 = new Block(this,  -5 , 10, "/block/block2.png", false);
    Wall leftWall = new Wall(true);
    Wall rightWall = new Wall(false);
    private BlockInformationPanel blockOneInfoPanel= new BlockInformationPanel(this,block1, 100, 75);
    private BlockInformationPanel blockTwoInfoPanel= new BlockInformationPanel(this, block2, 780, 75);
    CollisionDetector collisionDetector = new CollisionDetector(this);
    ImpulseCalculator impulseCalculator = new ImpulseCalculator(this);
    public int numOfCollisions = 0;
    public boolean isElastic;
    public boolean hasAlreadyCollided = false;


    public MomentumSimulationPanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // 1280x720
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);
    }

    public void setElasticity(boolean isElastic)
    {
        this.isElastic = isElastic;
    }

    public void startSimulationThread()
    {
        if (gameThread != null && gameThread.isAlive())
            return;

        isThreadRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopSimulationThread() {
        isThreadRunning = false;
        if (gameThread != null)
        {
            gameThread.interrupt();
            try {
                gameThread.join(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        while (gameThread != null)
        {
            final double drawInterval = 1000000000.0/FPS; // 0.016666 seconds per frame
            double nextDrawTime = System.nanoTime() + drawInterval;
            while (isThreadRunning) {
                // 1. UPDATE: update information such as character positions
                update();

                // 2. DRAW: draw the screen w/ updated information
                repaint();
                try {
                    double remainingTime = nextDrawTime - System.nanoTime();

                    if (remainingTime < 0)
                        remainingTime = 0;

                    Thread.sleep((long) remainingTime / 1000000);
                    nextDrawTime += drawInterval;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public void update()
    {
        if (isRestarted)
        {
            block1.resetValues();
            block2.resetValues();
            numOfCollisions = 0;
            hasAlreadyCollided = false;
        }

        if (isRunning) {
            isRestarted = false;
            if (!hasAlreadyCollided)
                collisionDetector.checkCollision(block1, block2, isElastic);

            collisionDetector.checkWallCollision(leftWall, block1, isElastic, block2);
            collisionDetector.checkWallCollision(rightWall, block1, isElastic, block2);
            collisionDetector.checkWallCollision(leftWall, block2, isElastic, block1);
            collisionDetector.checkWallCollision(rightWall, block2, isElastic, block1);

            if (!isElastic && !hasAlreadyCollided && numOfCollisions == 1) {
                hasAlreadyCollided = true;
            }

            block1.update();
            block2.update();
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        block1.draw(g2);
        block2.draw(g2);
        blockOneInfoPanel.draw(g2);
        blockTwoInfoPanel.draw(g2);
        floor.draw(g2);
        leftWall.draw(g2);
        rightWall.draw(g2);

    }
}

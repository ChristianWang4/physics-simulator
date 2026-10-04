package main;

import entity.Entity;
import entity.Wall;

public class CollisionDetector
{
    MomentumSimulationPanel sp;

    public CollisionDetector(MomentumSimulationPanel sp)
    {
        this.sp = sp;
    }

    public void checkCollision(Entity entity1, Entity entity2, boolean isElastic)
    {
        // Check if the rectangles overlap using proper bounding box collision
        if (entity1.x + entity1.width >= entity2.x &&
                entity1.x <= entity2.x + entity2.width)
        {
            correctEntityOverlap(entity1, entity2);
            if (isElastic)
            {
                sp.impulseCalculator.calculateElasticImpulse(entity1, entity2);
            }
            else
            {
                sp.impulseCalculator.calculateInelasticImpulse(entity1, entity2);
            }
            sp.numOfCollisions++;
        }
    }

    private void correctEntityOverlap(Entity entity1, Entity entity2)
    {
        // Calculate overlap
        double overlap = (entity1.x + entity1.width) - entity2.x;

        // If there is overlap, move entity back
        if (overlap > 0) {
            entity1.x -= overlap;
            correctWallOverlap(entity2, overlap);
        }
    }

    private void correctWallOverlap(Entity entity, double overlap)
    {
        // Calculate overlap
        double wallOverlap = (entity.x + entity.width);

        if (wallOverlap > sp.rightWall.x)
        {
            entity.x -= sp.rightWall.x - entity.width - overlap;
        }

    }

    public void checkWallCollision(Wall wall, Entity entity, boolean isElastic, Entity otherBlock)
    {
        if (entity.velocity < 0) {
            if (wall.x + wall.width >= entity.x && wall.x <= entity.x + entity.width) {
                entity.velocity = entity.velocity * -1;
                if (!isElastic)
                    otherBlock.velocity = otherBlock.velocity * -1;
            }
        }
        else
        {
            if (entity.x + entity.width >= wall.x && entity.x <= wall.x + wall.width) {
                entity.velocity = entity.velocity * -1;
                if (!isElastic)
                    otherBlock.velocity = otherBlock.velocity * -1;
            }
        }
    }
}
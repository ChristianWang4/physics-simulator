package main;

import entity.Entity;

public class ImpulseCalculator
{
    SimulationPanel sp;
    public ImpulseCalculator(SimulationPanel sp)
    {
        this.sp = sp;
    }

    public void calculateElasticImpulse(Entity entity1, Entity entity2)
    {
        double finalVelocity1 = calcElasticFinalVelocity(entity1.mass, entity2.mass, entity1.velocity, entity2.velocity);
        double finalVelocity2 = calcElasticFinalVelocity(entity2.mass, entity1.mass, entity2.velocity, entity1.velocity);

        entity1.velocity = finalVelocity1;
        entity2.velocity = finalVelocity2;

    }

    public void calculateInelasticImpulse(Entity entity1, Entity entity2)
    {
        double finalVelocity = calculateInelasticFinalVelocity(entity1.mass, entity2.mass, entity1.velocity, entity2.velocity);

        entity1.velocity = finalVelocity;
        entity2.velocity = finalVelocity;
    }

    private double calcElasticFinalVelocity(double mass1, double mass2, double velocity1, double velocity2)
    {
        return ((velocity1 * (mass1 - mass2)) + (2 * mass2 * velocity2)) / (mass1+mass2);
    }

    private double calculateInelasticFinalVelocity(double mass1, double mass2, double velocity1, double velocity2)
    {
        return ((mass1 * velocity1) + (mass2 * velocity2)) / (mass1 + mass2);
    }
}

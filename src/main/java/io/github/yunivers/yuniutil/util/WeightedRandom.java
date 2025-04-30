package io.github.yunivers.yuniutil.util;

import java.util.Collection;
import java.util.Random;

@SuppressWarnings("unused")
public class WeightedRandom
{
    public static int getWeightTotal(Collection<WeightedRandomChoice> weights)
    {
        int total = 0;
        for (WeightedRandomChoice weighted : weights)
            total += weighted.weight;

        return total;
    }

    public static WeightedRandomChoice getRandomWeight(Random random, Collection<WeightedRandomChoice> weights, int maxWeight)
    {
        if (maxWeight <= 0)
            throw new IllegalArgumentException();

        int remaining = random.nextInt(maxWeight);
        for (WeightedRandomChoice weighted : weights)
        {
            if ((remaining -= weighted.weight) >= 0)
                continue;

            return weighted;
        }
        return null;
    }

    public static WeightedRandomChoice getRandomWeight(Random random, Collection<WeightedRandomChoice> weights)
    {
        return WeightedRandom.getRandomWeight(random, weights, WeightedRandom.getWeightTotal(weights));
    }

    public static int getWeightTotal(WeightedRandomChoice[] weights)
    {
        int total = 0;
        for (WeightedRandomChoice weighted : weights)
            total += weighted.weight;

        return total;
    }

    public static WeightedRandomChoice getRandomWeight(Random random, WeightedRandomChoice[] weights, int maxWeight)
    {
        if (maxWeight <= 0)
            throw new IllegalArgumentException();

        int remaining = random.nextInt(maxWeight);
        for (WeightedRandomChoice weighted : weights)
        {
            if ((remaining -= weighted.weight) >= 0)
                continue;

            return weighted;
        }
        return null;
    }

    public static WeightedRandomChoice getRandomWeight(Random random, WeightedRandomChoice[] weights)
    {
        return WeightedRandom.getRandomWeight(random, weights, WeightedRandom.getWeightTotal(weights));
    }
}

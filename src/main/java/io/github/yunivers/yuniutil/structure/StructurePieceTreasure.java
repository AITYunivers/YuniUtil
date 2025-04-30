package io.github.yunivers.yuniutil.structure;

import io.github.yunivers.yuniutil.util.WeightedRandomChoice;

public class StructurePieceTreasure extends WeightedRandomChoice
{
    public int itemId;
    public int damage;
    public int minCount;
    public int maxCount;

    public StructurePieceTreasure(int itemId, int damage, int minCount, int maxCount, int weight)
    {
        super(weight);
        this.itemId = itemId;
        this.damage = damage;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }
}

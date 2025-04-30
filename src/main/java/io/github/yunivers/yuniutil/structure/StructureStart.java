package io.github.yunivers.yuniutil.structure;

import lombok.Getter;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("unused")
public abstract class StructureStart
{
    @Getter
    protected LinkedList<StructureComponent> components = new LinkedList<>();
    @Getter
    protected StructureBoundingBox boundingBox;

    protected StructureStart()
    {

    }

    public void generateStructure(World world, Random random, StructureBoundingBox box)
    {
        Iterator<StructureComponent> iterator = this.components.iterator();
        while (iterator.hasNext())
        {
            StructureComponent component = iterator.next();
            if (!component.getBoundingBox().intersectsWith(box) || component.addComponentParts(world, random, box))
                continue;
            iterator.remove();
        }
    }

    protected void updateBoundingBox()
    {
        this.boundingBox = StructureBoundingBox.getNewBoundingBox();
        for (StructureComponent component : this.components)
            this.boundingBox.resizeBoundingBoxTo(component.getBoundingBox());
    }

    protected void markAvailableHeight(World world, Random random, int yPush)
    {
        int maxY = world.getHeight() / 2 - 1 - yPush;
        int boxHeight = this.boundingBox.getYSize() + 1;
        if (boxHeight < maxY)
            boxHeight += random.nextInt(maxY - boxHeight);
        int yOffset = boxHeight - this.boundingBox.maxY;
        this.boundingBox.offset(0, yOffset, 0);
        for (StructureComponent component : this.components)
            component.getBoundingBox().offset(0, yOffset, 0);
    }

    protected void calculateStructureY(World world, Random random, int minY, int maxY)
    {
        int height = maxY - minY + 1 - this.boundingBox.getYSize();
        int maxHeight = height > 1 ? minY + random.nextInt(height) : minY;
        int yOffset = maxHeight - this.boundingBox.minY;
        this.boundingBox.offset(0, yOffset, 0);
        for (StructureComponent component : this.components)
            component.getBoundingBox().offset(0, yOffset, 0);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isSizeableStructure()
    {
        return true;
    }
}

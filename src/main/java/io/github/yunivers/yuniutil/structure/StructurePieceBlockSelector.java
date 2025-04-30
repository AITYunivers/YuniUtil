package io.github.yunivers.yuniutil.structure;

import lombok.Getter;

import java.util.Random;

@Getter
public abstract class StructurePieceBlockSelector
{
    protected int selectedBlockId;
    protected int selectedBlockMetaData;

    protected StructurePieceBlockSelector()
    {

    }

    public abstract void selectBlocks(Random random, int x, int y, int z, boolean isEdge);
}

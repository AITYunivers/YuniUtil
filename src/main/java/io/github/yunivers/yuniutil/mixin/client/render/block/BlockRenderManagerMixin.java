package io.github.yunivers.yuniutil.mixin.client.render.block;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.yunivers.yuniutil.block.YuniTemplateFenceGateBlock;
import io.github.yunivers.yuniutil.block.YuniTemplateStemCrop;
import io.github.yunivers.yuniutil.client.render.block.YuniUtilBlockRenderers;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.world.BlockView;
import net.modificationstation.stationapi.api.client.render.block.StationRendererBlockRenderManager;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRenderManager.class)
@SuppressWarnings("unused")
public class BlockRenderManagerMixin implements StationRendererBlockRenderManager
{
    @Shadow public BlockView blockView;
    @Shadow public int textureOverride;

    @Inject(
        method = "render(Lnet/minecraft/block/Block;III)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    public void yuniutil$render_renderStem(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir)
    {
        if (block instanceof YuniTemplateStemCrop)
            cir.setReturnValue(YuniUtilBlockRenderers.renderBlockStem((BlockRenderManager)(Object)this, block, x, y, z));
    }

    @WrapOperation(
        method = "renderFence",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/BlockView;getBlockId(III)I"
        )
    )
    public int yuniutil$renderFence_connectFenceGate(BlockView instance, int x, int y, int z, Operation<Integer> original)
    {
        int blockId = original.call(instance, x, y, z);
        if (Block.BLOCKS[blockId] instanceof YuniTemplateFenceGateBlock)
            return Block.FENCE.id;
        return blockId;
    }
}

package io.github.yunivers.yuniutil.item;

import io.github.yunivers.yuniutil.entity.YuniTemplateProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class YuniTemplateThrowableItem extends TemplateItem
{
    public final Class<YuniTemplateProjectileEntity> projectleEntity;

    public YuniTemplateThrowableItem(Identifier identifier, Class<YuniTemplateProjectileEntity> projectleEntity)
    {
        super(identifier);
        this.projectleEntity = projectleEntity;
    }

    public ItemStack use(ItemStack stack, World world, PlayerEntity user)
    {
        stack.count--;
        world.playSound(user, "random.bow", 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote)
            try
            {
                world.spawnEntity(projectleEntity.getDeclaredConstructor(World.class, PlayerEntity.class).newInstance(world, user));
            }
            catch (Exception ignored)
            {

            }

        return stack;
    }
}

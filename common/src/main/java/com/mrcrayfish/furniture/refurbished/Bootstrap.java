package com.mrcrayfish.furniture.refurbished;

import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.network.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.DispenserBlock;

/**
 * Author: MrCrayfish
 */
public class Bootstrap
{
    public static void init()
    {
        Network.init();

        // Allows a spatula in a dispenser to flip items on the grill
        DispenserBlock.registerBehavior(ModItems.SPATULA::get, (source, stack) ->
        {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos pos = source.getPos().relative(direction).below();
            if(source.getLevel().getBlockEntity(pos) instanceof GrillBlockEntity grill)
            {
                grill.flipItems();
            }
            return stack;
        });
    }
}

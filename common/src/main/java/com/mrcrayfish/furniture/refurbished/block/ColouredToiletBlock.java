package com.mrcrayfish.furniture.refurbished.block;

import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.List;

/**
 * Author: MrCrayfish
 */
public class ColouredToiletBlock extends ToiletBlock implements BlockTagSupplier
{
    private final DyeColor color;

    public ColouredToiletBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL);
    }
}
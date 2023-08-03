package com.mrcrayfish.furniture.refurbished.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mrcrayfish.furniture.refurbished.blockentity.GrillBlockEntity;
import com.mrcrayfish.furniture.refurbished.core.ModBlockEntities;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.data.tag.BlockTagSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: MrCrayfish
 */
public class GrillBlock extends FurnitureHorizontalBlock implements EntityBlock, BlockTagSupplier
{
    private final DyeColor color;

    public GrillBlock(DyeColor color, Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION, Direction.NORTH));
        this.color = color;
    }

    public DyeColor getDyeColor()
    {
        return this.color;
    }

    @Override
    protected Map<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape baseShape = Block.box(1, 0, 1, 15, 16, 15);
        return ImmutableMap.copyOf(states.stream().collect(Collectors.toMap(state -> state, o -> baseShape)));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if(state.getBlock() != newState.getBlock())
        {
            if(level.getBlockEntity(pos) instanceof GrillBlockEntity grill)
            {
                Containers.dropContents(level, pos, grill.getCookingItems());
                Containers.dropContents(level, pos, grill.getFuelItems());
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result)
    {
        if(!level.isClientSide() && result.getDirection() == Direction.UP)
        {
            if(level.getBlockEntity(pos) instanceof GrillBlockEntity grill)
            {
                ItemStack stack = player.getItemInHand(hand);
                if(stack.getItem() == ModItems.SPATULA.get())
                {
                    grill.flipItem(this.getGrillQuadrant(result));
                }
                else if(stack.getItem() == Items.CHARCOAL)
                {
                    if(grill.addFuel(stack))
                    {
                        stack.shrink(1);
                        level.playSound(null, pos, SoundEvents.ANCIENT_DEBRIS_HIT, SoundSource.BLOCKS, 1.0F, 1.5F);
                    }
                }
                else if(!stack.isEmpty())
                {
                    if(grill.addCookingItem(stack, this.getGrillQuadrant(result), player.getDirection().get2DDataValue()))
                    {
                        if(!player.getAbilities().instabuild)
                        {
                            stack.shrink(1);
                        }
                    }
                }
                else
                {
                    grill.removeCookingItem(this.getGrillQuadrant(result));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private int getGrillQuadrant(BlockHitResult hit)
    {
        BlockPos pos = hit.getBlockPos();
        Vec3 hitVec = hit.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        return (hitVec.x() > 0.5 ? 1 : 0) + (hitVec.z() > 0.5 ? 2 : 0);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new GrillBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTicker(level, type, ModBlockEntities.GRILL.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createTicker(Level level, BlockEntityType<T> type, BlockEntityType<? extends GrillBlockEntity> grill)
    {
        if(level.isClientSide())
        {
            return createTickerHelper(type, grill, GrillBlockEntity::clientTick);
        }
        else
        {
            return createTickerHelper(type, grill, GrillBlockEntity::serverTick);
        }
    }

    @Override
    public List<TagKey<Block>> getTags()
    {
        return List.of(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.NEEDS_STONE_TOOL);
    }
}
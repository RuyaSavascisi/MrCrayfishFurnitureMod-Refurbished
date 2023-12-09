package com.mrcrayfish.furniture.refurbished.compat.jei;

import com.mrcrayfish.furniture.refurbished.Constants;
import com.mrcrayfish.furniture.refurbished.core.ModBlocks;
import com.mrcrayfish.furniture.refurbished.core.ModItems;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardRecipe;
import com.mrcrayfish.furniture.refurbished.crafting.FreezerSolidifyingRecipe;
import com.mrcrayfish.furniture.refurbished.util.Utils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/**
 * Author: MrCrayfish
 */
public class CuttingBoardCategory implements IRecipeCategory<CuttingBoardRecipe>
{
    public static final RecipeType<CuttingBoardRecipe> TYPE = RecipeType.create(Constants.MOD_ID, "cutting_board_slicing", CuttingBoardRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CuttingBoardCategory(IGuiHelper helper)
    {
        this.background = helper.createDrawable(Plugin.TEXTURES, 0, 36, 133, 36);
        this.icon = helper.createDrawableItemStack(new ItemStack(ModItems.KNIFE.get()));
    }

    @Override
    public RecipeType<CuttingBoardRecipe> getRecipeType()
    {
        return TYPE;
    }

    @Override
    public Component getTitle()
    {
        return Utils.translation("jei_category", "cutting_board_slicing");
    }

    @Override
    public IDrawable getBackground()
    {
        return this.background;
    }

    @Override
    public IDrawable getIcon()
    {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CuttingBoardRecipe recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 6).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 111, 10).addItemStack(Plugin.getResult(recipe));
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 73, 11).addItemStack(new ItemStack(ModItems.KNIFE.get()));
    }
}
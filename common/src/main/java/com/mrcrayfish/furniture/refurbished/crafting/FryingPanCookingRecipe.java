package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
public class FryingPanCookingRecipe extends ProcessingRecipe.Item
{
    public FryingPanCookingRecipe(Category category, Ingredient ingredient, ItemStack result, int time)
    {
        super(ModRecipeTypes.FRYING_PAN_COOKING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.FRYING_PAN_RECIPE.get();
    }
}

package com.mrcrayfish.furniture.refurbished.crafting;

import com.mrcrayfish.furniture.refurbished.core.ModRecipeSerializers;
import com.mrcrayfish.furniture.refurbished.core.ModRecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Author: MrCrayfish
 */
public class MicrowaveHeatingRecipe extends ProcessingRecipe.ItemWithCount
{
    public MicrowaveHeatingRecipe(Category category, Ingredient ingredient, ItemStack result, int time)
    {
        super(ModRecipeTypes.MICROWAVE_HEATING.get(), category, ingredient, result, time);
    }

    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipeSerializers.MICROWAVE_RECIPE.get();
    }
}

package com.mrcrayfish.furniture.refurbished.compat.crafttweaker.handlers;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.component.IDecomposedRecipe;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.IngredientUtil;
import com.blamejared.crafttweaker.api.util.StringUtil;
import com.mrcrayfish.furniture.refurbished.crafting.CuttingBoardSlicingRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Optional;

/**
 * Author: MrCrayfish
 */
@IRecipeHandler.For(CuttingBoardSlicingRecipe.class)
public class CuttingBoardSlicingRecipeHandler implements IRecipeHandler<CuttingBoardSlicingRecipe>
{
    @Override
    public String dumpToCommandString(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, RegistryAccess registryAccess, RecipeHolder<CuttingBoardSlicingRecipe> holder)
    {
        return "%s.addRecipe(%s, %s, %s);".formatted(
            manager.getCommandString(),
            StringUtil.quoteAndEscape(holder.id()),
            IIngredient.fromIngredient(holder.value().getIngredient()).getCommandString(),
            IItemStack.ofMutable(holder.value().getResult()).getCommandString()
        );
    }

    @Override
    public <U extends Recipe<?>> boolean doesConflict(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, CuttingBoardSlicingRecipe firstRecipe, U secondRecipe)
    {
        if(!(secondRecipe instanceof CuttingBoardSlicingRecipe slicingRecipe))
            return true;
        // Cutting board recipes are conflicting if the input is the same
        return IngredientUtil.canConflict(firstRecipe.getIngredient(), slicingRecipe.getIngredient());
    }

    @Override
    public Optional<IDecomposedRecipe> decompose(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, RegistryAccess registryAccess, CuttingBoardSlicingRecipe recipe)
    {
        IDecomposedRecipe decomposedRecipe = IDecomposedRecipe.builder()
            .with(BuiltinRecipeComponents.Input.INGREDIENTS, IIngredient.fromIngredient(recipe.getIngredient()))
            .with(BuiltinRecipeComponents.Output.ITEMS, IItemStack.ofMutable(recipe.getResult()))
            .build();
        return Optional.of(decomposedRecipe);
    }

    @Override
    public Optional<CuttingBoardSlicingRecipe> recompose(IRecipeManager<? super CuttingBoardSlicingRecipe> manager, RegistryAccess registryAccess, IDecomposedRecipe recipe)
    {
        Ingredient ingredient = recipe.getOrThrowSingle(BuiltinRecipeComponents.Input.INGREDIENTS).asVanillaIngredient();
        ItemStack result = recipe.getOrThrowSingle(BuiltinRecipeComponents.Output.ITEMS).getInternal();
        return Optional.of(new CuttingBoardSlicingRecipe(ingredient, result));
    }
}

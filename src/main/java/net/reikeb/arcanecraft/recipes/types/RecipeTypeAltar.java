package net.reikeb.arcanecraft.recipes.types;

import net.minecraft.item.crafting.IRecipeType;

import net.reikeb.arcanecraft.recipes.AltarRecipe;

public class RecipeTypeAltar implements IRecipeType<AltarRecipe> {

    @Override
    public String toString() {

        // Source: https://github.com/Minecraft-Forge-Tutorials/Custom-Json-Recipes/blob/master/src/main/java/net/darkhax/customrecipeexample/RecipeTypeClickBlock.java
        // Credits: Darkhax
        return "arcanecraft:ritual";
    }
}

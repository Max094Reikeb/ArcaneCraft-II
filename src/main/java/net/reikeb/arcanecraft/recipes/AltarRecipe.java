package net.reikeb.arcanecraft.recipes;

import com.google.gson.*;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import net.minecraftforge.registries.ForgeRegistryEntry;

import net.reikeb.arcanecraft.ArcaneCraft;
import net.reikeb.arcanecraft.init.BlockInit;
import net.reikeb.arcanecraft.recipes.contexts.RitualContext;
import net.reikeb.arcanecraft.utils.SingletonInventory;

import javax.annotation.Nullable;
import java.util.Optional;

public class AltarRecipe implements IRecipe<IInventory> {

    public static final Serializer SERIALIZER = new Serializer();

    private final Ingredient input;
    private final Ingredient input2;
    private final ItemStack output;
    private final ResourceLocation id;

    public AltarRecipe(ResourceLocation id, Ingredient input, Ingredient input2, ItemStack output) {
        this.id = id;
        this.input = input;
        this.input2 = input2;
        this.output = output;

        // This output is not required, but it can be used to detect when a recipe has been
        // loaded into the game.
        System.out.println("Loaded " + this.toString());
    }

    @Override
    public String toString() {

        // Overriding toString is not required, it's just useful for debugging.
        return "Ritual recipe [input=" + this.input + ", input2=" + this.input2 + ", output=" + this.output + ", id=" + this.id + "]";
    }

    public static Optional<AltarRecipe> getRecipe(World world, @Nullable BlockPos pos, ItemStack stack) {
        return getRecipe(world, new RitualContext(new SingletonInventory(stack), null, pos != null ? () -> Vector3d.atCenterOf(pos) : null, null));
    }

    public static Optional<AltarRecipe> getRecipe(World world, RitualContext ctx) {
        return world.getRecipeManager().getRecipeFor(ArcaneCraft.RITUAL, ctx, world);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return (this.input.test(inv.getItem(0)) && (this.input2.test(inv.getItem(1))));
    }

    @Override
    public ItemStack assemble(IInventory p_77572_1_) {

        // This method is ignored by our custom recipe system. getRecipeOutput().copy() is used instead.
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {

        // Unnecessary method, just need to override it to true.
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return ArcaneCraft.RITUAL;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(BlockInit.ALTAR.get());
    }

    private static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<AltarRecipe> {
        Serializer() {

            // This registry name is what people will specify in their json files.
            this.setRegistryName(new ResourceLocation(ArcaneCraft.MODID, "ritual"));
        }

        @Override
        public AltarRecipe fromJson(ResourceLocation recipeId, JsonObject json) {

            // Reads a recipe from json.

            // Reads the inputs. Accepts items, tags, and anything else that
            // Ingredient.deserialize can understand.
            final JsonElement inputElement = JSONUtils.isArrayNode(json, "input") ? JSONUtils.getAsJsonArray(json, "input") : JSONUtils.getAsJsonObject(json, "input");
            final Ingredient input = Ingredient.fromJson(inputElement);

            final JsonElement inputElement2 = JSONUtils.isArrayNode(json, "input2") ? JSONUtils.getAsJsonArray(json, "input2") : JSONUtils.getAsJsonObject(json, "input2");
            final Ingredient input2 = Ingredient.fromJson(inputElement2);

            // Reads the output. The common utility method in ShapedRecipe is what all vanilla
            // recipe classes use for this.
            final ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "output"));

            return new AltarRecipe(recipeId, input, input2, output);
        }

        @Override
        public AltarRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {

            // Reads a recipe from a packet buffer. This code is called on the client.
            final Ingredient input = Ingredient.fromNetwork(buffer);
            final Ingredient input2 = Ingredient.fromNetwork(buffer);
            final ItemStack output = buffer.readItem();

            return new AltarRecipe(recipeId, input, input2, output);
        }

        @Override
        public void toNetwork(PacketBuffer buffer, AltarRecipe recipe) {

            // Writes the recipe to a packet buffer. This is called on the server when a player
            // connects or when /reload is used.
            recipe.input.toNetwork(buffer);
            recipe.input2.toNetwork(buffer);
            buffer.writeItemStack(recipe.output, true);
        }
    }
}
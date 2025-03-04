package someassemblyrequired.data;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import someassemblyrequired.common.init.ModBlocks;
import someassemblyrequired.common.init.ModItems;
import someassemblyrequired.common.util.Util;
import someassemblyrequired.data.recipe.SandwichSpoutingRecipeBuilder;
import someassemblyrequired.data.recipe.farmersdelight.CuttingRecipeBuilder;
import someassemblyrequired.integration.ModCompat;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        addCraftingRecipes(consumer);
        addCookingRecipes(consumer);
        if (ModCompat.isFarmersDelightLoaded()) {
            CuttingRecipeBuilder.addCuttingRecipes(consumer);
        }
        if (ModCompat.isCreateLoaded()) {
            SandwichSpoutingRecipeBuilder.addFillingRecipes(consumer);
        }
    }

    private void addCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ModBlocks.SANDWICHING_STATION.get())
                .pattern("AA")
                .pattern("BB")
                .define('A', Items.SMOOTH_STONE)
                .define('B', ItemTags.PLANKS)
                .unlockedBy("has_smooth_stone", createItemCriterion(Items.SMOOTH_STONE))
                .save(consumer, getRecipeLocation(ModBlocks.SANDWICHING_STATION.get(), "crafting_shaped"));

        ShapelessRecipeBuilder.shapeless(ModItems.BURGER_BUN.get())
                .requires(Ingredient.of(ForgeTags.GRAIN_WHEAT), 2)
                .requires(Ingredient.of(ForgeTags.SEEDS))
                .unlockedBy("has_wheat", createItemCriterion(Items.WHEAT))
                .save(consumer, getRecipeLocation(ModItems.BURGER_BUN.get(), "crafting_shapeless"));
    }

    private void addCookingRecipes(Consumer<FinishedRecipe> consumer) {
        addBreadCookingRecipe(consumer, 200, RecipeSerializer.SMELTING_RECIPE, "smelting");
        addBreadCookingRecipe(consumer, 100, RecipeSerializer.SMOKING_RECIPE, "smoking");
        addBreadCookingRecipe(consumer, 600, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, "campfire_cooking");
    }

    private void addBreadCookingRecipe(Consumer<FinishedRecipe> consumer, int time, SimpleCookingSerializer<?> serializer, String type) {
        SimpleCookingRecipeBuilder
                .cooking(Ingredient.of(ModItems.BREAD_SLICE.get()), ModItems.TOASTED_BREAD_SLICE.get(), 0.35F, time, serializer)
                .unlockedBy("has_bread", createItemCriterion(ModItems.BREAD_SLICE.get()))
                .save(consumer, getRecipeLocation(ModItems.TOASTED_BREAD_SLICE.get(), type));
    }

    private ResourceLocation getRecipeLocation(ItemLike result, String location) {
        // noinspection ConstantConditions
        return Util.id(location + "/" + ForgeRegistries.ITEMS.getKey(result.asItem()).getPath());
    }

    private InventoryChangeTrigger.TriggerInstance createItemCriterion(ItemLike itemProvider) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(itemProvider);
    }
}

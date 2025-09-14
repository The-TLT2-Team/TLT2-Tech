package com.fg.tlt_tech.jei;

import blusunrize.immersiveengineering.api.crafting.IERecipeTypes;
import blusunrize.immersiveengineering.api.excavator.MineralMix;
import blusunrize.immersiveengineering.common.blocks.multiblocks.IEMultiblocks;
import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.init.TltTechItems;
import com.fg.tlt_tech.jei.clientOnlyRecipe.FuelFillingRecipe;
import com.fg.tlt_tech.jei.clientOnlyRecipe.NeutronCollectingRecipe;
import com.fg.tlt_tech.jei.clientOnlyRecipe.WasteExtractingRecipe;
import com.fg.tlt_tech.jei.integrations.*;
import mekanism.api.recipes.ChemicalCrystallizerRecipe;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.common.registries.MekanismItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.recipe.TinkerRecipeTypes;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuel;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.List;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return TltTech.getResource("tlt_tech_jei");
    }

    public static RecipeType<MineralMix> MINERAL_MIX = new RecipeType<>(ImmersiveMineRecipeCategory.UID, MineralMix.class);
    public static RecipeType<MeltingFuel> MELTING_FUEL = new RecipeType<>(MeltingFuelRecipeCategory.UID, MeltingFuel.class);

    public static final MekanismJEIRecipeType<FuelFillingRecipe> FUEL_FILLING = new MekanismJEIRecipeType<>(TltTech.getResource("dry_state_fuel_filling"), FuelFillingRecipe.class);
    public static final MekanismJEIRecipeType<ChemicalCrystallizerRecipe> NEUTRON_COLLECTING = new MekanismJEIRecipeType<>(TltTech.getResource("fission_neutron_collecting"), NeutronCollectingRecipe.class);
    public static final MekanismJEIRecipeType<ChemicalCrystallizerRecipe> WASTE_EXTRACTING = new MekanismJEIRecipeType<>(TltTech.getResource("dry_state_waste_extracting"), WasteExtractingRecipe.class);

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FuelFillingCategory(helper));
        registration.addRecipeCategories(new WasteExtractingCategory(helper));
        registration.addRecipeCategories(new NeutronCollectingCategory(helper));
        registration.addRecipeCategories(new ImmersiveMineRecipeCategory(helper));
        registration.addRecipeCategories(new MeltingFuelRecipeCategory(helper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level != null) {
            List<MineralMix> mineralMixes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(IERecipeTypes.MINERAL_MIX.get());
            List<MeltingFuel> meltingFuels = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(TinkerRecipeTypes.FUEL.get());

            registration.addRecipes(MINERAL_MIX, mineralMixes);
            registration.addRecipes(MELTING_FUEL, meltingFuels);
        }
        registration.addRecipes(MekanismJEI.recipeType(WASTE_EXTRACTING), List.of(new WasteExtractingRecipe()));
        registration.addRecipes(MekanismJEI.recipeType(NEUTRON_COLLECTING), List.of(new NeutronCollectingRecipe()));
        registration.addRecipes(MekanismJEI.recipeType(FUEL_FILLING),List.of(
                new FuelFillingRecipe(MekanismItems.YELLOW_CAKE_URANIUM.asItem(),250),
                new FuelFillingRecipe(TltTechItems.FU_DUST.get(),500),
                new FuelFillingRecipe(TltTechItems.CK_INDUCED_FU_DUST.get(),8000)
        ));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(TltTechBlocks.DRY_STATE_FUEL_FILLER,MekanismJEI.recipeType(FUEL_FILLING));
        registration.addRecipeCatalyst(TltTechBlocks.DRY_STATE_WASTE_EXTRACTOR,MekanismJEI.recipeType(WASTE_EXTRACTING));
        registration.addRecipeCatalyst(TltTechBlocks.FISSION_NEUTRON_COLLECT_PORT,MekanismJEI.recipeType(NEUTRON_COLLECTING));
        registration.addRecipeCatalyst(new ItemStack(IEMultiblocks.EXCAVATOR.getBlock()), MINERAL_MIX);
        registration.addRecipeCatalyst(new ItemStack(TinkerSmeltery.smelteryController), MELTING_FUEL);
        registration.addRecipeCatalyst(new ItemStack(TinkerSmeltery.scorchedAlloyer), MELTING_FUEL);
        registration.addRecipeCatalyst(new ItemStack(TinkerSmeltery.searedMelter), MELTING_FUEL);
        registration.addRecipeCatalyst(new ItemStack(TinkerSmeltery.foundryController), MELTING_FUEL);
    }
}

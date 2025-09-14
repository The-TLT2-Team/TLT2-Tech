package com.fg.tlt_tech.jei.integrations;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.jei.JeiPlugin;
import com.fg.tlt_tech.jei.clientOnlyRecipe.FuelFillingRecipe;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasTank;
import mekanism.api.providers.IItemProvider;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiGasGauge;
import mekanism.client.jei.MekanismJEI;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.client.jei.machine.ItemStackToChemicalRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeType;
import org.jetbrains.annotations.NotNull;

public class FuelFillingCategory extends ItemStackToChemicalRecipeCategory<Gas, GasStack,FuelFillingRecipe> {
    public FuelFillingCategory(IGuiHelper helper) {
        super(helper, JeiPlugin.FUEL_FILLING, TltTechBlocks.DRY_STATE_FUEL_FILLER, MekanismJEI.TYPE_GAS, true);
    }

    @Override
    protected @NotNull GuiChemicalGauge<Gas,GasStack, IGasTank> getGauge(@NotNull GaugeType type, int x, int y) {
        return GuiGasGauge.getDummy(type, this, x, y);
    }

    @Override
    public RecipeType<FuelFillingRecipe> getRecipeType() {
        return super.getRecipeType();
    }
}

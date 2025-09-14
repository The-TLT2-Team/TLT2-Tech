package com.fg.tlt_tech.jei.clientOnlyRecipe;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechItems;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.impl.ChemicalCrystallizerIRecipe;
import mekanism.common.registries.MekanismGases;
import net.minecraft.world.item.ItemStack;

public class WasteExtractingRecipe extends ChemicalCrystallizerIRecipe {
    public WasteExtractingRecipe() {
        super(TltTech.getResource("waste_extracting_from_port"), IngredientCreatorAccess.gas().from(MekanismGases.NUCLEAR_WASTE.getStack(2000)), new ItemStack(TltTechItems.REACTIVE_NUCLEAR_WASTE.get()));
    }
}

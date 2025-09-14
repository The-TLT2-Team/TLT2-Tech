package com.fg.tlt_tech.jei.clientOnlyRecipe;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechItems;
import committee.nova.mods.avaritia.init.registry.ModItems;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.impl.ChemicalCrystallizerIRecipe;
import mekanism.common.registries.MekanismGases;
import net.minecraft.world.item.ItemStack;

public class NeutronCollectingRecipe extends ChemicalCrystallizerIRecipe {
    public NeutronCollectingRecipe() {
        super(TltTech.getResource("neutron_collecting_from_port"), IngredientCreatorAccess.gas().from(MekanismGases.FISSILE_FUEL.getStack(1000)), new ItemStack(ModItems.neutron_ingot.get()));
    }
}
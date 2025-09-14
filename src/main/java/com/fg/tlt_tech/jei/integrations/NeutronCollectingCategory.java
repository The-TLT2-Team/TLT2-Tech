package com.fg.tlt_tech.jei.integrations;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.jei.JeiPlugin;
import com.fg.tlt_tech.jei.clientOnlyRecipe.NeutronCollectingRecipe;
import com.fg.tlt_tech.jei.clientOnlyRecipe.WasteExtractingRecipe;
import mekanism.api.recipes.ChemicalCrystallizerRecipe;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.client.jei.machine.ChemicalCrystallizerRecipeCategory;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;

public class NeutronCollectingCategory extends ChemicalCrystallizerRecipeCategory {
    public final IDrawable icon;
    public NeutronCollectingCategory(IGuiHelper helper) {
        super(helper, JeiPlugin.NEUTRON_COLLECTING);
        this.icon = createIcon(helper, TltTechBlocks.FISSION_NEUTRON_COLLECT_PORT);
    }

    @Override
    public Component getTitle() {
        return TltTechBlocks.FISSION_NEUTRON_COLLECT_PORT.getTextComponent();
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
}

package com.fg.tlt_tech.jei.integrations;

import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.init.TltTechBlocks;
import com.fg.tlt_tech.jei.JeiPlugin;
import com.fg.tlt_tech.jei.clientOnlyRecipe.WasteExtractingRecipe;
import mekanism.api.recipes.ChemicalCrystallizerRecipe;
import mekanism.client.jei.MekanismJEIRecipeType;
import mekanism.client.jei.machine.ChemicalCrystallizerRecipeCategory;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;

public class WasteExtractingCategory extends ChemicalCrystallizerRecipeCategory {
    public final IDrawable icon;
    public WasteExtractingCategory(IGuiHelper helper) {
        super(helper, JeiPlugin.WASTE_EXTRACTING);
        this.icon = createIcon(helper, TltTechBlocks.DRY_STATE_WASTE_EXTRACTOR);
    }

    @Override
    public Component getTitle() {
        return TltTechBlocks.DRY_STATE_WASTE_EXTRACTOR.getTextComponent();
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
}

package com.fg.tlt_tech.data.providers.tinker;

import com.fg.tlt_tech.TltTech;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;

public class TiAcModifierTagProvider extends AbstractModifierTagProvider {
    public TiAcModifierTagProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TltTech.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }

    @Override
    public String getName() {
        return "TLT Tech Modifier Tag Provider.";
    }
}

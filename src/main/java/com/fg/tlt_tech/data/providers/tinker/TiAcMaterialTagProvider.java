package com.fg.tlt_tech.data.providers.tinker;

import com.fg.tlt_tech.TltTech;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.data.tinkering.AbstractMaterialTagProvider;

public class TiAcMaterialTagProvider extends AbstractMaterialTagProvider {
    public TiAcMaterialTagProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TltTech.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }

    @Override
    public String getName() {
        return "TLT Tech Material Tag Provider";
    }
}

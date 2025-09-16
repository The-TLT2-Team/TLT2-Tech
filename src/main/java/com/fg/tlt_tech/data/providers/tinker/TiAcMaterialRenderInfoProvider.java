package com.fg.tlt_tech.data.providers.tinker;

import com.fg.tlt_tech.data.TltTechMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class TiAcMaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {
    public TiAcMaterialRenderInfoProvider(PackOutput packOutput, @Nullable AbstractMaterialSpriteProvider materialSprites, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, materialSprites, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(TltTechMaterialIds.PNEUMATIC_REINFORCED_TITANIUM).color(0xFFDEAAB8).fallbacks("metal");
        buildRenderInfo(TltTechMaterialIds.COMPRESSED_CHROMATIC_STEEL).color(0xFF443E4D).luminosity(15).fallbacks("metal");
        buildRenderInfo(TltTechMaterialIds.RESTRUCTURE_CHROMATIC_STEEL).color(0xFF9F97C9).luminosity(15).fallbacks("metal");
        buildRenderInfo(TltTechMaterialIds.AWAKEN_CHROMATIC_STEEL).color(0xFF8BBBFF).luminosity(15).fallbacks("metal");
    }

    @Override
    public String getName() {
        return "TLT Tech Material Info Provider";
    }
}

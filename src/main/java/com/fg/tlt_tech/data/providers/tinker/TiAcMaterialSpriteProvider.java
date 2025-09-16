package com.fg.tlt_tech.data.providers.tinker;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.fg.tlt_tech.TltTech;
import com.fg.tlt_tech.data.TltTechMaterialIds;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToSpriteTransformer;

public class TiAcMaterialSpriteProvider extends AbstractMaterialSpriteProvider {
    @Override
    public String getName() {
        return "TLT Tech Material Sprite Provider";
    }

    @Override
    protected void addAllMaterials() {
        this.buildMaterial(TltTechMaterialIds.COMPRESSED_CHROMATIC_STEEL).meleeHarvest().armor().fallbacks("metal").transformer(GreyToSpriteTransformer.builder()
                .addARGB(0,0xFF000000)
                .addTexture(63, TltTech.getResource("materials/platte/activated_chroma_plate_light"))
                .addTexture(102,TltTech.getResource("materials/platte/activated_chroma_plate_light"))
                .addARGB(140,0xFF575757)
                .addARGB(178,0xFF67676E)
                .addARGB(216,0xFF7B7C8C)
                .addARGB(255,0xFF888BA6).build());
        this.buildMaterial(TltTechMaterialIds.RESTRUCTURE_CHROMATIC_STEEL).meleeHarvest().armor().fallbacks("metal").transformer(GreyToSpriteTransformer.builder()
                .addARGB(0,0xFF000000)
                .addTexture(63, TltTech.getResource("materials/platte/exo_alloy_edge"))
                .addTexture(102,TltTech.getResource("materials/platte/exo_alloy_edge"))
                .addTexture(140,TinkersAdvanced.getLocation("materials/neutronite/neutronite_extra_dark"))
                .addARGB(178,0xFF1F0840)
                .addARGB(216,0xFF331154)
                .addARGB(255,0xFF634880).build());
        this.buildMaterial(TltTechMaterialIds.AWAKEN_CHROMATIC_STEEL).meleeHarvest().armor().fallbacks("metal").transformer(GreyToSpriteTransformer.builder()
                .addARGB(0,0xFF000000)
                .addTexture(63,TinkersAdvanced.getLocation("materials/activated_chromatic_steel/edge"))
                .addTexture(102,TinkersAdvanced.getLocation("materials/activated_chromatic_steel/edge"))
                .addARGB(140,0xFF42318F)
                .addARGB(178,0xFF614FAB)
                .addARGB(216,0xFF8773C4)
                .addARGB(255,0xFF634880).build());
        this.buildMaterial(TltTechMaterialIds.PNEUMATIC_REINFORCED_TITANIUM).ranged().meleeHarvest().armor().fallbacks("metal").transformer(GreyToSpriteTransformer.builder()
                .addARGB(0,0xFF000000)
                .addARGB(63,0xFF9A6A31)
                .addARGB(102,0xFFD9B275)
                .addARGB(140,0xFF7460AD)
                .addARGB(178,0xFFB8857E)
                .addARGB(216,0xFFDCB861)
                .addARGB(255,0xFFFFF99D).build());
    }
}

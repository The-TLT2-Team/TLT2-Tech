package com.fg.tlt_tech.data.providers.tinker;

import com.fg.tlt_tech.data.enums.EnumMaterial;
import com.fg.tlt_tech.data.enums.EnumMaterialModifier;
import com.fg.tlt_tech.data.enums.EnumTconMaterial;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class TiAcMaterialModifierProvider extends AbstractMaterialTraitDataProvider {
    public TiAcMaterialModifierProvider(PackOutput packOutput) {
        super(packOutput, new TiAcMaterialProvider(packOutput));
    }

    @Override
    protected void addMaterialTraits() {
        for (EnumMaterial material : EnumMaterial.values()){
            for (EnumMaterialModifier materialModifier:material.modifiers){
                if (materialModifier.statType==null){
                    addDefaultTraits(material.id,materialModifier.modifiers);
                }
                else addTraits(material.id,materialModifier.statType,materialModifier.modifiers);
            }
        }
        for (EnumTconMaterial material:EnumTconMaterial.values()){
            for (EnumMaterialModifier modifier:material.modifiers){
                addTraits(material.id,modifier.statType,modifier.modifiers);
            }
        }
    }

    @Override
    public String getName() {
        return "TLT Tech Material Modifier Provider";
    }
}

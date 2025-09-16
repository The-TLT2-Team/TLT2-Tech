package com.fg.tlt_tech.data.enums;

import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import com.fg.tlt_tech.data.enums.EnumMaterialModifier;
import com.fg.tlt_tech.data.enums.EnumTconExtraStat;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import static com.c2h6s.tinkers_advanced.data.enums.EnumMaterialModifier.*;

public enum EnumTconMaterial {

    ;
    public final EnumTconExtraStat stats;
    public final EnumMaterialModifier[] modifiers;
    public final MaterialId id;

    EnumTconMaterial(EnumTconExtraStat stats, MaterialId id, EnumMaterialModifier... modifiers) {
        this.stats = stats;
        this.modifiers = modifiers;
        this.id = id;
    }
}

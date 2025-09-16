package com.fg.tlt_tech.data.enums;

import com.c2h6s.etstlib.register.EtSTLibModifier;
import com.c2h6s.tinkers_advanced.registery.TiAcModifiers;
import com.fg.tlt_tech.init.TltTechModifiers;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.tools.TinkerModifiers;

public enum EnumMaterialModifier {
    PNEUMATIC_TITANIUM_DEFAULT(null,entry(EtSTLibModifier.EtSTLibModifierPnC.aerial_reinforced.getId(),2),entry(TltTechModifiers.PNEUMATIC_TOOL.getId()),entry(TinkerModifiers.momentum.getId())),
    PNEUMATIC_TITANIUM_ARMOR(MaterialRegistry.ARMOR,entry(EtSTLibModifier.EtSTLibModifierPnC.aerial_reinforced.getId(),2),entry(TiAcModifiers.AERIAL_PROTECTION.getId())),

    COMPRESSED_CHROMATIC_STEEL_MELEE(MaterialRegistry.MELEE_HARVEST,entry(TiAcModifiers.FLUX_INFUSED.getId()),entry(TltTechModifiers.EDGE_DISCHARGE.getId()),entry(TltTechModifiers.PNEUMATIC_TOOL.getId()),entry(TltTechModifiers.INDUSTRIAL_BORN.getId())),
    COMPRESSED_CHROMATIC_STEEL_ARMOR(MaterialRegistry.ARMOR,entry(TltTechModifiers.OVER_PRESSURE.getId()),entry(TiAcModifiers.FLUX_INFUSED.getId()),entry(TiAcModifiers.THERMAL_ENHANCE.getId()),entry(TltTechModifiers.INDUSTRIAL_BORN.getId())),

    RESTRUCTURE_CHROMATIC_STEEL_MELEE(MaterialRegistry.MELEE_HARVEST,entry(TltTechModifiers.HYPER_FLUX_LOADED.getId()),entry(TltTechModifiers.ELEMENTAL_SLASH.getId()),entry(TltTechModifiers.INDUSTRIAL_BORN.getId(),2),entry(TltTechModifiers.AUTOMATIC_TOOL.getId())),
    RESTRUCTURE_CHROMATIC_STEEL_ARMOR(MaterialRegistry.ARMOR,entry(TltTechModifiers.ENERGY_SHIELDING.getId()),entry(TltTechModifiers.INDUSTRIAL_BORN.getId(),2)),

    AWAKENED_CHROMATIC_STEEL_MELEE(MaterialRegistry.MELEE_HARVEST,entry(TltTechModifiers.ELEMENTAL_SWORD.getId()),entry(TltTechModifiers.HYPER_FLUX_LOADED.getId(),2),entry(TltTechModifiers.INDUSTRIAL_BORN.getId(),3),entry(TltTechModifiers.AUTOMATIC_TOOL.getId())),
    AWAKENED_CHROMATIC_STEEL_ARMOR(MaterialRegistry.ARMOR,entry(TltTechModifiers.HYPPER_ENERGY_SHIELDING.getId()),entry(TltTechModifiers.FLYING.getId()),entry(TltTechModifiers.INDUSTRIAL_BORN.getId(),3)),
    ;

    public final ModifierEntry[] modifiers;
    public final MaterialStatsId statType;
    EnumMaterialModifier(MaterialStatsId statType, ModifierEntry... modifiers){
        this.modifiers = modifiers;
        this.statType = statType;
    }
    public static ModifierEntry entry(ModifierId id,int level){
        return new ModifierEntry(id,level);
    }
    public static ModifierEntry entry(ModifierId id){
        return new ModifierEntry(id,1);
    }
}

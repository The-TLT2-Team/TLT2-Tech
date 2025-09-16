package com.fg.tlt_tech.data.enums;

import com.fg.tlt_tech.data.TltTechMaterialIds;
import com.fg.tlt_tech.data.enums.EnumMaterialModifier;
import com.fg.tlt_tech.data.enums.EnumMaterialStats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import slimeknights.mantle.recipe.condition.TagFilledCondition;
import slimeknights.tconstruct.common.json.ConfigEnabledCondition;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import static com.fg.tlt_tech.data.enums.EnumMaterialModifier.*;

public enum EnumMaterial {
    PNEUMATIC_TITANIUM(TltTechMaterialIds.PNEUMATIC_REINFORCED_TITANIUM,5,true,false,EnumMaterialStats.PNEUMATIC_TITANIUM,null,PNEUMATIC_TITANIUM_ARMOR,PNEUMATIC_TITANIUM_DEFAULT),
    COMPRESSED_CHROMATIC_STEEL(TltTechMaterialIds.COMPRESSED_CHROMATIC_STEEL,6,true,false,EnumMaterialStats.COMPRESSED_CHROMATIC_STEEL,null,COMPRESSED_CHROMATIC_STEEL_MELEE,COMPRESSED_CHROMATIC_STEEL_ARMOR),
    RESTRUCTURE_CHROMATIC_STEEL(TltTechMaterialIds.RESTRUCTURE_CHROMATIC_STEEL,7,false,false,EnumMaterialStats.RESTRUCTURE_CHROMATIC_STEEL,null,RESTRUCTURE_CHROMATIC_STEEL_ARMOR,RESTRUCTURE_CHROMATIC_STEEL_MELEE),
    AWAKEN_CHROMATIC_STEEL(TltTechMaterialIds.AWAKEN_CHROMATIC_STEEL,8,false,false,EnumMaterialStats.AWAKENED_CHROMATIC_STEEL,null,AWAKENED_CHROMATIC_STEEL_ARMOR,AWAKENED_CHROMATIC_STEEL_MELEE),

    ;
    public final MaterialId id;
    public final int tier;
    public final boolean craftable;
    public final boolean hidden;
    public final EnumMaterialStats stats;
    public final EnumMaterialModifier[] modifiers;
    public final ICondition condition;
    EnumMaterial(MaterialId id, int tier, boolean craftable, boolean hidden, EnumMaterialStats stats, ICondition condition, EnumMaterialModifier... modifiers){
        this.id = id;
        this.tier =tier;
        this.craftable = craftable;
        this.hidden = hidden;
        this.stats = stats;
        this.modifiers = modifiers;
        this.condition = condition;
    }
    public static ICondition modLoaded(String modId){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS,new ModLoadedCondition(modId));
    }
    public static ICondition tagFilled(TagKey<Item> tagKey){
        return new OrCondition(ConfigEnabledCondition.FORCE_INTEGRATION_MATERIALS, new TagFilledCondition<>(tagKey));
    }
}

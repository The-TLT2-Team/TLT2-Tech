package com.fg.tlt_tech.data.enums;

import com.c2h6s.tinkers_advanced.content.item.tinkering.materialStat.FluxCoreMaterialStat;
import net.minecraft.world.item.Tiers;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.tools.stats.*;

import static slimeknights.tconstruct.tools.stats.PlatingMaterialStats.*;

public enum EnumMaterialStats {
    PNEUMATIC_TITANIUM(
            armor(50,3f,7.5f,5.5f,3f).toughness(3).knockbackResistance(0.05F),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new FluxCoreMaterialStat(2.2f,3.2f),
            new HandleMaterialStats(0.2f,0.1f,0.1f,0.5f),
            new HeadMaterialStats(1750,6.5f, Tiers.NETHERITE,5f),
            new GripMaterialStats(0.2f,0.05f,5),
            new LimbMaterialStats(1750,0.25f,0.25f,0.05f)
    ),
    COMPRESSED_CHROMATIC_STEEL(
            armor(60,3.5f,8f,6f,3.5f).toughness(4).knockbackResistance(0.2F),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new FluxCoreMaterialStat(16.2f,96.2f),
            new HandleMaterialStats(0.3f,0.3f,0.2f,0.6f),
            new HeadMaterialStats(2060,7f, Tiers.NETHERITE,7f)
    ),
    RESTRUCTURE_CHROMATIC_STEEL(
            armor(70,4f,8.5f,6.5f,4f).toughness(8).knockbackResistance(0.5F),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new FluxCoreMaterialStat(172.2f,2048f),
            new HandleMaterialStats(0.5f,0.25f,0.25f,1.0f),
            new HeadMaterialStats(1750,8f, Tiers.NETHERITE,9f)
    ),
    AWAKENED_CHROMATIC_STEEL(
            armor(320,8f,14f,10f,7f).toughness(16).knockbackResistance(1F),
            true,
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.MAILLE,
            StatlessMaterialStats.SHIELD_CORE,
            new FluxCoreMaterialStat(262144f,262144),
            new HandleMaterialStats(0.5f,0.5f,1.5f,1.5f),
            new HeadMaterialStats(9999,8f, Tiers.NETHERITE,24f)
    ),

    ;
    private final IMaterialStats[] stats;
    private final Builder armorStatBuilder;
    public final boolean allowShield;
    EnumMaterialStats(Builder builder,boolean allowShield ,IMaterialStats... stats) {
        this.stats = stats;
        this.armorStatBuilder =builder;
        this.allowShield = allowShield;
    }
    EnumMaterialStats(IMaterialStats... stats) {
        this.stats = stats;
        this.armorStatBuilder =null;
        this.allowShield = false;
    }

    public IMaterialStats[] getStats() {
        return stats;
    }
    public Builder getArmorBuilder() {
        return armorStatBuilder;
    }

    public static Builder armor(int durabilityFactor,float helmet,float chestplate,float leggings,float boots){
        return PlatingMaterialStats.builder().durabilityFactor(durabilityFactor).armor(boots,leggings,chestplate,helmet);
    }
}

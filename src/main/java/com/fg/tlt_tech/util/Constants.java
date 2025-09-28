package com.fg.tlt_tech.util;

import me.desht.pneumaticcraft.api.pressure.PressureTier;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

import java.util.List;

public class Constants {
    public static final String TAG_MACHINE_PARALLEL = "MachineParallel";

    public static final List<String> CUSTOM_ADD = List.of(TAG_MACHINE_PARALLEL);
    public static final List<String> CUSTOM_INT = List.of(TAG_MACHINE_PARALLEL);

    public static final PressureTier TIER_3 = new PressureTier() {
        @Override
        public float getDangerPressure() {
            return Float.MAX_VALUE;
        }

        @Override
        public float getCriticalPressure() {
            return Float.MAX_VALUE;
        }
    };

    public static class NbtLocations{
        //使单个工具不被封印，以VolatileData的形式附加在匠魂工具上便能生效，这样无需遍历全词条。
        public static final ResourceLocation KEY_ANTI_RAGNAROK = new ResourceLocation("tltmod:anti_ragnarok");
    }
    public static class TinkerDataKeys{
        //实体带有这个data后全身物品都不会被封印，用ArmorLevelingModule来添加。
        public static final TinkerDataCapability.TinkerDataKey<Integer> KEY_TOTAL_ANTI_RAGNAROK = TinkerDataCapability.TinkerDataKey.of(new ResourceLocation("total_anti_ragnarok"));
    }
}

package com.fg.tlt_tech.util;

import me.desht.pneumaticcraft.api.pressure.PressureTier;

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
}

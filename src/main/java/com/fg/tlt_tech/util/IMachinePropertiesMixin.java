package com.fg.tlt_tech.util;

import cofh.thermal.lib.util.recipes.MachineProperties;

public interface IMachinePropertiesMixin {
    float tlt2$getMaxParallel();

    static float getMaxParallel(MachineProperties properties){
        return ((IMachinePropertiesMixin) properties).tlt2$getMaxParallel();
    }
}

package com.fg.tlt_tech.util;

import me.desht.pneumaticcraft.common.capabilities.MachineAirHandler;

public interface IMachineAirHandlerMixin {
    boolean tlt_tech$isSuperSafe();
    void tlt_tech$setSuperSafe(Boolean superSafe);

    static void setSuperSafe(MachineAirHandler handler,boolean superSafe){
        ((IMachineAirHandlerMixin)handler).tlt_tech$setSuperSafe(superSafe);
    }
    static boolean isSuperSafe(MachineAirHandler handler){
        return ((IMachineAirHandlerMixin)handler).tlt_tech$isSuperSafe();
    }
}

package com.fg.tlt_tech.mixin.thermal;

import cofh.core.util.helpers.AugmentableHelper;
import cofh.thermal.lib.util.recipes.MachineProperties;
import com.fg.tlt_tech.util.IMachinePropertiesMixin;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.fg.tlt_tech.util.Constants.TAG_MACHINE_PARALLEL;

@Mixin(value = MachineProperties.class,remap = false)
public class MachinePropertiesMixin implements IMachinePropertiesMixin {
    @Unique private static float tlt2$machineParallel;

    @Override
    public float tlt2$getMaxParallel() {
        return tlt2$machineParallel;
    }

    @Inject(method = "resetAttributes",at = @At("TAIL"))
    public void resetCustomAttribute(CallbackInfo ci){
        tlt2$machineParallel = 0;
    }
    @Inject(method = "setAttributesFromAugment",at = @At("TAIL"))
    public void setCustomAttribute(CompoundTag augmentData, CallbackInfo ci){
        tlt2$machineParallel+= AugmentableHelper.getAttributeMod(augmentData,TAG_MACHINE_PARALLEL);
    }
}

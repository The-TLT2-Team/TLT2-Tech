package com.fg.tlt_tech.mixin.mekanism;

import mekanism.common.content.sps.SPSMultiblockData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SPSMultiblockData.class,remap = false)
public class SPSMultiBlockDataMixin {
    @Inject(method = "getMaxInputGas",at = @At("RETURN"),cancellable = true)
    public void modifyInputGas(CallbackInfoReturnable<Long> cir){
        cir.setReturnValue(cir.getReturnValue()*1000);
    }
}

package com.fg.tlt_tech.mixin.immersive;

import blusunrize.immersiveengineering.api.excavator.MineralVein;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MineralVein.class,remap = false)
public class MineralVeinMixin {
    @Inject(method = "deplete",at = @At("HEAD"),cancellable = true)
    public void stopDeplete(CallbackInfo ci){ci.cancel();}
    @Inject(method = "isDepleted",at = @At("RETURN"),cancellable = true)
    public void setNotDepleted(CallbackInfoReturnable<Boolean> cir){cir.setReturnValue(false);}
}

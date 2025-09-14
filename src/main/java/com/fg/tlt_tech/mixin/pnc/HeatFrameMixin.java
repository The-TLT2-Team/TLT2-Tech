package com.fg.tlt_tech.mixin.pnc;

import me.desht.pneumaticcraft.common.entity.semiblock.HeatFrameEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = HeatFrameEntity.class,remap = false)
public class HeatFrameMixin {
    @ModifyArg(method = "doCooling",at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"),index = 1)
    public int modifyCoolingSpeed(int i){return i*2;}

    @ModifyArg(method = "doCooking",at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"),index = 0)
    public int modifyMaxCookingSpeed(int i){return 20;}
    @ModifyArg(method = "doCooling",at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"),index = 0)
    public int modifyMaxCoolingSpeed(int i){return 20;}
}

package com.fg.tlt_tech.mixin.pnc;

import me.desht.pneumaticcraft.common.tubemodules.AirGrateModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AirGrateModule.class,remap = false)
public class AirGrateMixin {
    @Inject(method = "pushEntities",at = @At("HEAD"),cancellable = true)
    public void stopPushing(Level world, BlockPos pos, Vec3 traceVec, CallbackInfo ci) {ci.cancel();}
}

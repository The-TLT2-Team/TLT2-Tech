package com.fg.tlt_tech.mixin.pnc;

import com.fg.tlt_tech.util.IMachineAirHandlerMixin;
import me.desht.pneumaticcraft.common.capabilities.MachineAirHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MachineAirHandler.class,remap = false)
public class MachineAirHandlerMixin implements IMachineAirHandlerMixin {
    @Unique private boolean tlt2$superSafe;

    @Override
    public boolean tlt_tech$isSuperSafe() {
        return tlt2$superSafe;
    }

    @Override
    public void tlt_tech$setSuperSafe(Boolean superSafe) {
        this.tlt2$superSafe =superSafe;
    }

    @Inject(method = "doOverpressureChecks",cancellable = true,at = @At("HEAD"))
    private void preventCheck(BlockEntity ownerTE, Level world, BlockPos pos, CallbackInfo ci){
        if (tlt2$superSafe) ci.cancel();
    }
}

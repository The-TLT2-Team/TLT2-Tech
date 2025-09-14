package com.fg.tlt_tech.mixin.pnc;

import me.desht.pneumaticcraft.api.pressure.PressureTier;
import me.desht.pneumaticcraft.common.block.entity.PressureTubeBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import static com.fg.tlt_tech.util.Constants.TIER_3;

@Mixin(value = PressureTubeBlockEntity.class,remap = false)
public class PressureTubeBlockEntityMixin {
    @ModifyArg(method = "<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lme/desht/pneumaticcraft/api/pressure/PressureTier;I)V",at = @At(value = "INVOKE", target = "Lme/desht/pneumaticcraft/common/block/entity/AbstractAirHandlingBlockEntity;<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lme/desht/pneumaticcraft/api/pressure/PressureTier;II)V"))
    private static PressureTier modifyPressureValue(PressureTier tier) {
        if (tier.equals(PressureTier.TIER_ONE)) return PressureTier.TIER_ONE_HALF;
        else if (tier.equals(PressureTier.TIER_TWO)) return TIER_3;
        else return PressureTier.TIER_TWO;
    }
}

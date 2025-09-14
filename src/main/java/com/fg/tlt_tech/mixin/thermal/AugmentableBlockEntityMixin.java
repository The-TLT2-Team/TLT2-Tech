package com.fg.tlt_tech.mixin.thermal;

import cofh.core.util.helpers.AugmentableHelper;
import cofh.thermal.lib.common.block.entity.AugmentableBlockEntity;
import com.fg.tlt_tech.util.Constants;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AugmentableBlockEntity.class,remap = false)
public class AugmentableBlockEntityMixin {
    @Shadow protected CompoundTag augmentNBT;

    @Inject(method = "setAttributesFromAugment",at = @At(value = "INVOKE", target = "Lcofh/core/util/helpers/AugmentableHelper;setAttributeFromAugmentMax(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/nbt/CompoundTag;Ljava/lang/String;)V",ordinal = 0,shift = At.Shift.AFTER))
    public void setParallelAttribute(CompoundTag augmentData, CallbackInfo ci){
        AugmentableHelper.setAttributeFromAugmentAdd(augmentNBT,augmentData, Constants.TAG_MACHINE_PARALLEL);
    }
}

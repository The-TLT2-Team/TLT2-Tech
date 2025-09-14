package com.fg.tlt_tech.mixin.thermal;

import cofh.core.util.helpers.AugmentableHelper;
import cofh.thermal.lib.common.block.entity.AugmentableBlockEntity;
import cofh.thermal.lib.common.block.entity.DeviceBlockEntity;
import com.fg.tlt_tech.util.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = DeviceBlockEntity.class,remap = false)
public abstract class DeviceBlockEntityMixin extends AugmentableBlockEntity {
    @Shadow protected float baseMod;

    public DeviceBlockEntityMixin(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    @Inject(method = "finalizeAttributes",at = @At("TAIL"))
    public void addParallelModifier(Map<Enchantment, Integer> enchantmentMap, CallbackInfo ci){
        if (AugmentableHelper.getAttributeMod(augmentNBT, Constants.TAG_MACHINE_PARALLEL)>0) baseMod*=AugmentableHelper.getAttributeMod(augmentNBT, Constants.TAG_MACHINE_PARALLEL)+1;
    }
}

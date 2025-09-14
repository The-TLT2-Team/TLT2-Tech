package com.fg.tlt_tech.mixin.mekanism;

import com.fg.tlt_tech.init.TltTechBlocks;
import mekanism.common.content.blocktype.BlockType;
import mekanism.common.lib.multiblock.FormationProtocol;
import mekanism.generators.common.content.fission.FissionReactorValidator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FissionReactorValidator.class,remap = false)
public abstract class FissionReactorValidatorMixin{
    @Inject(method = "getCasingType",at = @At("HEAD"),cancellable = true)
    private void addCustomPort(BlockState state, CallbackInfoReturnable<FormationProtocol.CasingType> cir){
        Block block = state.getBlock();

        if (BlockType.is(block, TltTechBlocks.BlockTypes.DRY_STATE_FUEL_FILLER)||
                BlockType.is(block, TltTechBlocks.BlockTypes.DRY_STATE_WASTE_EXTRACTOR)||
                BlockType.is(block, TltTechBlocks.BlockTypes.FISSION_NEUTRON_COLLECT_PORT)||
                BlockType.is(block, TltTechBlocks.BlockTypes.FISSION_THERMAL_ELECTRIC_PORT)
        ) cir.setReturnValue(FormationProtocol.CasingType.VALVE);
    }
}

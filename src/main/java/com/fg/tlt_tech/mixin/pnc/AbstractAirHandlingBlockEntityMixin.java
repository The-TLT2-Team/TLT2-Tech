package com.fg.tlt_tech.mixin.pnc;

import com.fg.tlt_tech.init.TltTechItems;
import com.fg.tlt_tech.util.IMachineAirHandlerMixin;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerMachine;
import me.desht.pneumaticcraft.common.block.entity.AbstractAirHandlingBlockEntity;
import me.desht.pneumaticcraft.common.block.entity.AbstractPneumaticCraftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AbstractAirHandlingBlockEntity.class,remap = false)
public abstract class AbstractAirHandlingBlockEntityMixin extends AbstractPneumaticCraftBlockEntity {


    public AbstractAirHandlingBlockEntityMixin(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "handleSecurityUpgrade",at = @At(value = "HEAD"))
    public void handleSuperSafety(IAirHandlerMachine handler, CallbackInfo ci){
        ((IMachineAirHandlerMixin)handler).tlt_tech$setSuperSafe(getUpgrades(TltTechItems.PnCUpgrades.ADVANCED_SAFETY_UPGRADE)>0);
    }
}

package com.fg.tlt_tech.content.tool.cap;

import cofh.redstonearsenal.common.capability.CapabilityFluxShielding;
import cofh.redstonearsenal.common.capability.IFluxShieldedItem;
import com.fg.tlt_tech.content.tool.modifier.FluxBlocking;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Supplier;

public class TinkerFluxShieldedItemCapProvider implements IFluxShieldedItem, ToolCapabilityProvider.IToolCapabilityProvider {
    public final IToolStackView tool;

    public TinkerFluxShieldedItemCapProvider(IToolStackView tool) {
        this.tool = tool;
    }
    public TinkerFluxShieldedItemCapProvider(Supplier<? extends IToolStackView> supplier){
        this.tool = supplier.get();
    }

    @Override
    public int currCharges(LivingEntity livingEntity) {
        return (int) (tool.getPersistentData().getFloat(FluxBlocking.KEY_CUR_CHARGE)/FluxBlocking.FE_PER_BLOCK);
    }

    @Override
    public int maxCharges(LivingEntity livingEntity) {
        return tool.getVolatileData().getInt(FluxBlocking.KEY_MAX_CHARGE);
    }

    @Override
    public boolean useCharge(LivingEntity livingEntity) {
        if (currCharges(livingEntity)>=1){
            if (livingEntity.invulnerableTime==0) tool.getPersistentData().putFloat(FluxBlocking.KEY_CUR_CHARGE,tool.getPersistentData().getFloat(FluxBlocking.KEY_CUR_CHARGE)-FluxBlocking.FE_PER_BLOCK);
            else if (livingEntity.invulnerableTime<10) livingEntity.invulnerableTime=10;
            return true;
        }
        return false;
    }

    @Override
    public <T> LazyOptional<T> getCapability(IToolStackView tool, Capability<T> cap) {
        if (cap== CapabilityFluxShielding.FLUX_SHIELDED_ITEM_CAPABILITY){
            return LazyOptional.of(()->new TinkerFluxShieldedItemCapProvider(tool)).cast();
        }
        return LazyOptional.empty();
    }
}

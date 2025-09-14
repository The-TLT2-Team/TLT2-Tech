package com.fg.tlt_tech.mixin.pnc;

import com.fg.tlt_tech.init.TltTechItems;
import me.desht.pneumaticcraft.api.upgrade.IUpgradeRegistry;
import me.desht.pneumaticcraft.common.upgrades.ApplicableUpgradesDB;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ApplicableUpgradesDB.class,remap = false)
public class ApplicableUpgradesDBMixin {
    @Inject(at = @At("HEAD"),method = "addApplicableUpgrades(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lme/desht/pneumaticcraft/api/upgrade/IUpgradeRegistry$Builder;)V")
    public void addCustomUpgrade(BlockEntityType<?> type, IUpgradeRegistry.Builder builder, CallbackInfo ci){
        builder.with(TltTechItems.PnCUpgrades.ADVANCED_SAFETY_UPGRADE,1);
    }
}

package com.fg.tlt_tech.mixin.tinkers.tiac;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.tinkers_advanced.content.modifier.combat.toolBase.SculkResonance;
import com.fg.tlt_tech.init.TltTechModifiers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

@Mixin(value = SculkResonance.class,remap = false)
public class SculkResonanceMixin {
    @Inject(method = "modifyDamageSource",at = @At("HEAD"),cancellable = true)
    public void setBypassEffect(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source, CallbackInfoReturnable<LegacyDamageSource> cir){
        if (tool.getModifierLevel(TltTechModifiers.REVERSE_POLARITY.get())>0){
            cir.setReturnValue(source.setBypassMagic().setBypassEnchantment());
        }
    }
}

package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.tool.modifiers.base.BasicFEModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class EnergyShielding extends BasicFEModifier implements DamageBlockModifierHook, ModifyDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_BLOCK,ModifierHooks.MODIFY_HURT);
    }

    @Override
    public int getCapacity(ModifierEntry modifierEntry) {
        return 1000000*modifierEntry.getLevel();
    }
    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (ToolEnergyUtil.extractEnergy(tool, 250 * amount, true) >= 250) {
            int reduce = ToolEnergyUtil.extractEnergy(tool, 250 * amount, false) / 250;
            return reduce >= amount ? 0 : amount - reduce;
        } else {
            return amount;
        }
    }
    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry entry, EquipmentContext context, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount) {
        if (context.getEntity().invulnerableTime > 0||
                damageSource.is(DamageTypeTags.IS_EXPLOSION)||
                damageSource.is(DamageTypeTags.IS_FIRE)||
                damageSource.is(DamageTypeTags.IS_DROWNING)||
                damageSource.is(DamageTypeTags.IS_FREEZING)||
                damageSource.is(DamageTypeTags.IS_LIGHTNING)) {
            return true;
        } else if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)){
            int needed = (int)(amount * 2000.0F);
            int maxCancle = ToolEnergyUtil.extractEnergy(tool, needed, true);
            if (maxCancle >= needed) {
                ToolEnergyUtil.extractEnergy(tool, needed, false);
                LivingEntity var10000 = context.getEntity();
                var10000.invulnerableTime += 20;
                return true;
            }
        }
        return false;
    }
    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        amount -= (float)ToolEnergyUtil.extractEnergy(tool, (int)(amount * 4000.0F), false) / 4000.0F;
        return amount;
    }
}

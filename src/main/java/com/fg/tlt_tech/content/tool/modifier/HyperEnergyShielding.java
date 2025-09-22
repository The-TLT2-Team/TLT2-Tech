package com.fg.tlt_tech.content.tool.modifier;

import cofh.core.init.CoreMobEffects;
import com.c2h6s.etstlib.tool.modifiers.base.BasicFEModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.c2h6s.tinkers_advanced.content.modifier.compat.thermal.ThermalEnhance;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class HyperEnergyShielding extends BasicFEModifier implements DamageBlockModifierHook , ModifyDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_BLOCK,ModifierHooks.MODIFY_HURT);
    }

    @Override
    public int getCapacity(ModifierEntry modifierEntry) {
        return 100000000*modifierEntry.getLevel();
    }
    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (ToolEnergyUtil.extractEnergy(tool, 200 * amount, true) >= 200) {
            int reduce = ToolEnergyUtil.extractEnergy(tool, 200 * amount, false) / 200;
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
                damageSource.is(DamageTypeTags.IS_LIGHTNING)||
                damageSource.is(DamageTypeTags.WITCH_RESISTANT_TO)) {
            return true;
        } else if (!damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)){
            int needed = (int)(amount * 250.0F);
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
        amount -= (float)ToolEnergyUtil.extractEnergy(tool, (int)(amount * 300), false) / 300;
        return amount;
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (isCorrectSlot) {
            holder.addEffect(new MobEffectInstance(CoreMobEffects.COLD_RESISTANCE.get(), 100, 5, false, false));
            holder.addEffect(new MobEffectInstance(CoreMobEffects.LIGHTNING_RESISTANCE.get(), 100, 5, false, false));
            holder.addEffect(new MobEffectInstance(CoreMobEffects.EXPLOSION_RESISTANCE.get(), 100, 5, false, false));
            holder.addEffect(new MobEffectInstance(CoreMobEffects.MAGIC_RESISTANCE.get(), 100, 5, false, false));
            holder.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100, 5, false, false));
            holder.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 5, false, false));
            holder.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 3, false, false));
            holder.addEffect(new MobEffectInstance(MobEffects.LUCK, 100, 5, false, false));
            if (!world.isClientSide) {
                if (holder.getAbsorptionAmount()<holder.getMaxHealth()*5&&world.getGameTime()%10==0){
                    holder.setAbsorptionAmount(holder.getAbsorptionAmount()+20);
                }
                if ((holder.isOnFire() || holder.isInLava()) && tool.getDamage() > 0) {
                    tool.setDamage(tool.getDamage() - 1);
                }
            }
        }
    }
}

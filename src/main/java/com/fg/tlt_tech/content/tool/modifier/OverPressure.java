package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.tool.modifiers.base.BasicPressurizableModifier;
import com.c2h6s.etstlib.tool.modifiers.capabilityProvider.PnCIntegration.AirStorageProvider;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.fg.tlt_tech.TltTech;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.DamageBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class OverPressure extends BasicPressurizableModifier implements DamageBlockModifierHook, EquipmentChangeModifierHook, ModifyDamageModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.DAMAGE_BLOCK,ModifierHooks.EQUIPMENT_CHANGE,ModifierHooks.MODIFY_DAMAGE);
    }
    public static final String KEY_LEGACY_FLYING = "tlt_tech_legacy_flying";

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity living = context.getEntity();
        if (living instanceof Player player) {
            player.getAbilities().mayfly = true;
            if (player.getPersistentData().getBoolean(KEY_LEGACY_FLYING)) {
                player.getAbilities().flying = true;
            }
        }
    }
    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        LivingEntity living = context.getEntity();
        if (living instanceof Player player) {
            player.getPersistentData().putBoolean(KEY_LEGACY_FLYING,player.getAbilities().flying);
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
        }
    }

    @Override
    public void modifierOnInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide&&holder instanceof Player player&&player.getAbilities().flying){
            if (ToolEnergyUtil.extractEnergy(tool,250,true)>=250){
                ToolEnergyUtil.extractEnergy(tool,250,false);
            } else if (AirStorageProvider.getAir(tool)>50){
                AirStorageProvider.addAir(tool,-50);
            } else {
                player.getPersistentData().putBoolean(KEY_LEGACY_FLYING,player.getAbilities().flying);
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
        }
    }

    @Override
    public int getBaseVolume(ModifierEntry modifierEntry) {
        return 5000;
    }

    @Override
    public float getMaxPressure(ModifierEntry modifierEntry) {
        return 10;
    }

    @Override
    public boolean isDamageBlocked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount) {
        return source.is(DamageTypeTags.IS_EXPLOSION)||source.is(DamageTypeTags.IS_FALL)||source.is(DamageTypeTags.DAMAGES_HELMET);
    }

    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (AirStorageProvider.getAir(tool)>500/modifier.getLevel()){
            float reduction = Math.min(AirStorageProvider.getAir(tool)/(500/modifier.getLevel()),source.is(DamageTypeTags.BYPASSES_RESISTANCE)||source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)||source.is(DamageTypeTags.BYPASSES_EFFECTS)?10:5);
            amount-=reduction;
            AirStorageProvider.addAir(tool, (int) (-reduction*500/modifier.getLevel()));
        }
        if (ToolEnergyCapability.getEnergy(tool)>2000/modifier.getLevel()){
            float reduction = Math.min(amount*0.2f*(source.is(DamageTypeTags.BYPASSES_ARMOR)||source.is(DamageTypeTags.BYPASSES_SHIELD)?1.5f:1),ToolEnergyCapability.getEnergy(tool)/2000f);
            ToolEnergyUtil.extractEnergy(tool,(int) (reduction*2000),false);
            amount-=reduction;
        }
        return amount;
    }
}

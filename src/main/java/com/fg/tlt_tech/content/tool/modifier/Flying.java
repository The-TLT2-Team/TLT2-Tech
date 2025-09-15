package com.fg.tlt_tech.content.tool.modifier;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static com.fg.tlt_tech.content.tool.modifier.OverPressure.KEY_LEGACY_FLYING;

public class Flying extends NoLevelsModifier implements EquipmentChangeModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
    }
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
}

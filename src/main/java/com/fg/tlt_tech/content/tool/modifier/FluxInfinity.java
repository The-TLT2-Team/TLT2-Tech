package com.fg.tlt_tech.content.tool.modifier;

import cofh.redstonearsenal.common.item.FluxQuiverItem;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.fg.tlt_tech.init.TltTechItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.ranged.BowAmmoModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.function.Predicate;

public class FluxInfinity extends EtSTBaseModifier implements BowAmmoModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BOW_AMMO);
    }

    @Override
    public ItemStack findAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack standardAmmo, Predicate<ItemStack> ammoPredicate) {
        if (standardAmmo.isEmpty()){
            if (ToolEnergyUtil.extractEnergy(tool,1000,true)>=1000) {
                ToolEnergyUtil.extractEnergy(tool, 1000, false);
                return new ItemStack(TltTechItems.FLUX_ARROW.get(), 64);
            }
        }
        return standardAmmo;
    }

    @Override
    public void shrinkAmmo(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, ItemStack ammo, int needed) {
        if (ammo.is(TltTechItems.FLUX_ARROW.get())) return;
        if (ToolEnergyUtil.extractEnergy(tool,1000*needed,true)>=1000){
            ToolEnergyUtil.extractEnergy(tool,1000*needed,false);
            return;
        }
        BowAmmoModifierHook.super.shrinkAmmo(tool, modifier, shooter, ammo, needed);
    }
}

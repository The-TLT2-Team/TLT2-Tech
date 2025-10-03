package com.fg.tlt_tech.content.tool.modifier;

import cofh.redstonearsenal.common.entity.FluxSlash;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.data.ModifierIds;

public class FluxSlashModifier extends EtSTBaseModifier {
    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (!level.isClientSide&&player.getAttackStrengthScale(0)>0.8&& ToolEnergyUtil.extractEnergy(tool,250,true)>=250){
            ToolEnergyUtil.extractEnergy(tool,250,false);
            FluxSlash projectile = new FluxSlash(level, player, tool.getModifierLevel(ModifierIds.sharpness)+2);
            level.addFreshEntity(projectile);
        }
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (context.isFullyCharged()&& ToolEnergyUtil.extractEnergy(tool,250,true)>=250&&!context.isExtraAttack()){
            Level level = context.getLevel();
            ToolEnergyUtil.extractEnergy(tool,250,false);
            FluxSlash projectile = new FluxSlash(level, context.getAttacker(), tool.getModifierLevel(ModifierIds.sharpness)+2);
            level.addFreshEntity(projectile);
        }
        return knockback;
    }
}

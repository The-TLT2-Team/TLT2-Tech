package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.tool.modifiers.base.BasicFEModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SupremeFluxLoaded extends BasicFEModifier {
    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (ToolEnergyCapability.getEnergy(tool)>5){
            float bonus = Math.min(ToolEnergyCapability.getEnergy(tool)/5f,damage*2*modifier.getLevel());
            bonus = Math.min(bonus, ToolEnergyUtil.extractEnergy(tool,(int) bonus*5,false)/5f);
            damage+=bonus;
        }
        return damage;
    }

    @Override
    public int getCapacity(ModifierEntry modifierEntry) {
        return 10000000*modifierEntry.getLevel();
    }

    @Override
    public int modifierDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
        if (ToolEnergyUtil.extractEnergy(tool, 10 * amount, true) >= 10) {
            int reduce = ToolEnergyUtil.extractEnergy(tool, 10 * amount, false) / 10;
            return reduce >= amount ? 0 : amount - reduce;
        } else {
            return amount;
        }
    }
}

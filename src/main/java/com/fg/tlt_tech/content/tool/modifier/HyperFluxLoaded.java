package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.tool.modifiers.base.BasicFEModifier;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.ToolEnergyCapability;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class HyperFluxLoaded extends BasicFEModifier {
    @Override
    public float onGetMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (ToolEnergyCapability.getEnergy(tool)>250){
            float bonus = Math.min(ToolEnergyCapability.getEnergy(tool)/250f,damage*0.5f*modifier.getLevel());
            bonus = Math.min(bonus, ToolEnergyUtil.extractEnergy(tool,(int) bonus*250,false)/250f);
            damage+=bonus;
        }
        return damage;
    }

    @Override
    public int getCapacity(ModifierEntry modifierEntry) {
        return 1000000*modifierEntry.getLevel();
    }
}

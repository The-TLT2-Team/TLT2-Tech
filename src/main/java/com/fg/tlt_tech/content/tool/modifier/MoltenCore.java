package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerInstance;
import com.c2h6s.etstlib.content.misc.entityTicker.EntityTickerManager;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.tinkers_advanced.util.CommonUtil;
import com.fg.tlt_tech.init.TltTechEntityTickers;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class MoltenCore extends EtSTBaseModifier implements BreakSpeedModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED);
    }

    @Override
    public void postMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage) {
        if (context.isFullyCharged()&&context.getAttacker() instanceof Player player&&context.getTarget() instanceof LivingEntity living){
            var tickerManager = EntityTickerManager.getInstance(living);
            living.getPersistentData().putInt(CommonUtil.KEY_ATTACKER,player.getId());
            tickerManager.addTicker(new EntityTickerInstance(TltTechEntityTickers.FIERY.get(),modifier.getLevel(),400),(i1,i2)->Math.min(25*modifier.getLevel(),i1+i2),Integer::max);
        }
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (isEffective) event.setNewSpeed(Float.MAX_VALUE);
    }
}

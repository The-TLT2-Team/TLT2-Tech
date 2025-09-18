package com.fg.tlt_tech.content.tool.modifier;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.tool.modifiers.base.EtSTBaseModifier;
import com.c2h6s.etstlib.util.ToolEnergyUtil;
import com.fg.tlt_tech.content.entity.ElementalSlashEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

public class ElementalSlashModifier extends EtSTBaseModifier {
    @Override
    public LegacyDamageSource modifyDamageSource(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source) {
        if (!(target instanceof Player)) source.setBypassInvul();
        return source;
    }

    @Override
    public void onLeftClickEmpty(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot) {
        if (!level.isClientSide&&player.getAttackStrengthScale(0)>0.8&& ToolEnergyUtil.extractEnergy(tool,1000,true)>=1000){
            ToolEnergyUtil.extractEnergy(tool,1000,false);
            createSlash(tool,player,level);
        }
    }

    @Override
    public void onLeftClickBlock(IToolStackView tool, ModifierEntry entry, Player player, Level level, EquipmentSlot equipmentSlot, BlockState state, BlockPos pos) {
        if (!level.isClientSide&&player.getAttackStrengthScale(0)>0.8&&!tool.getItem().isCorrectToolForDrops(state)&& ToolEnergyUtil.extractEnergy(tool,1000,true)>=1000){
            ToolEnergyUtil.extractEnergy(tool,1000,false);
            createSlash(tool,player,level);
        }
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (!context.isExtraAttack() && context.isFullyCharged()&& ToolEnergyUtil.extractEnergy(tool,1000,true)>=1000&&context.getTarget() instanceof LivingEntity){
            ToolEnergyUtil.extractEnergy(tool,1000,false);
            createSlash(tool,context.getAttacker(),context.getLevel());
        }
        return knockback;
    }

    static void createSlash(IToolStackView tool, LivingEntity player, Level level){
        ElementalSlashEntity entity = new ElementalSlashEntity(level);
        entity.tool = (ToolStack) tool;
        entity.setOwner(player);
        entity.setPos(player.position().add(0,0.5*player.getBbHeight(),0));
        entity.setDeltaMovement(player.getLookAngle().scale(0.5));
        level.addFreshEntity(entity);
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
}
